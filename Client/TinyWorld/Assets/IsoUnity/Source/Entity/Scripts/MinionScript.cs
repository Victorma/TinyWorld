using UnityEngine;
using System.Collections.Generic;


public class MinionScript : EntityScript, JSONAble {
    public int salud = 0, sed = 0, energia = 0;
    public int maxSalud = 0, maxFuerza = 0, maxSed = 0, maxEnergia = 0;
	public bool uses = false, forceuses = false, observes = false;
	public int visionrange = 0;
	private GameEvent ge;
    public new string name = "";
    private static string[][] usos = new string[][]{
		new string[]{"Roca","Roca"},
		new string[]{"Roca","Palo"},
		new string[]{"Roca","Roca Afilada","Madera"},
		new string[]{"Cantera","Martillo"},
		new string[]{"Madera","Cuerda"},
		new string[]{"Roca Afilada","Palo"},
		new string[]{"Roca Afilada","Cadaver"},
		new string[]{"Tomatera","Hacha"},
		new string[]{"Arbol","Hacha"},
		new string[]{"Hoja","Hoja","Hoja","Hoja"},
		new string[]{"Martillo","Roca"},
		new string[]{"Brote"}
	};

    private int saludmod = 0, sedmod = 0, energiamod = 0;

    public MinionScript() {
    }

    public override void eventHappened(GameEvent ge) {
		if (ge.getParameter("minion_id")!=null && ((int)ge.getParameter ("minion_id")) == this.gameObject.GetInstanceID()) {
			switch (ge.Name.ToLower ()) {
			case "forceduse":
				this.forceuses = true;
				break;
			case "use":
				this.uses = true;
				this.ge = ge;
				break;
			case "modify salud":
				saludmod = (int)ge.getParameter ("salud");
				break;
			case "modify sed":
				sedmod = (int)ge.getParameter ("sed");
				break;
			case "modify energia":
				energiamod = (int)ge.getParameter ("energia");
				break;
			case "observe":
				this.observes = true;
				break;
			}
		}
	}

    private string previousStatus = "";

