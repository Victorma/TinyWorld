using UnityEngine;
using System.Collections.Generic;

public class ArbolItem : TWItem {

	public List<string> usos;

    public override int Manos { get { return 10; } }
    public override int Peso { get { return 1000; } }
    public override int Salud { get { return 0; } }
    public override int Sed { get { return 0; } }
	
	public override bool canBeConsumed(){
		return true;
	}
	
	public override void tick(TWItemScript father){
		float t = 0.02f * Time.deltaTime;
		if (Random.Range (0f, 1f) < t) {
			Cell[] vecinas = ((Cell)father.Entity.Position).Map.getNeightbours (((Cell)father.Entity.Position));
			List<Cell> vecinasUsables = new List<Cell>();
			foreach(Cell v in vecinas)
				if(v!=null&&v.getEntities().Length==0) vecinasUsables.Add(v);

			if(vecinasUsables.Count>0){
				Cell cs =  vecinasUsables[Random.Range (0, vecinasUsables.Count)];
				//TWItem item;
				if(Random.Range(0,2)==0)
					TWItem.instanceItem("Palo",cs);
				else
					TWItem.instanceItem("Hoja",cs);
			}
		}
	}
}
