package icaro.aplicaciones.informacion.minions;

import icaro.aplicaciones.informacion.minions.JSON.JSONAble;

public class Coord implements JSONAble{
	private int x;
	private int y;
	
	public Coord() {
		this(0,0);
	}
	
	private void init(String s){
		init(s.substring(1, s.length() - 1).split(","));
	}
	
	private void init(String[] coords){
		init(Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
	}
	
	private void init(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Coord(String s){
		init(s.substring(1, s.length() - 1).split(","));
	}
	
	public Coord(String[] coords){
		init(Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
	}

	public Coord(int x, int y) {
		init(x,y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}

	@Override
	public Object toJSONObject() {
		return this.toString();
	}

	@Override
	public void fromJSONObject(Object jsonObject) {
		if(jsonObject instanceof String)
			init((String)jsonObject);
	}

	public int distanceTo(Coord other) {
		return Math.abs(x - other.x) + Math.abs(y - other.y);
	}

	@Override
	public String getCorrespondingClassName() {
		return null;
	}
	
}
