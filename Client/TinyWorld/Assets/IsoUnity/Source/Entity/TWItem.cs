using UnityEngine;
using System.Collections.Generic;

public abstract class TWItem : ScriptableObject {

	public abstract string Name{ get; set; }
	public abstract string Description{ get; set; }
	public abstract IsoDecoration Representation{ get; set; }
	public abstract Texture2D Image{ get; set; }

	public abstract int getManos();
	public abstract int getPeso();
	public abstract int getSalud();
	public abstract int getSed();

	public abstract bool canBeConsumed();

	public abstract void tick(TWItemScript father);
	
	//Static functions
	public static TWItemScript instanceItem(string itemname, Cell position){
		TWItem item = Instantiate(Resources.Load<TWItem>(itemname));
		
		GameObject gp = position.addDecoration(new Vector3(0,0,0), 0, false, true, item.Representation);	
		Entity enp = gp.AddComponent<Entity>();
		TWItemScript itp = gp.AddComponent<TWItemScript>();
		itp.item = item;
		enp.Position = position;
		position.Map.registerEntity(enp);

		return itp;
	}
	
	public static void destroyItem(TWItemScript item){
		Cell cs = null;
		if (item.Entity.Position is Cell)
			cs = (Cell)item.Entity.Position;
		else
			cs = (Cell) ((Entity)item.Entity.Position).Position;
		
		cs.Map.unRegisterEntity(item.Entity);
		Component.Destroy(item.Entity);
		Component.Destroy(item);
		GameObject.DestroyImmediate(item.gameObject);
	}
}
