package icaro.aplicaciones.informacion.minions;

import org.json.JSONObject;

public interface JSONAble {

    public JSONObject toJSONObject();

    public void fromJSONObject(JSONObject jsonObject);

}
