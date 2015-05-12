using UnityEngine;
using System.Collections.Generic;

public class BrotePlantadoItem : TWItem {

    public override int Manos { get { return 10; } }
    public override int Peso { get { return 1000; } }
    public override int Salud { get { return 0; } }
    public override int Sed { get { return 0; } }

    public override bool CanBeConsumed { get { return false; } }

    private int mytick = 0;

    public override void tick(TWItemScript father) {
        float t = 0.05f * Time.deltaTime;
        if (Random.Range(0f, 1f) < t) {
            mytick++;
            if (mytick >= 1) {
                TWItem.instanceItem("Arbol", (Cell)father.Entity.Position).Entity.isBlackList = false;
                TWItem.destroyItem(father);
            }
        }
    }
}