    public override void tick() {

        var jable = this as JSONAble;
        if (previousStatus == "")
            previousStatus = jable.toJSONObject().ToString();

        if (this.saludmod != 0) { this.salud += this.saludmod; this.saludmod = 0; }
        if (this.sedmod != 0) { this.sed += this.sedmod; this.sedmod = 0; }
        if (this.energiamod != 0) { this.energia += this.energiamod; this.energiamod = 0; }
        if (this.forceuses) {
            Hands hands = this.GetComponent<Hands>();
            List<TWItemScript> items = new List<TWItemScript>();
            if (hands.leftHand != null)
                items.Add(hands.leftHand);
            if (hands.rightHand != null)
                if (!items.Contains(hands.rightHand))
                    items.Add(hands.rightHand);

            Cell pos = (Cell)this.Entity.Position;
            List<Cell> vecinas = new List<Cell>(pos.Map.getNeightbours(pos));
            vecinas.Add(pos);
            TWItemScript its;

            foreach (Cell v in vecinas) {
                if (v != null)
                    foreach (Entity e in v.getEntities()) {
                        its = e.GetComponent<TWItemScript>();
                        if (its != null)
                            if (!items.Contains(its))
                                items.Add(its);
                    }
            }

            if (items.Count != 0) { }
            this.use(items.ToArray());
			this.forceuses = false;
        }

        if (previousStatus != jable.toJSONObject().ToString()){
            GameEvent ge = GameEvent.CreateInstance<GameEvent>();
            ge.name = "update minion";
            ge.setParameter("minion", this);

            Game.main.enqueueEvent(ge);

            previousStatus = jable.toJSONObject().ToString();
        }
		if (this.uses) {
			IList<object> obs = (IList<object>) ge.getParameter("items");
			List<ItemData> items= new List<ItemData>();
			foreach(object o in obs){
				items.Add((ItemData)o);
			}
			GameEvent ret = this.use(items.ToArray());
			Game.main.enqueueEvent(ret);
			this.uses = false;
		}
		if (this.observes) {
			TWItemScript [] items = GameObject.FindObjectsOfType<TWItemScript>();
			Map mapa = ((Cell)this.Entity.Position).Map;
			Vector2 myCoords = mapa.getCoords(this.Entity.gameObject), itmCoords;

			List<object> itemsFloor = new List<object>(), itemsHand = new List<object>();

			foreach(TWItemScript twi in items){
				if(twi.Entity.Position is Cell){
					itmCoords = mapa.getCoords(((Cell)twi.Entity.Position).gameObject);

					if((myCoords-itmCoords).magnitude<this.visionrange){
						ItemData id = ScriptableObject.CreateInstance<ItemData>();
						id.setItem(twi);
						itemsFloor.Add(id);
					}
				}else if(twi.Entity.Position is Entity){
					itmCoords = mapa.getCoords(((Cell)((Entity)twi.Entity.Position).Position).gameObject);
					
					if((myCoords-itmCoords).magnitude<this.visionrange){
						ItemData id = ScriptableObject.CreateInstance<ItemData>();
						id.setItem(twi);
						itemsHand.Add(id);
					}
				}
			}

			GameEvent ge = ScriptableObject.CreateInstance<GameEvent>();
			ge.Name = "observereport";
			ge.setParameter("minion_id", this.gameObject.GetInstanceID());
			ge.setParameter("items_floor",itemsFloor);
			ge.setParameter("items_hand",itemsHand);

			observes = false;
			Game.main.enqueueEvent(ge);
		}
    }
    public override void Update() {
    }
    public override Option[] getOptions() {
        GameEvent ge = ScriptableObject.CreateInstance<GameEvent>();
        ge.Name = "forceduse";
        ge.setParameter("minion_id", this.gameObject.GetInstanceID());
        Option option = new Option("Usar Manos", ge, false, 0);

		GameEvent ge2 = ScriptableObject.CreateInstance<GameEvent>();
		ge2.Name = "use";
		ge2.setParameter("minion_id", this.gameObject.GetInstanceID());
		Hands h = this.GetComponent<Hands> ();
		List<TWItemScript> items = new List<TWItemScript> ();
		if(h.leftHand!=null) if(!items.Contains(h.leftHand)) items.Add (h.leftHand); 
		if(h.rightHand!=null) if(!items.Contains(h.rightHand)) items.Add (h.rightHand); 

		Cell pos = (Cell)this.Entity.Position;
		List<Cell> vecinas = new List<Cell>(pos.Map.getNeightbours(pos));
		vecinas.Add(pos);
		TWItemScript its;
		
		foreach (Cell v in vecinas) {
			if (v != null)
			foreach (Entity e in v.getEntities()) {
				its = e.GetComponent<TWItemScript>();
				if (its != null)
					if (!items.Contains(its))
						items.Add(its);
			}
		}

		List<object> ids = new List<object> ();
		foreach (TWItemScript twi in items) {
			ItemData id = ScriptableObject.CreateInstance<ItemData> ();
			id.setItem(twi);
			ids.Add(id);
		}

		ge2.setParameter ("items", ids);

		Option option2 = new Option("Usar Manos2", ge2, false, 0);

		GameEvent ge3 = ScriptableObject.CreateInstance<GameEvent>();
		ge3.Name = "ObservarEntorno";
		ge3.setParameter("minion_id", this.gameObject.GetInstanceID());
		Option option3 = new Option("Observar", ge3, false, 0);

		return new Option[] { option,option2,option3 };
    }

    public bool canPick(TWItemScript item) {
        Hands manos = this.GetComponent<Hands>();
        return manos.canPick(item);
    }

