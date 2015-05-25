package icaro.aplicaciones.informacion.minions;

import icaro.aplicaciones.informacion.minions.JSON.JSONAble;
import icaro.aplicaciones.informacion.minions.JSON.JSONSerializer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class GameEvent implements JSONAble {
    //****************************************************************************************************
    // Constants:
    //****************************************************************************************************

    public final static String LOGIN_EVENT = "login";
    public final static String ACTION_EVENT = "action";
    public final static String SEND_TEXT_EVENT = "send.text";
    public final static String RECEIVE_TEXT_EVENT = "receive.text";

    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************

    private String name_;
    private Map<String, Object> parameters_;

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************

    public GameEvent() {
        this("");
    }

    public GameEvent(String name) {
        name_ = name;
        parameters_ = new HashMap<>();
    }

    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************

    public String getName() {
        return name_;
    }

    public void setName(String name) {
        name_ = name;
    }

    public Object getParameter(String parameter) {
        String key = parameter.toLowerCase();
        return parameters_.containsKey(key) ? parameters_.get(key) : null;
    }

    public void setParameter(String parameter, Object obj) {
        parameters_.put(parameter.toLowerCase(), obj);
    }

    public Collection<String> getParameters() {
        return parameters_.keySet();
    }

    //****************************************************************************************************
    // Methods (public):
    //****************************************************************************************************
    
    public boolean isNameEquals(String victim) {
        return name_.equalsIgnoreCase(victim);
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name_);
            JSONObject parameters = new JSONObject();

            for (Entry<String, Object> entry : this.parameters_.entrySet()) {
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
            this.name_ = json.getString("name");
            this.parameters_ = new HashMap<String, Object>();

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
        return GameEvent.class.getSimpleName();
    }
}
