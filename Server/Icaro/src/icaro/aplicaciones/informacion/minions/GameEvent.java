package icaro.aplicaciones.informacion.minions;

import icaro.aplicaciones.informacion.minions.JSON.JSONAble;
import icaro.aplicaciones.informacion.minions.JSON.JSONSerializer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

public class GameEvent implements JSONAble {

    //public static String INICIAR_PARTIDA = "IniciarPartida";
    public String name;
    private Map<String, Object> parameters;

    public GameEvent() {
        this("");
    }

    public GameEvent(String name) {
        this.name = name;
        this.parameters = new HashMap<String, Object>();
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getParameter(String parameter) {
        return parameters.containsKey(parameter.toLowerCase()) ? parameters.get(parameter.toLowerCase()) : null;
    }

    public void setParameter(String parameter, Object obj) {
        parameters.put(parameter.toLowerCase(), obj);
    }

    public Collection<String> getParameters() {
        return parameters.keySet();
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            JSONObject parameters = new JSONObject();

            for (Entry<String, Object> entry : this.parameters.entrySet()) {
                parameters.put(entry.getKey(), JSONSerializer.Serialize(entry.getValue()));
            }

            json.put("parameters", parameters);
        } catch (JSONException e) {
            e.printStackTrace(System.err);
        }
        return json;
    }

    public void fromJSONObject(Object o) {
        try {
            JSONObject json = (JSONObject) o;
            this.name = json.getString("name");
            this.parameters = new HashMap<String, Object>();

            JSONObject parameters = json.getJSONObject("parameters");
            Iterator<String> keyIterator = parameters.keys();

            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                this.setParameter(key, JSONSerializer.UnSerialize(parameters.get(key)));
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public String getCorrespondingClassName() {
        return "GameEvent";
    }

}
