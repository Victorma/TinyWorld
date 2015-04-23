package icaro.aplicaciones.informacion.game_manager;

import org.json.JSONObject;

public interface JSONAble {
	
	public JSONObject toJSONObject();
	public void fromJSONObject(JSONObject jsonObject);

}
