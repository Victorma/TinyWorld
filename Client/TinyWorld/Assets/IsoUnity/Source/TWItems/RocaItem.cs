using UnityEngine;
using System.Collections.Generic;

public class RocaItem : TWItem {

    public List<string> usos;

    public override int getManos() { return 1; }
    public override int getPeso() { return 20; }
    public override int getSalud() { return 10; }
    public override int getSed() { return 0; }

    public override bool canBeConsumed() {
        return false;
    }

    public override void tick(TWItemScript father) { }
}
