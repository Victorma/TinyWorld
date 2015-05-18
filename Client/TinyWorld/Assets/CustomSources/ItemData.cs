using UnityEngine;
using System.Collections;

public class ItemData : ScriptableObject, JSONAble {
    private int id;
    private Vector2 coords;
    private string nombre;
    private Hands manos;

    public int Id { get { return this.id; } set { this.id = value; } }
    public Vector2 Coords { get { return this.coords; } set { this.coords = value; } }
    public string Nombre { get { return this.nombre; } set { this.nombre = value; } }
    public Hands Manos { get { return this.manos; } set { this.manos = value; } }

    public ItemData setItem(TWItemScript source) {
        this.id = source.GetInstanceID();
        Cell tmp;
        if (source.Entity.Position is Cell)
            tmp = ((Cell)source.Entity.Position);
        else //if (source.Entity.Position is Entity)
            tmp = (Cell)((Entity)source.Entity.Position).Position;

        this.coords = tmp.Map.getCoords(tmp.gameObject);
        this.nombre = source.item.Name;

        if (source.Entity.Position is Entity)
            manos = ((Entity)source.Entity.Position).GetComponent<Hands>();
        return this;
    }

    public JSONObject toJSONObject() {
        JSONObject js = new JSONObject();
        js.AddField("_instanceID", id);
        js.AddField("name", nombre);
        js.AddField("coords", coords.ToString());
        if (manos != null)
            js.AddField("hands", manos.gameObject.GetInstanceID());

        return js;
    }
    public void fromJSONObject(JSONObject json) {
        this.id = (int)json.GetField("_instanceID").n;
        this.coords = (Vector2)VectorUtil.getVQ(json.GetField("coords").str);
        this.nombre = json.GetField("name").str;

        Hands[] tmp = GameObject.FindObjectsOfType<Hands>();

        foreach (Hands h in tmp) {
            if (h.gameObject.GetInstanceID() == ((int)json.GetField("hands").n)) {
                this.manos = h;
                break;
            }
        }
    }

}