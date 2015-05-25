package icaro.aplicaciones.informacion.minions;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import icaro.aplicaciones.informacion.minions.JSON.JSONAble;
import icaro.aplicaciones.informacion.minions.JSON.JSONSerializer;

public class MinionInfo implements JSONAble {

    private int salud, sed, energia, maxSalud, maxFuerza, maxSed, maxEnergia;
    private boolean uses;
    private String name;
    private int _instanceId;
    private Coord coords;

    private List<ItemData> availableItems;
    private List<ItemData> unavailableItems;

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getSed() {
        return sed;
    }

    public void setSed(int sed) {
        this.sed = sed;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getMaxSalud() {
        return maxSalud;
    }

    public void setMaxSalud(int maxSalud) {
        this.maxSalud = maxSalud;
    }

    public int getMaxFuerza() {
        return maxFuerza;
    }

    public void setMaxFuerza(int maxFuerza) {
        this.maxFuerza = maxFuerza;
    }

    public int getMaxSed() {
        return maxSed;
    }

    public void setMaxSed(int maxSed) {
        this.maxSed = maxSed;
    }

    public int getMaxEnergia() {
        return maxEnergia;
    }

    public void setMaxEnergia(int maxEnergia) {
        this.maxEnergia = maxEnergia;
    }

    public boolean isUses() {
        return uses;
    }

    public void setUses(boolean uses) {
        this.uses = uses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInstanceId() {
        return _instanceId;
    }

    public void setInstanceId(int _instanceId) {
        this._instanceId = _instanceId;
    }

    @Override
    public String getCorrespondingClassName() {
        return "MinionScript";
    }

    public Coord getCoords() {
        return coords;
    }

    public void setCoords(Coord coords) {
        this.coords = coords;
    }

    public void setAvailableItems(List<ItemData> items) {
        this.availableItems = items;
    }

    public List<ItemData> getAvailableItems() {
        return this.availableItems;
    }

    public void setUnavailableItems(List<ItemData> items) {
        this.unavailableItems = items;
    }

    public List<ItemData> getUnavailableItems() {
        return this.unavailableItems;
    }

    @Override
    public Object toJSONObject() {
        JSONObject jso = new JSONObject();
        try {
            jso.put("salud", salud);
            jso.put("sed", sed);
            jso.put("energia", energia);
            jso.put("maxSalud", maxSalud);
            jso.put("maxFuerza", maxFuerza);
            jso.put("maxSed", maxSed);
            jso.put("maxEnergia", maxEnergia);
            jso.put("uses", uses);
            jso.put("name", name);
            jso.put("coords", JSONSerializer.Serialize(coords));
            jso.put("_instanceID", _instanceId);

        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }
        return jso;
    }

    @Override
    public void fromJSONObject(Object jsonObject) {
        JSONObject json = (JSONObject) jsonObject;
        try {
            this.salud = json.getInt("salud");
            this.sed = json.getInt("sed");
            this.energia = json.getInt("energia");
            this.maxSalud = json.getInt("maxSalud");
            this.maxFuerza = json.getInt("maxFuerza");
            this.maxSed = json.getInt("maxSed");
            this.maxEnergia = json.getInt("maxEnergia");
            this.uses = json.getBoolean("uses");
            this.coords = (Coord) JSONSerializer.UnSerialize(json.getString("coords"));
            this.name = json.getString("name");
            this._instanceId = json.getInt("_instanceID");
        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public String toString() {
        return this.toJSONObject().toString();
    }

}
