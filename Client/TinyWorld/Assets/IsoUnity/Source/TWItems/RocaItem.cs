using UnityEngine;
using System.Collections.Generic;

public class RocaItem : TWItem {

    public List<string> usos;

    public override int Manos { get { return 1; } }
    public override int Peso { get { return 20; } }
    public override int Salud { get { return 10; } }
    public override int Sed { get { return 0; } }

    public override bool CanBeConsumed { get { return false; } }

    public override void tick(TWItemScript father) { }
}
