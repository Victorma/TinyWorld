using UnityEngine;
using System.Collections.Generic;

public class Hands : EntityScript, JSONAble {

    private List<TWItemScript> itemsToPick = new List<TWItemScript>();
    private bool dropLeft = false, dropRight = false, drop = false;
    private TWItemScript dropItem;
    private Cell dropInto;
    public TWItemScript leftHand = null, rightHand = null;
    private List<GameEvent> events = new List<GameEvent>();

    public override void eventHappened(GameEvent ge) {
        if (ge.getParameter("minion_id") != null && this.gameObject.GetInstanceID() == ((int)ge.getParameter("minion_id"))) {
            switch (ge.Name.ToLower()) {
                case "pick item":
                    Map map = ((Cell)this.Entity.Position).Map;
                    List<Cell> vecinas = new List<Cell>(map.getNeightbours((Cell)this.Entity.Position));
                    vecinas.Add((Cell)this.Entity.Position);
                    ItemData itd = (ItemData)ge.getParameter("Item");
                    TWItemScript item = null;
                    foreach (Cell c in vecinas) {
                        List<Entity> entities = new List<Entity>(c.getEntities());
                        foreach (Entity e in entities) {
                            TWItemScript its = e.GetComponent<TWItemScript>();
                            if (its != null) {
                                if (its.GetInstanceID() == itd.Id) {
                                    item = its;
                                    break;
                                }
                            }
                        }
                        if (item == null)
                            foreach (Entity e in entities) {
                                TWItemScript its = e.GetComponent<TWItemScript>();
                                if (its != null) {
                                    if (its.item.GetInstanceID() == itd.Id) {
                                        item = its;
                                        break;
                                    }
                                }
                            }

                        if (item != null)
                            break;
                    }

                    if (item != null) {
                        if (canPick(item)) {
                            itemsToPick.Add(item);
                            events.Add(ge);
                        }
                    }
                    break;
                case "drop leftitem":
                    dropLeft = true;
                    events.Add(ge);
                    break;
                case "drop rightitem":
                    dropRight = true;
                    events.Add(ge);
                    break;
                case "drop item":
                    drop = true;
                    ItemData tmpItem = (ItemData)ge.getParameter("item");
                    TWItemScript[] items = GameObject.FindObjectsOfType<TWItemScript>();

                    foreach (TWItemScript twi in items) {
                        if (twi.GetInstanceID() == tmpItem.Id) {
                            dropItem = twi;
                            break;
                        }
                    }

                    dropInto = ((Cell)this.Entity.Position).Map.fromCoords((Vector2)ge.getParameter("destination"));
                    events.Add(ge);
                    break;
            }
        }
    }

    public override Option[] getOptions() {
        if (this.Entity.GetComponent<Player>() != null) {
            GameEvent ge = ScriptableObject.CreateInstance<GameEvent>(),
            ge2 = ScriptableObject.CreateInstance<GameEvent>(),
            ge3 = ScriptableObject.CreateInstance<GameEvent>();
            ge.Name = "drop leftitem";
            ge2.Name = "drop rightitem";
            ge3.Name = "DepositarObjeto";
            ge.setParameter("minion_id", this.gameObject.GetInstanceID());
            ge2.setParameter("minion_id", this.gameObject.GetInstanceID());

            ge3.setParameter("minion_id", this.gameObject.GetInstanceID());
            if (this.rightHand != null)
                ge3.setParameter("item", ScriptableObject.CreateInstance<ItemData>().setItem(this.rightHand));
            ge3.setParameter("destination", new Vector2(5.0f, 5.0f));

            Option option = new Option("LeftHand", ge, false, 0),
            option2 = new Option("RightHand", ge2, false, 0),
            option3 = new Option("Depositar", ge3, false, 0);

            return new Option[] { option, option2, option3 };
        } else
            return new Option[] { };
    }

