using UnityEngine;
using System.Collections.Generic;
using System.Linq;

/// <summary>
/// This class represents a game event inside the simulation.
/// </summary>
[System.Serializable]
public class GameEvent : ScriptableObject, JSONAble {
    //****************************************************************************************************
    // Constants:
    //****************************************************************************************************

    public const string LOGIN_EVENT = "login";
    public const string ACTION_EVENT = "action";
    public const string SEND_TEXT_EVENT = "send.text";
    public const string RECEIVE_TEXT_EVENT = "receive.text";

    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************

    /// <summary>
    /// The keys of the game event.
    /// </summary>
    [SerializeField]
    private List<string> keys = new List<string>();

    /// <summary>
    /// The values of the game event.
    /// </summary>
    [SerializeField]
    private List<Object> values = new List<Object>();

    /// <summary>
    /// The keys and values of the game event.
    /// </summary>
    private Dictionary<string, Object> args = new Dictionary<string, Object>();

    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************

    /// <summary>
    /// Gets or sets the name of the game event.
    /// </summary>
    /// <remarks>This is used to tell the type of event to the system.</remarks>
    [SerializeField]
    public string Name {
        get { return name; }
        set { this.name = value; }
    }

    /// <summary>
    /// Gets all the key names of the parameters of the game event.
    /// </summary>
    public string[] Params {
        get { return args.Keys.ToArray(); }
    }

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    /// <summary>
    /// This method is called when the scriptable object is awaked in the instantiation.
    /// </summary>
    void Awake() {
        if (args == null || args.Count != keys.Count) {
            args = new Dictionary<string, Object>();
            for (int i = 0; i < keys.Count; i++) {
                args.Add(keys[i], values[i]);
            }
        }
    }

    /// <summary>
    /// Checks if a parameter exists inside the game event.
    /// </summary>
    /// <param name="param">The name of the parameter.</param>
    /// <returns>true if the parameter exists inside the game event.</returns>
    public bool containsParameter(string param) {
        return args.ContainsKey(param.ToLower());
    }

    /// <summary>
    /// Gets a parameter from the game event.
    /// </summary>
    /// <param name="param">The name of the parameter.</param>
    /// <returns>The value of the parameter.</returns>
    public object getParameter(string param) {
        param = param.ToLower();
        if (args.ContainsKey(param))
            return (args[param] is IsoUnityType) ? ((IsoUnityType)args[param]).Value : args[param];
        else
            return null;
    }

    /// <summary>
    /// Sets a parameter from the game event.
    /// </summary>
    /// <param name="param">The name of the parameter.</param>
    /// <param name="content">The value of the parameter.</param>
    public void setParameter(string param, object content) {
        param = param.ToLower();
        object c = IsoUnityTypeFactory.Instance.getIsoUnityType(content);
        if (c == null) {
            c = content;
        }

        if (args.ContainsKey(param)) {
            args[param] = (Object)c;
        } else {
            args.Add(param, (Object)c);
        }

        this.keys = new List<string>(args.Keys);
        this.values = new List<Object>(args.Values);
    }

    /// <summary>
    /// Removes a parameter from the game event.
    /// </summary>
    /// <param name="param">The name of the parameter.</param>
    public void removeParameter(string param) {
        param = param.ToLower();
        if (args.ContainsKey(param)) {
            UnityEngine.Object v = args[param];
            if (v is IsoUnityBasicType) {
                IsoUnityTypeFactory.Instance.Destroy(v as IsoUnityBasicType);
            }
            args.Remove(param);
        }

        this.keys = new List<string>(args.Keys);
        this.values = new List<Object>(args.Values);
    }

    /// <summary>
    /// Determines whether the specified object is equal to the current object.
    /// </summary>
    /// <param name="o">The object to compare with the current object.</param>
    /// <returns>true if the specified object is equal to the current object; otherwise, false.</returns>
    public override bool Equals(object o) {
        return this == o;
    }

    /// <summary>
    /// Serves as the default hash function.
    /// </summary>
    /// <returns>A hash code for the current object.</returns>
    public override int GetHashCode() {
        return base.GetHashCode();
    }

    /// <summary>
    /// Checks if two objects are the same.
    /// </summary>
    /// <param name="ge1">The left hand side value.</param>
    /// <param name="ge2">The right hand side value.</param>
    /// <returns>true if the objects are the same.</returns>
    public static bool operator ==(GameEvent ge1, GameEvent ge2) {
        // http://msdn.microsoft.com/en-us/library/ms173147(v=vs.80).aspx
        // If both are null, or both are same instance, return true.
        if (System.Object.ReferenceEquals(ge1, ge2)) {
            return true;
        }

        // If one is null, but not both, return false.
        if (((object)ge1 == null) || ((object)ge2 == null)) {
            return false;
        }

        bool result = ge1.Name.ToLower().Equals(ge2.Name.ToLower()) && ge1.args.Count == ge2.args.Count;
        if (result) {
            foreach (string arg in ge1.args.Keys) {
                result = ge2.args.ContainsKey(arg) && (ge2.args[arg] == ge1.args[arg]);
                if (!result) break;
            }
        }

        return result;
    }

    /// <summary>
    /// Checks if two objects are not the same.
    /// </summary>
    /// <param name="ge1">The left hand side value.</param>
    /// <param name="ge2">The right hand side value.</param>
    /// <returns>true if the objects are not the same.</returns>
    public static bool operator !=(GameEvent ge1, GameEvent ge2) {
        return !(ge1 == ge2);
    }

    /// <summary>
    /// Serializes the game event into a JSON object.
    /// </summary>
    /// <returns>The game event serialized into a JSON object.</returns>
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        json.AddField("name", name);
        JSONObject parameters = new JSONObject();
        foreach (KeyValuePair<string, Object> entry in args) {
            if (entry.Value is JSONAble) {
                var jsonAble = entry.Value as JSONAble;
                parameters.AddField(entry.Key, JSONSerializer.Serialize(jsonAble));
            } else {
                parameters.AddField(entry.Key, entry.Value.GetInstanceID());
            }
        }
        json.AddField("parameters", parameters);
        return json;
    }

    /// <summary>
    /// Destroy basic values inside a table of parameters.
    /// </summary>
    /// <param name="args">The table of parameters.</param>
    private static void destroyBasic(Dictionary<string, Object> args) {
        if (args == null || args.Count == 0) return;
        foreach (KeyValuePair<string, Object> entry in args) {
            if (entry.Value is IsoUnityBasicType) {
                IsoUnityBasicType.DestroyImmediate(entry.Value);
            }
        }
    }

    /// <summary>
    /// Deserializes a JSON object into the game event.
    /// </summary>
    /// <param name="json">The game event serialized into a JSON object.</param>
    public void fromJSONObject(JSONObject json) {
        this.name = json["name"].str;

        //Clean basic types
        destroyBasic(this.args);

        this.args = new Dictionary<string, Object>();

        JSONObject parameters = json["parameters"];
        foreach (string key in parameters.keys) {
            JSONObject param = parameters[key];
            JSONAble unserialized = JSONSerializer.UnSerialize(param);
            this.setParameter(key, unserialized);
        }
    }
}