    public int canUse(TWItemScript[] items) {
        int canbeused = -1;

        for (int i = 0; i < usos.Length; i++) {
            //Para cada uno de las listas de items damos una vuelta.
            bool same = true;

            List<int> used = new List<int>();
            List<int> itemsused = new List<int>();

            string[] palabras = usos[i];

            if (palabras.Length == items.Length) {
                for (int j = 0; j < palabras.Length; j++) {
                    bool found = false;

                    for (int k = 0; k < items.Length; k++) {
                        if (!used.Contains(j) && !itemsused.Contains(k)) {
							string r = items[k].item.name, m = palabras[j];
                            if (r.ToLower().Equals(m.ToLower())) {
                                found = true;
                                used.Add(j);
                                itemsused.Add(k);
                                break;
                            }
                        }
                    }
                    if (!found) {
                        same = false;
                        break;
                    }
                }
            } else same = false;

            if (same) {
                canbeused = i;
                break;
            }
        }
        return canbeused;
    }

	public TWItemScript[] haveTheItems(Object[] items){
		TWItemScript [] list = null;
		if (items is TWItemScript[]) {
			list = haveTheItems((TWItemScript[]) items);
		}else if(items is ItemData[])
			list = haveTheItems((ItemData[]) items);

		return list;
	}

	public TWItemScript[] haveTheItems(TWItemScript[] items) {
        Hands manos = this.GetComponent<Hands>();
        Cell pos = (Cell)this.Entity.Position;
        List<Cell> vecinas = new List<Cell>(pos.Map.getNeightbours(pos));
        vecinas.Add(pos);

        bool have = true;
        foreach (TWItemScript it in items) {
            if (manos.leftHand != it && manos.rightHand != it) {
                if (it.Entity.Position is Cell) {
                    if (!vecinas.Contains((Cell)it.Entity.Position))
                        have = false;
                } else
                    have = false;
            }
        }

        return have?items:null;
    }

	public TWItemScript[] haveTheItems(ItemData[] items){
		Hands manos = this.GetComponent<Hands>();
		Cell pos = (Cell)this.Entity.Position;
		List<Cell> vecinas = new List<Cell>(pos.Map.getNeightbours(pos));
		vecinas.Add(pos);
		List<TWItemScript> itemList = new List<TWItemScript> ();

		bool have = true;
		Entity[] tmpEnt; TWItemScript tmpIt, foundItem;
		foreach (ItemData it in items) {
			if (manos.leftHand == null || manos.leftHand.GetInstanceID() != it.Id) {
				if(manos.rightHand == null || manos.rightHand.GetInstanceID() != it.Id){
					foundItem = null;
					foreach(Cell c in vecinas){
						tmpEnt = c.getEntities();
						foreach(Entity e in tmpEnt){
							tmpIt = e.GetComponent<TWItemScript>();
							if(tmpIt!=null)
								if(tmpIt.GetInstanceID()==it.Id){
									foundItem = tmpIt;
									itemList.Add(tmpIt);
								}
						}
					}
					if(foundItem==null){
						have = false;
						break;
					}
				}else
					itemList.Add(manos.rightHand);
			}else
				itemList.Add(manos.leftHand);
		}
		
		return have?itemList.ToArray():null;
	}

