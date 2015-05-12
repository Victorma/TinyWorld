using UnityEngine;
using System.Collections.Generic;

public class CanteraItem : TWItem {

    public List<string> usos;

    public override int Manos { get { return 10; } }
    public override int Peso { get { return 1000; } }
    public override int Salud { get { return 0; } }
    public override int Sed { get { return 0; } }

    public override bool canBeConsumed() {
        return true;
    }

    public override void tick(TWItemScript father) {
    }
}
