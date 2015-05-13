using UnityEngine;
using System.Collections.Generic;

public abstract class TWItem : ScriptableObject {

    public new string name;
    public string description;
    public Texture2D image;
    public IsoDecoration decoration;

    public string Name {
        get { return name; }
        set { name = value; }
    }
    public string Description {
        get { return description; }
        set { description = value; }
    }
    public IsoDecoration Representation {
        get { return decoration; }
        set { decoration = value; }
    }
    public Texture2D Image {
        get { return image; }
        set { image = value; }
    }

    public abstract int Manos { get; }
    public abstract int Peso { get; }
    public abstract int Salud { get; }
    public abstract int Sed { get; }

    public abstract bool CanBeConsumed { get; }

    public abstract void tick(TWItemScript father);

    //Static functions
    public static TWItemScript instanceItem(string itemname, Cell position) {
        TWItem item = Instantiate(Resources.Load<TWItem>(itemname));

        GameObject gp = position.addDecoration(new Vector3(0, 0, 0), 0, false, true, item.Representation);
        Entity enp = gp.AddComponent<Entity>();
        TWItemScript itp = gp.AddComponent<TWItemScript>();
        itp.item = item;
        enp.Position = position;
        position.Map.registerEntity(enp);

        return itp;
    }

    public static void destroyItem(TWItemScript item) {
        Cell cs = null;
        if (item.Entity.Position is Cell) {
            cs = (Cell)item.Entity.Position;
        } else {
            cs = (Cell)((Entity)item.Entity.Position).Position;
        }

        cs.Map.unRegisterEntity(item.Entity);
        Component.Destroy(item.Entity);
        Component.Destroy(item);
        GameObject.DestroyImmediate(item.gameObject);
    }
}