    public override void tick() {
        while (itemsToPick.Count > 0) {
            if (itemsToPick[0].item.Manos == 2) {
                if (rightHand == null && leftHand == null) {
                    rightHand = itemsToPick[0];
                    leftHand = itemsToPick[0];
                    itemsToPick[0].GetComponent<Entity>().Position = this.Entity;
                }
            } else if (itemsToPick[0].item.Manos == 1) {
                if (rightHand == null) {
                    rightHand = itemsToPick[0];
                    itemsToPick[0].GetComponent<Entity>().Position = this.Entity;
                } else if (leftHand == null) {
                    leftHand = itemsToPick[0];
                    itemsToPick[0].GetComponent<Entity>().Position = this.Entity;
                }
            }
            ItemData id = ScriptableObject.CreateInstance<ItemData>();
            id.setItem(itemsToPick[0]);
            events[0].setParameter("item", id);
            itemsToPick.Remove(itemsToPick[0]);
        }

        if (dropLeft) {
            if (leftHand != null) {
                Cell[] vecinas = ((Cell)this.Entity.Position).Map.getNeightbours((Cell)this.Entity.Position);
                foreach (Cell v in vecinas)
                    if (v.getEntities().Length == 0) {
                        leftHand.Entity.Position = v;
                        if (leftHand == rightHand) rightHand = null;
                        leftHand = null;
                        break;
                    }
                dropLeft = false;
            }
        }

        if (dropRight) {
            if (rightHand != null) {
                Cell[] vecinas = ((Cell)this.Entity.Position).Map.getNeightbours((Cell)this.Entity.Position);
                foreach (Cell v in vecinas)
                    if (v != null)
                        if (v.getEntities().Length == 0) {
                            rightHand.Entity.Position = v;
                            if (leftHand == rightHand) leftHand = null;
                            rightHand = null;
                            break;
                        }
                dropRight = false;
            }
        }

        if (drop) {
            List<Cell> vecinas = new List<Cell>(((Cell)this.Entity.Position).Map.getNeightbours((Cell)this.Entity.Position));
            if (vecinas.Contains(dropInto))
                if (leftHand == dropItem) {
                    dropItem.Entity.Position = dropInto;
                    if (leftHand == rightHand) rightHand = null;
                    leftHand = null;
                } else if (rightHand == dropItem) {
                    dropItem.Entity.Position = dropInto;
                    if (leftHand == rightHand) leftHand = null;
                    rightHand = null;
                }

            dropItem.Entity.Position = dropInto;
            drop = false;
        }



        while (events.Count > 0) {
            Game.main.eventFinished(events[0]);
            events.RemoveAt(0);
        }
    }

    public override void Update() { }

    [SerializeField]
    private List<Item> items = new List<Item>();
    public Item[] Items {
        get { return items.ToArray() as Item[]; }
    }

    private bool hayHueco(TWItem item) {
        bool hueco = false;
        switch (item.Manos) {
            case 1:
                hueco = leftHand == null || rightHand == null;
                break;
            case 2:
                hueco = leftHand == null && rightHand == null;
                break;
            default:
                hueco = false;
                break;
        }
        return hueco;
    }

    private int pesoManos() {
        int peso = 0;

        if (leftHand != null) peso += leftHand.item.Peso;
        if (rightHand != null) peso += rightHand.item.Peso;

        return peso;
    }

    public bool canPick(TWItemScript item) {
        MinionScript minion = this.GetComponent<MinionScript>();
        bool pickable = false;

        if (hayHueco(item.item)) {
            int it = item.item.Peso, man = this.pesoManos(), fu = minion.maxFuerza;
            if ((it + man) < fu)
                pickable = true;
        }

        return pickable;
    }

    #region JSONAble implementation

    public JSONObject toJSONObject() {
        MinionScript minion = this.GetComponent<MinionScript>();
        JSONObject js = new JSONObject();
        JSONObject jo = null;

        js.AddField("_instanceID", this.Entity.GetInstanceID());
        js.AddField("minion_name", minion.name);
        if (leftHand != null) {
            ItemData tmpid = ScriptableObject.CreateInstance<ItemData>();
            tmpid.setItem(leftHand);
            js.AddField("left_hand", tmpid);
        } else
            js.AddField("left_hand", jo);

        if (rightHand != null) {
            ItemData tmpid = ScriptableObject.CreateInstance<ItemData>();
            tmpid.setItem(rightHand);
            js.AddField("right_hand", tmpid);
        } else
            js.AddField("right_hand", jo);

        return js;
    }
    public void fromJSONObject(JSONObject json) {

    }

    #endregion

}
