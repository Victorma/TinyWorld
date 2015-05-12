using UnityEngine;
using System.Collections.Generic;

public class PaloItem : TWItem {

    public List<string> usos;

    public override int getManos() { return 1; }
    public override int getPeso() { return 25; }
    public override int getSalud() { return 0; }
    public override int getSed() { return 0; }

    public override bool canBeConsumed() {
        return true;
    }

    public override void tick(TWItemScript father) { }
}
