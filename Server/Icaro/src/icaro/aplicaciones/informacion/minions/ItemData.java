package icaro.aplicaciones.informacion.minions;

import org.json.JSONException;
import org.json.JSONObject;

import icaro.aplicaciones.informacion.minions.JSON.JSONAble;
import icaro.aplicaciones.informacion.minions.JSON.JSONSerializer;

public class ItemData implements JSONAble {

	private Coord coords;
	private String name;
	private int _instanceID;
	private int _minionID;
	
	public Coord getCoords() {
		return coords;
	}

	public void setCoords(Coord coords) {
		this.coords = coords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int get_instanceID() {
		return _instanceID;
	}

	public void set_instanceID(int _instanceID) {
		this._instanceID = _instanceID;
	}

	public int get_minionID() {
		return _minionID;
	}

	public void set_minionID(int _minionID) {
		this._minionID = _minionID;
	}

	@Override
	public String getCorrespondingClassName() {
		return "ItemData";
	}

	@Override
	public Object toJSONObject() {
		JSONObject json = new JSONObject();
		try {
			json.put("_instanceID", _instanceID);
			json.put("name", name);
			json.put("coords", JSONSerializer.Serialize(coords));
			json.put("hands", _minionID);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public void fromJSONObject(Object jsonObject) {
		JSONObject json = (JSONObject) jsonObject;
		try{
			this._instanceID = json.getInt("_instanceID");
			this.name = json.getString("itemName");
			this.coords = (Coord) JSONSerializer.UnSerialize(json.getJSONObject("coords"));
			this._minionID = json.getInt("hands");
		}catch(JSONException jse){
			// Shut up :)
		}
	}

}
