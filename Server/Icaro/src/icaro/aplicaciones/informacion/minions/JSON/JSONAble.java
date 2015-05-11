package icaro.aplicaciones.informacion.minions.JSON;

import org.json.JSONObject;

public interface JSONAble {
	
	public String getCorrespondingClassName();
	public Object toJSONObject();
	public void fromJSONObject(Object jsonObject);

}
