using UnityEngine;
using System.Collections.Generic;

public class PaloItem : TWItem {

    public List<string> usos;

    public override int Manos { get { return 1; } }
    public override int Peso { get { return 25; } }
    public override int Salud { get { return 0; } }
    public override int Sed { get { return 0; } }

    public override bool canBeConsumed() {
        return true;
    }

    public override void tick(TWItemScript father) { }
}
