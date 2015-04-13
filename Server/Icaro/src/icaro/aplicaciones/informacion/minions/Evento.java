package icaro.aplicaciones.informacion.minions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Evento {
	
	public String name;
	private Map<String,Object> parameters;
	
	public Evento(){
		this("");
	}
	
	public Evento(String name){
		this.name = name;
		this.parameters = new HashMap<String, Object>();
	}
	
	public Object getParameter(String parameter){
		return parameters.containsKey(parameter) ? parameters.get(parameter) : null;
	}
	
	public void setParameter(String parameter, String value){
		parameters.put(parameter, value);
	}
	
	public Collection<String> getParameters(){
		return parameters.keySet();
	}
	
	private void putParameter(String name, Object value, StringBuilder sb, boolean comaEnd){
		
		if(value instanceof Map)
			value = new JSONMap((Map<String,Object>) value);
		
		//TODO lists
		
		sb.append('"').append(name).append("\":\"").append(value.toString()).append(',');
		if(comaEnd)
			sb.append(',');
	}
	
	private class JSONMap {
		private Map<String,Object> map;
		public JSONMap(Map<String, Object> map){
			this.map = map;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder('{');
			
			int i = 0, s = map.size();
			for(Entry<String,Object> e : map.entrySet()){
				i++;
				putParameter(e.getKey(), e.getValue(), sb, i != s);
			}
			
			return sb.append('}').toString();
		}
	}
	
	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder("{");
		putParameter("name",name,sb,true);
		putParameter("parameters",parameters, sb, false);
		
		return sb.toString();
	}
}
