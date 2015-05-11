package icaro.aplicaciones.informacion.minions.JSON;

import icaro.aplicaciones.informacion.minions.Coord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reflections.Reflections;


public class JSONSerializer  {
	
	private static List<JSONAble> prototypes;
	
	private static void extractPrototypes(){
		Reflections reflections = new Reflections("icaro.aplicaciones.informacion");    
		Set<Class<? extends JSONAble>> classes = reflections.getSubTypesOf(JSONAble.class);
		
		prototypes = new ArrayList<JSONAble>();
		
		for(Class<? extends JSONAble> c : classes){
			try {
				prototypes.add(c.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static JSONAble findPrototype(String className){
		if(prototypes == null)
			extractPrototypes();
		
		JSONAble proto = null;
		for(JSONAble current : prototypes){
			if(current.getCorrespondingClassName() != null && current.getCorrespondingClassName().equals(className)){
				proto = current;
				break;
			}
		}
		return proto;
	}
	
	private static boolean isBasic(Object o){
		return o instanceof Boolean || o instanceof Integer || o instanceof Double || o instanceof Long || o instanceof String;
	}
	
	private static boolean isArray(Object o){
		return o instanceof Collection;
	}

    public static Object Serialize(Object jsonAble)
    {
        Object json = new JSONObject();	

        if (isBasic(jsonAble))
        {
            json = jsonAble;
        }
        else if(isArray(jsonAble))
        {
        	json = new JSONArray((Collection)jsonAble);
        }
        else if(jsonAble instanceof JSONAble)
        {
        	JSONObject jso = (JSONObject) json;
        	try {
				jso.put("_class", jsonAble.getClass().getName());
	        	jso.put("_data", ((JSONAble)jsonAble).toJSONObject());
			} catch (JSONException e) {
				e.printStackTrace();
			}  
        }

        return json;
    }

    public static Object UnSerialize(Object jsonObject){
    	Object r = null;

        if(isBasic(jsonObject)){
        	r = jsonObject;
        	
        	//Special String cases
        	if(jsonObject instanceof String){
        		try{
        			Coord coord = new Coord();
        			coord.fromJSONObject(jsonObject);
        			r = coord;
        		}catch(Exception e){}
        	}
        } 
        else if(jsonObject instanceof JSONArray){
        	List<Object> objects = new ArrayList<Object>();
        	JSONArray array = (JSONArray)jsonObject;
        	for(int i = 0; i<array.length(); i++){
        		try {
					objects.add(UnSerialize(array.get(i)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
        	}
        	r = objects;
        }
        else if (jsonObject instanceof JSONObject){
        	JSONObject jso = (JSONObject)jsonObject;
        	String _class = jso.optString("_class");
        	JSONObject _data = jso.optJSONObject("_data");
        	
        	if(_class!=null && _data != null){
        		JSONAble proto = findPrototype(_class);
        		if(proto!=null){
	        		try {
						JSONAble instance = proto.getClass().newInstance();
						instance.fromJSONObject(_data);
						r = instance;
					} catch (InstantiationException |IllegalAccessException e) {}
        		}
        	}
        }

        return r;
    }

}
