using UnityEngine;
using System.Collections.Generic;

public class BrotePlantadoItem : TWItem {
	
	public string name;
	public string description;
	public Texture2D image;
	public IsoDecoration decoration;
	
	public override string Name{get{return name;} set{name = value;} }
	public override string Description{get{return description;} set{description = value;} }
	public override IsoDecoration Representation{get{return decoration;} set{decoration = value;} }
	public override Texture2D Image{get{return image;} set{image = value;} }
	
	public override int getManos(){return 10;}
	public override int getPeso(){return 1000;}
	public override int getSalud(){return 0;}
	public override int getSed(){return 0;}
	
	public override bool canBeConsumed(){
		return false;
	}

	private int mytick = 0;

	public override void tick(TWItemScript father){
		float t = 0.05f * Time.deltaTime;
		if (Random.Range (0f, 1f) < t) {
			mytick++;
			if (mytick >= 1) {
				TWItem.instanceItem("Arbol",(Cell)father.Entity.Position).Entity.isBlackList = false;
				TWItem.destroyItem(father);
			}
		}
	}
}
