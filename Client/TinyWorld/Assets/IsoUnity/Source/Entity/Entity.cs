using UnityEngine;
using System.Collections.Generic;

[ExecuteInEditMode]
public class Entity : MonoBehaviour {

    public bool canBlockMe = true;
    public bool isBlackList = true;
    public float maxJumpSize = 1.5f;
    public IsoDecoration normalSprite;
    public IsoDecoration jumpingSprite;
    public Texture2D face;
    public List<EntityScript> list;


    private bool recolocate;
    [SerializeField]
    private Object position;
    public Object Position {
        get {
            return position;
        }
        set {
            if (my_transform != null) {
                if (value is Cell) {
                    Cell tmp = (Cell)value;
                    position = value;
                    this.transform.parent = tmp.transform;
                    my_transform.position = tmp.transform.position + new Vector3(0, tmp.WalkingHeight + my_transform.localScale.y / 2f, 0);
                } else if (value is Entity) {
                    Entity tmp = (Entity)value;
                    position = value;
                    Hands manos = ((Entity)position).GetComponent<Hands>();
                    float xmod = 0f, ymod = this.transform.localScale.y; ;
                    if (manos != null) {
                        TWItemScript itm = this.GetComponent<TWItemScript>();
                        if (manos.leftHand == itm && manos.rightHand == itm) {
                            ymod = this.transform.localScale.y / 2;
                        } else if (manos.leftHand == itm) {
                            ymod = -this.transform.localScale.y / 3;
                            xmod = -(((Entity)position).transform.localScale.x) / 3;
                        } else if (manos.rightHand == itm) {
                            ymod = -this.transform.localScale.y / 3;
                            xmod = (((Entity)position).transform.localScale.x) / 3;
                        }

                    }
                    this.transform.parent = tmp.transform;
                    this.transform.localPosition = new Vector3(xmod, ymod, -0.01f);
                }
            } else {
                position = value;
                recolocate = true;
            }
        }
    }

    public bool canMoveTo(Cell from, Cell to) {
        //canAccedTo(c);

        bool canMove = false;

        if (to != null && Mathf.Abs(from.WalkingHeight - to.WalkingHeight) <= maxJumpSize) {
            if (canBlockMe)
                canMove = to.isAccesibleBy(this);
            else
                canMove = true;
        }

        //canGoThroughEntities(c);
        return canMove;
    }

    public bool canMoveTo(Cell c) {
        bool canmove = false;
        if (position is Cell)
            canmove = canMoveTo((Cell)position, c);
        return canmove;
    }

    public bool letPass(Entity e) {
        /*foreach(EntityScript en in list){
            foreach(EntityScript hisEn in e.GetComponents<EntityScript>()){
                if(hisEn == en)
                    return !isBlackList;
            }
        }*/
        return isBlackList;
    }

    public bool canGoThrough(Entity e) {
        return false;
    }

    public void tick() {
        if (recolocate)
            this.Position = this.position;
        foreach (EntityScript es in this.GetComponents<EntityScript>())
            es.tick();
    }

    public void eventHappened(GameEvent ge) {
        EntityScript[] scripts = this.GetComponents<EntityScript>();

        //TODO Preference system

        foreach (EntityScript es in scripts)
            es.eventHappened(ge);
    }

    public Option[] getOptions() {
        EntityScript[] scripts = this.GetComponents<EntityScript>();
        List<Option> options = new List<Option>();

        foreach (EntityScript es in scripts)
            options.AddRange(es.getOptions());

        return options.ToArray() as Option[];
    }

    // Use this for initialization
    void Start() {
        if (Application.isPlaying) {
            Mover mover = this.gameObject.AddComponent<Mover>();
            mover.normalSprite = normalSprite;
            mover.jumpingSprite = jumpingSprite;
        }
    }


    Transform my_transform;
    public Decoration decoration {
        get {
            return this.GetComponent<Decoration>();
        }
    }

    public Mover mover {
        get {
            return this.GetComponent<Mover>();
        }
    }

    // Update is called once per frame
    void Update() {
        if (my_transform == null)
            my_transform = this.transform;

        if (!Application.isPlaying && Application.isEditor) {

            Transform parent = my_transform.parent;
            Transform actual = null;
            if (position != null)
                if (position is Cell) actual = ((Cell)position).transform;
                else actual = ((Entity)position).transform;

            if (parent != actual) {
                if (parent.GetComponent<Cell>() != null) {
                    Cell probablyParent = parent.GetComponent<Cell>();
                    if (probablyParent != null)
                        position = probablyParent;
                    else if (actual != null)
                        my_transform.parent = actual;
                } else if (parent.GetComponent<Entity>() != null) {
                    Entity probablyParent = parent.GetComponent<Entity>();
                    if (probablyParent != null)
                        position = probablyParent;
                    else if (actual != null)
                        my_transform.parent = actual;
                }
            }

            if (this.position != null) {
                if (position is Cell)
                    my_transform.position = ((Cell)position).transform.position + new Vector3(0, ((Cell)position).WalkingHeight + my_transform.localScale.y / 2f, 0);
                else if (position is Entity) {
                    Entity tmp = (Entity)position;
                    Hands manos = ((Entity)position).GetComponent<Hands>();
                    float xmod = 0f, ymod = this.transform.localScale.y;
                    if (manos != null) {
                        TWItemScript itm = this.GetComponent<TWItemScript>();
                        if (manos.leftHand == itm && manos.rightHand == itm) {
                            ymod = this.transform.localScale.y / 2;
                        } else if (manos.leftHand == itm) {
                            ymod = -this.transform.localScale.y / 3;
                            xmod = -(((Entity)position).transform.localScale.x) / 3;
                        } else if (manos.rightHand == itm) {
                            ymod = -this.transform.localScale.y / 3;
                            xmod = (((Entity)position).transform.localScale.x) / 3;
                        }

                    }
                    this.transform.parent = tmp.transform;
                    this.transform.localPosition = new Vector3(xmod, ymod, -0.01f);
                }
            }
        }


    }

    public void onDestroy() {

    }
}
