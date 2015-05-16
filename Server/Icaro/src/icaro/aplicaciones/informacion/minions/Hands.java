package icaro.aplicaciones.informacion.minions;

import org.json.JSONException;
import org.json.JSONObject;

import icaro.aplicaciones.informacion.minions.JSON.JSONAble;
import icaro.aplicaciones.informacion.minions.JSON.JSONSerializer;

public class Hands implements JSONAble {

	private int _instanceID;
	private String minionName;
	private ItemData leftHand, rightHand;
	
	
	
	public int get_instanceID() {
		return _instanceID;
	}

	public void set_instanceID(int _instanceID) {
		this._instanceID = _instanceID;
	}

	public String getMinionName() {
		return minionName;
	}

	public void setMinionName(String minionName) {
		this.minionName = minionName;
	}

	public ItemData getLeftHand() {
		return leftHand;
	}

	public void setLeftHand(ItemData leftHand) {
		this.leftHand = leftHand;
	}

	public ItemData getRightHand() {
		return rightHand;
	}

	public void setRightHand(ItemData rightHand) {
		this.rightHand = rightHand;
	}

	@Override
	public String getCorrespondingClassName() {
		return "Hands";
	}

	@Override
	public Object toJSONObject() {
		return new JSONObject();
	}

	@Override
	public void fromJSONObject(Object jsonObject) {
		JSONObject json = (JSONObject) jsonObject;
		try{
			this._instanceID = json.getInt("_instanceID");
			this.minionName = json.getString("minionName");
			this.leftHand = (ItemData) JSONSerializer.UnSerialize(json.getJSONObject("leftHand"));
			this.rightHand = (ItemData) JSONSerializer.UnSerialize(json.getJSONObject("rightHand"));
		}catch(JSONException jse){
			// Shut up :)
		}
	}

}