	public GameEvent use(Object[] itm) {
		GameEvent ge = GameEvent.CreateInstance<GameEvent>();
		ge.Name = "UseReport";

		TWItemScript[] items = haveTheItems (itm);

		if (items == null) {
			ge.setParameter("result","donthave");
			return ge;
		}

		List<ItemData> nuevos = new List<ItemData>(), noHanCambiado = new List<ItemData>(), borrados = new List<ItemData>();
		Cell pos;
        switch (canUse(items)) {
            case 0: { //{"Roca","Roca"}
                    foreach (TWItemScript it in items){
						addItemDatatoList(it,borrados);
						TWItem.destroyItem(it);
					}
					pos = (Cell)this.Entity.Position;
					
					addItemDatatoList(TWItem.instanceItem("RocaAfilada", pos),pos,nuevos);
					ge.setParameter("result","success");
                    break;
                }
            case 1: { //{"Roca","Palo"}
					foreach (TWItemScript it in items){
						addItemDatatoList(it,borrados);
						TWItem.destroyItem(it);
					}
					pos = (Cell)this.Entity.Position;

					addItemDatatoList(TWItem.instanceItem("Martillo", pos),pos,nuevos);
					ge.setParameter("result","success");
                    break;
                }
            case 2: { break; }
            case 3: {//{"Cantera","Martillo"}
                    bool rotura = Random.Range(0, 10) <= 2;
                    bool piedra = Random.Range(0, 10) <= 6;
                    //bool hierro = Random.Range(0, 10) <= 4;
                    //bool diamante = Random.Range(0, 10) <= 1;
                    Map map = ((Cell)this.Entity.Position).Map;

                    TWItemScript cantera = null;
                    foreach (TWItemScript it in items) {
                        if (it.item.Name == "Cantera") {
                            cantera = it;
                            break;
						}else if(it.item.Name == "Martillo"){
							addItemDatatoList(it,noHanCambiado);
						}
                    }

                    List<Cell> vecinas = new List<Cell>(map.getNeightbours((Cell)cantera.Entity.Position));

                    if (rotura) {
                        Decoration dec = cantera.GetComponent<Decoration>();
                        if (dec.Tile < 2) {
                            dec.Tile = dec.Tile + 1;
							addItemDatatoList(cantera,noHanCambiado);
                        } else {
							addItemDatatoList(cantera,borrados);
                            vecinas.Add((Cell)cantera.Entity.Position);
                            TWItem.destroyItem(cantera);
                        }
                    }

                    if (piedra){
						pos = vecinas[Random.Range(0, vecinas.Count)];
						addItemDatatoList(TWItem.instanceItem("Roca", pos),pos,nuevos);
					}

                    break;
                }
            case 4: { break; }
            case 5: { //{"Roca Afilada","Palo"}
					foreach (TWItemScript it in items){
						addItemDatatoList(it,borrados);
						TWItem.destroyItem(it);
					}
					pos = (Cell)this.Entity.Position;
					
					addItemDatatoList(TWItem.instanceItem("Hacha", pos),pos,nuevos);
					ge.setParameter("result","success");
					break;
                }
            case 6: { break; }
            case 7: { break; }
            case 8: { //{"Arbol","Hacha"}
                    int maderas = 1 + Random.Range(0, 3);
                    int palos = 2 + Random.Range(0, 2);
                    //int hojas = 5;
                    int brotes = Random.Range(0, 3);
                    Cell arbol = null;

                    foreach (TWItemScript it in items) {
                        if (it.item.Name == "Arbol") {
							addItemDatatoList(it,borrados);
                            arbol = (Cell)it.Entity.Position;
                            TWItem.destroyItem(it);
						}else if(it.item.Name == "Hacha")
							addItemDatatoList(it,noHanCambiado);
                    }

                    Map map = arbol.Map;
                    Cell[] vecinas = map.getNeightbours(arbol);

                    int dir = Random.Range(0, 4);
                    List<Cell> csbrotes = new List<Cell>();
                    if (brotes == 1) {
                        if (Random.Range(0, 2) == 0)
                            csbrotes.Add(vecinas[(dir + 1) % 4]);
                        else
                            csbrotes.Add(vecinas[(dir + 3) % 4]);
                    } else if (brotes == 2) {
                        csbrotes.Add(vecinas[(dir + 1) % 4]);
                        csbrotes.Add(vecinas[(dir + 3) % 4]);
                    }

                    foreach (Cell c in csbrotes) addItemDatatoList(TWItem.instanceItem("Brote", c),c,nuevos);

                    for (int i = 1; i <= maderas; i++) {
                        Cell cs = arbol;
                       
						addItemDatatoList(TWItem.instanceItem("Madera", cs),cs,nuevos);

                        if (i == maderas) {
                            List<Cell> cspalos = new List<Cell>();
                            cspalos.Add(vecinas[(dir + 1) % 4]);
                            cspalos.Add(vecinas[(dir + 3) % 4]);
                            if (palos > 2) cspalos.Add(map.getNeightbours(vecinas[(dir + 1) % 4])[(dir + 1) % 4]);

							foreach (Cell c in cspalos) addItemDatatoList(TWItem.instanceItem("Palo", c),c,nuevos);

                            List<Cell> cshojas = new List<Cell>();
                            cshojas.Add(vecinas[dir]);
                            Cell[] vecinashojas = map.getNeightbours(vecinas[dir]);

                            cshojas.Add(vecinashojas[dir]);
                            cshojas.Add(vecinashojas[(dir + 1) % 4]);
                            cshojas.Add(vecinashojas[(dir + 3) % 4]);
                            cshojas.Add(map.getNeightbours(vecinashojas[dir])[(dir + 1) % 4]);
                            cshojas.Add(map.getNeightbours(vecinashojas[dir])[(dir + 3) % 4]);

							foreach (Cell c in cshojas) addItemDatatoList(TWItem.instanceItem("Hoja", c),c,nuevos);
                        }
                        arbol = vecinas[dir];
                        vecinas = map.getNeightbours(vecinas[dir]);
                    }
					ge.setParameter("result","success");
                    break;
                }
            case 9: { break; }
            case 10: { break; }
            case 11: {
					addItemDatatoList(items[0],borrados);
                    TWItem.destroyItem(items[0]);
					pos = (Cell)this.Entity.Position;

					addItemDatatoList(TWItem.instanceItem("BrotePlantado", pos),pos,nuevos);
					ge.setParameter("result","success");
                    break;
                }
			default:{
				ge.setParameter("result","cantbeused");
				break;
			}
        }

		ge.setParameter ("NewItems", nuevos.ToArray());
		ge.setParameter ("DeletedItems", borrados.ToArray());
		ge.setParameter ("UnmodifiedItems", noHanCambiado.ToArray());

		return ge;
    }

