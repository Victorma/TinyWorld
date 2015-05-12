using UnityEngine;
using System.Collections.Generic;


public class MinionScript : EntityScript, JSONAble {
    public int salud = 0, sed = 0, energia = 0;
    public int maxSalud = 0, maxFuerza = 0, maxSed = 0, maxEnergia = 0;
    public bool uses = false;
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
        if (((string)ge.getParameter("minionname")) == this.name) {
            switch (ge.Name.ToLower()) {
                case "use":
                    this.uses = true;
                    break;
                case "modify salud":
                    saludmod = (int)ge.getParameter("salud");
                    break;
                case "modify sed":
                    sedmod = (int)ge.getParameter("sed");
                    break;
                case "modify energia":
                    energiamod = (int)ge.getParameter("energia");
                    break;
            }
        }
    }
    public override void tick() {
        if (this.saludmod != 0) { this.salud += this.saludmod; this.saludmod = 0; }
        if (this.sedmod != 0) { this.sed += this.sedmod; this.sedmod = 0; }
        if (this.energiamod != 0) { this.energia += this.energiamod; this.energiamod = 0; }
        if (this.uses) {
            //Cell[] vecinas = ((Cell)this.Entity.Position).Map.getNeightbours((Cell)this.Entity.Position);
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
            this.uses = false;
        }
    }
    public override void Update() {
    }
    public override Option[] getOptions() {
        GameEvent ge = ScriptableObject.CreateInstance<GameEvent>();
        ge.Name = "use";
        ge.setParameter("minionname", this.name);
        Option option = new Option("Usar Manos", ge, false, 0);
        return new Option[] { option };
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
                            TWItem it = items[k].item;
                            string r = it.Name, m = palabras[j];
                            if (r.Equals(m)) {
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

    public bool haveTheItems(TWItemScript[] items) {
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

        return have;
    }

    public void use(TWItemScript[] items) {

        switch (canUse(items)) {
            case 0: { //{"Roca","Roca"}
                    if (haveTheItems(items)) {
                        foreach (TWItemScript it in items) TWItem.destroyItem(it);
                        TWItem.instanceItem("RocaAfilada", (Cell)this.Entity.Position);
                    }
                    break;
                }
            case 1: { //{"Roca","Palo"}
                    if (haveTheItems(items)) {
                        foreach (TWItemScript it in items) TWItem.destroyItem(it);
                        TWItem.instanceItem("Martillo", (Cell)this.Entity.Position);
                    }
                    break;
                }
            case 2: { break; }
            case 3: {//{"Cantera","Martillo"}
                    bool rotura = Random.Range(0, 10) <= 2;
                    bool piedra = Random.Range(0, 10) <= 6;
                    bool hierro = Random.Range(0, 10) <= 4;
                    bool diamante = Random.Range(0, 10) <= 1;
                    Map map = ((Cell)this.Entity.Position).Map;

                    TWItemScript cantera = null;
                    foreach (TWItemScript it in items) {
                        if (it.item.Name == "Cantera") {
                            cantera = it;
                            break;
                        }
                    }

                    List<Cell> vecinas = new List<Cell>(map.getNeightbours((Cell)cantera.Entity.Position));

                    if (rotura) {
                        Decoration dec = cantera.GetComponent<Decoration>();
                        if (dec.Tile < 2) {
                            dec.Tile = dec.Tile + 1;
                        } else {
                            vecinas.Add((Cell)cantera.Entity.Position);
                            TWItem.destroyItem(cantera);
                        }
                    }

                    if (piedra) TWItem.instanceItem("Roca", vecinas[Random.Range(0, vecinas.Count)]);

                    break;
                }
            case 4: { break; }
            case 5: { //{"Roca Afilada","Palo"}
                    if (haveTheItems(items)) {
                        foreach (TWItemScript it in items) TWItem.destroyItem(it);
                        TWItem.instanceItem("Hacha", (Cell)this.Entity.Position);
                    }
                    break;
                }
            case 6: { break; }
            case 7: { break; }
            case 8: { //{"Arbol","Hacha"}
                    int maderas = 1 + Random.Range(0, 3);
                    int palos = 2 + Random.Range(0, 2);
                    int hojas = 5;
                    int brotes = Random.Range(0, 3);

                    Cell arbol = null;

                    foreach (TWItemScript it in items) {
                        if (it.item.Name == "Arbol") {
                            arbol = (Cell)it.Entity.Position;
                            TWItem.destroyItem(it);
                        }
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

                    foreach (Cell c in csbrotes) TWItem.instanceItem("Brote", c);

                    for (int i = 1; i <= maderas; i++) {
                        Cell cs = arbol;
                        TWItem.instanceItem("Madera", cs);

                        if (i == maderas) {
                            List<Cell> cspalos = new List<Cell>();
                            cspalos.Add(vecinas[(dir + 1) % 4]);
                            cspalos.Add(vecinas[(dir + 3) % 4]);
                            if (palos > 2) cspalos.Add(map.getNeightbours(vecinas[(dir + 1) % 4])[(dir + 1) % 4]);

                            foreach (Cell c in cspalos) TWItem.instanceItem("Palo", c);

                            List<Cell> cshojas = new List<Cell>();
                            cshojas.Add(vecinas[dir]);
                            Cell[] vecinashojas = map.getNeightbours(vecinas[dir]);

                            cshojas.Add(vecinashojas[dir]);
                            cshojas.Add(vecinashojas[(dir + 1) % 4]);
                            cshojas.Add(vecinashojas[(dir + 3) % 4]);
                            cshojas.Add(map.getNeightbours(vecinashojas[dir])[(dir + 1) % 4]);
                            cshojas.Add(map.getNeightbours(vecinashojas[dir])[(dir + 3) % 4]);

                            foreach (Cell c in cshojas) TWItem.instanceItem("Hoja", c);
                        }
                        arbol = vecinas[dir];
                        vecinas = map.getNeightbours(vecinas[dir]);
                    }

                    break;
                }
            case 9: { break; }
            case 10: { break; }
            case 11: {
                    TWItem.destroyItem(items[0]);
                    TWItem.instanceItem("BrotePlantado", (Cell)this.Entity.Position);
                    break;
                }
        }
    }

    #region JSONAble implementation


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
        jso.AddField("_instanceID", this.GetInstanceID());
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

    #endregion

}
