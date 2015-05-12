using UnityEngine;
using System.Collections.Generic;

public class TWItemScript : EntityScript {

    public TWItem item;

    //private bool usado = false;
    private bool comido = false;
    //private TWItemScript[] items = null;
    private bool destroy = false;
    private Inventory by;
    private GameEvent addItemEvent = null;

    public override void eventHappened(GameEvent ge) {
        switch (ge.Name.ToLower()) {
            case "usar":
                /*if(((TWItemScript) ge.getParameter("baseitem")) == this){
                    TWItemScript[] geItms = {this,this};
                    if(item.canBeUsed(geItms)){
                        this.items = geItms;
                        usado = true;
                    }
                }*/
                break;
            case "consumir":
                if (item.CanBeConsumed) {
                    comido = true;
                }
                break;
            case "event finished":
                if (ge.getParameter("event") == addItemEvent)
                    destroy = true;
                break;
        }

    }

    public override Option[] getOptions() {
        //return this.item.getOptions ();
        GameEvent ge = ScriptableObject.CreateInstance<GameEvent>();
        ge.Name = "pick item";
        ge.setParameter("item", this.item.Name);
        Option option = new Option("Pick", ge, false, 0);

        GameEvent ge2 = ScriptableObject.CreateInstance<GameEvent>();
        ge2.Name = "consumir";
        ge2.setParameter("item", this);
        Option option2 = new Option("Eat", ge2, false, 0);

        GameEvent ge3 = ScriptableObject.CreateInstance<GameEvent>();
        ge3.Name = "usar";
        //TWItemScript[] items = { this };
        ge3.setParameter("baseitem", this);
        //ge3.setParameter ("objetos", items);
        Option option3 = new Option("UsarCableado", ge3, false, 0);

        return new Option[] { option, option2, option3 };
    }

    public override void tick() {
        item.tick(this);
        if (comido) {
            /*GameEvent ge = ScriptableObject.CreateInstance<GameEvent>();
            ge.name = "add item";
            ge.setParameter("item", this.item);
            ge.setParameter("inventory", by);
            ge.setParameter("synchronous", true);
            Game.main.enqueueEvent(ge);
            addItemEvent = ge;
            picked = false;*/
        }
    }

    public override void Update() {
        if (destroy) {
            GameObject.DestroyImmediate(this.gameObject);
        }
    }
}