	private void addItemDatatoList (TWItemScript item, List<ItemData> list){
		ItemData tmpId = ScriptableObject.CreateInstance<ItemData>();
		tmpId.setItem(item);
		list.Add(tmpId);
	}

	private void addItemDatatoList (TWItemScript item, Cell pos, List<ItemData> list){
		ItemData tmpId = ScriptableObject.CreateInstance<ItemData>();
		tmpId.setItem(item);
		tmpId.Coords=pos.Map.getCoords(pos.gameObject);
		list.Add(tmpId);
	}


    JSONObject JSONAble.toJSONObject() {
        JSONObject jso = new JSONObject();
        jso.AddField("salud", salud);
        jso.AddField("sed", sed);
        jso.AddField("energia", energia);
        jso.AddField("maxSalud", maxSalud);
        jso.AddField("maxFuerza", maxFuerza);
        jso.AddField("maxSed", maxSed);
        jso.AddField("maxEnergia", maxEnergia);
        jso.AddField("uses", uses);
        jso.AddField("name", name);

        Cell tmp = (Cell)this.Entity.Position;
        jso.AddField("coords", tmp.Map.getCoords(tmp.gameObject).ToString());
        jso.AddField("_instanceID", this.gameObject.GetInstanceID());
        return jso;
    }

    void JSONAble.fromJSONObject(JSONObject json) {
        this.salud = (int)json.GetField("salud").n;
        this.sed = (int)json.GetField("sed").n;
        this.energia = (int)json.GetField("energia").n;
        this.maxSalud = (int)json.GetField("maxSalud").n;
        this.maxFuerza = (int)json.GetField("maxFuerza").n;
        this.maxSed = (int)json.GetField("maxSed").n;
        this.maxEnergia = (int)json.GetField("maxEnergia").n;
        this.uses = json.GetField("uses").b;
        this.name = json.GetField("name").str;

    }

}
