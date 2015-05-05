using UnityEngine;
using System.Collections.Generic;

public class Hands : EntityScript {

	private List<TWItemScript> itemsToPick = new List<TWItemScript> ();
	private bool dropLeft = false, dropRight = false;
	public TWItemScript leftHand = null, rightHand = null;
	private List<GameEvent> events = new List<GameEvent>();
	
	public override void eventHappened (GameEvent ge)
	{
		switch (ge.Name.ToLower()) 
		{
		case "pick item": 
			Map map = ((Cell) this.Entity.Position).Map;
			List<Cell> vecinas = new List<Cell>(map.getNeightbours((Cell) this.Entity.Position));
			vecinas.Add((Cell) this.Entity.Position);
			string itemname = (string)ge.getParameter("Item");
			TWItemScript item = null;
			foreach(Cell c in vecinas){
				List<Entity> entities = new List<Entity>(c.getEntities());
				foreach(Entity e in entities){
					TWItemScript its = e.GetComponent<TWItemScript>();
					if(its!=null){
						if(its.item.Name == itemname){
							item = its;
							break;
						}
					}
				}
				if(item!=null)
					break;
			}

			if(item!=null){
				if(canPick(item)){
					itemsToPick.Add(item);
					events.Add(ge);
				}
			}
			break;
		case "drop leftitem": 
			if(ge.getParameter("Hands") == this || ge.getParameter("Hands") == this.Entity.gameObject){
				dropLeft = true;
				events.Add(ge);
			}
			break;
		case "drop rightitem": 
			//if(ge.getParameter("Hands") == this || ge.getParameter("Hands") == this.Entity.gameObject){
				dropRight = true;
				events.Add(ge);
			//}
			break;
		}
	}
	
	public override Option[] getOptions ()
	{
		if (this.Entity.GetComponent<Player> () != null) {
			GameEvent ge = ScriptableObject.CreateInstance<GameEvent> (),
			ge2 = ScriptableObject.CreateInstance<GameEvent> ();
			ge.Name = "drop leftitem";
			ge2.Name = "drop rightitem";
			ge.setParameter ("Hands", this);
			ge2.setParameter ("Hands", this);
			Option option = new Option ("LeftHand", ge, false, 0),
			option2 =  new Option ("RightHand", ge2, false, 0);
			return new Option[]{option,option2};
		} else
			return new Option[]{};
	}
	
	public override void tick ()
	{
		while (itemsToPick.Count > 0) { 
			if(itemsToPick[0].item.getManos()==2){
				if(rightHand==null&&leftHand==null){
					rightHand=itemsToPick[0];
					leftHand=itemsToPick[0];
					itemsToPick[0].GetComponent<Entity>().Position = this.Entity;
				}
			}else if(itemsToPick[0].item.getManos()==1){
				if(rightHand==null){
					rightHand=itemsToPick[0];
					itemsToPick[0].GetComponent<Entity>().Position = this.Entity;
				}else if(leftHand==null){
					leftHand=itemsToPick[0];
					itemsToPick[0].GetComponent<Entity>().Position = this.Entity;
				}
			}
			itemsToPick.Remove(itemsToPick[0]);
		}

		if (dropLeft) {
			if(leftHand!=null){
				Cell[] vecinas = ((Cell) this.Entity.Position).Map.getNeightbours((Cell) this.Entity.Position);
				foreach(Cell v in vecinas)
					if(v.getEntities().Length == 0){
						leftHand.Entity.Position = v;
						if(leftHand==rightHand) rightHand=null;
						leftHand = null;
						break;
					}
				dropLeft = false;
			}
		}

		if (dropRight) {
			if(rightHand!=null){
				Cell[] vecinas = ((Cell) this.Entity.Position).Map.getNeightbours((Cell) this.Entity.Position);
				foreach(Cell v in vecinas)
					if(v!=null)
					if(v.getEntities().Length == 0){
						rightHand.Entity.Position = v;
						if(leftHand==rightHand) leftHand=null;
						rightHand = null;
						break;
					}
				dropRight = false;
			}
		}

		
		
		while (events.Count > 0) { 
			Game.main.eventFinished(events[0]);
			events.RemoveAt(0);
		}
	}
	
	public override void Update (){}
	
	[SerializeField]
	private List<Item> items = new List<Item> ();
	public Item[] Items{
		get{return items.ToArray() as Item[];}
	}

	private bool hayHueco(TWItem item){
		bool hueco = false;
		switch (item.getManos ()) {
			case 1:
				hueco = leftHand==null || rightHand==null;
				break;
			case 2:
				hueco = leftHand==null && rightHand==null;
				break;
			default:
				hueco = false;
				break;
		}
		return hueco;
	}

	private int pesoManos(){
		int peso = 0;

		if (leftHand != null)	peso += leftHand.item.getPeso ();
		if (rightHand != null)	peso += rightHand.item.getPeso ();

		return peso;
	}

	public bool canPick(TWItemScript item){
		MinionScript minion = this.GetComponent<MinionScript>();
		bool pickable = false;
		
		if (hayHueco (item.item)) {
			int it = item.item.getPeso(), man = this.pesoManos(), fu = minion.maxFuerza;
			if((it+man)<fu)
				pickable = true;
		}

		return pickable;
	}
	
}
