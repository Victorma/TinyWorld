package icaro.aplicaciones.informacion.minions;

import org.json.JSONObject;

public interface JSONAble {
	
	public Object toJSONObject();
	public void fromJSONObject(Object jsonObject);

}
