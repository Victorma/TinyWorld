package icaro.aplicaciones.informacion.minions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
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

	public void setParameter(String parameter, Object obj) {
		parameters.put(parameter, obj);
	}

	public Collection<String> getParameters() {
		return parameters.keySet();
	}
	
	@Override
	public String toString() {
		return toJSONObject().toString();
	}
	
	@Override
	public JSONObject toJSONObject(){
		JSONObject json = null;
		try {
			json = new JSONObject();
			json.put("name", name);
			

	        JSONObject paramObject = new JSONObject();
	        for(Entry<String, Object> entry : parameters.entrySet())
	        {
	        	Object value = entry.getValue();
	        	if(value instanceof JSONAble){
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
	
	private Object parseJSONObject(Object jso){
		Object o = jso;
		if(jso instanceof JSONArray){
			JSONArray a = (JSONArray) jso;
			List<Object> list = new ArrayList<Object>();
			try{
				for(int i = 0; i<a.length(); i++)
					list.add(parseJSONObject(a.get(i)));
			}catch(JSONException jse){}
		}else if (jso instanceof JSONObject) {
			GameEvent child = new GameEvent();
			child.fromJSONObject((JSONObject) jso);
			o = child;
		}
		
		return o;
		
	}

	@Override
	public void fromJSONObject(Object o) {
		if(o instanceof JSONObject){
			try {
				JSONObject jsonObject = (JSONObject)o;
				this.name = jsonObject.getString("name");
				this.parameters = new HashMap<String, Object>();
	
				JSONObject paramObject = jsonObject.getJSONObject("parameters");
				@SuppressWarnings("unchecked")
				Iterator<String> keys = paramObject.keys();
				String key = null;
				while (keys.hasNext()) {
					key = keys.next();
					this.parameters.put(key, parseJSONObject(paramObject.get(key)));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
