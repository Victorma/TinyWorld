package icaro.aplicaciones.informacion.minions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

public class GameEvent implements JSONAble {

    public String name;
    private Map<String, Object> parameters;

    public GameEvent() {
        this("");
    }

    public GameEvent(String name) {
        this.name = name;
        this.parameters = new HashMap<String, Object>();
    }

    public Object getParameter(String parameter) {
        return parameters.containsKey(parameter) ? parameters.get(parameter) : null;
    }

    public void setParameter(String parameter, String value) {
        parameters.put(parameter, value);
    }

    public Collection<String> getParameters() {
        return parameters.keySet();
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put("name", name);

            JSONObject paramObject = new JSONObject();
            for (Entry<String, Object> entry : parameters.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof JSONAble) {
                    value = ((JSONAble) value).toJSONObject();
                }
                paramObject.put(entry.getKey(), value);
            }

            json.put("parameters", paramObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    public void fromJSONObject(JSONObject jsonObject) {

        try {
            this.name = jsonObject.getString("name");
            this.parameters = new HashMap<String, Object>();

            JSONObject paramObject = jsonObject.getJSONObject("parameters");
            Iterator<String> keys = paramObject.keys();
            String key = null;
            while (keys.hasNext()) {
                key = keys.next();

                Object param;
                param = paramObject.get(key);
                if (param instanceof JSONObject) {
                    GameEvent child = new GameEvent();
                    child.fromJSONObject((JSONObject) param);
                    parameters.put(key, child);
                } else {
                    parameters.put(key, param);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
