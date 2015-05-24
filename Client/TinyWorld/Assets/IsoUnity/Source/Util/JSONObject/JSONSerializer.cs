using UnityEngine;
using System;
using System.Collections.Generic;

public class JSONSerializer {

    public static JSONObject Serialize(object jsonAble) {
        JSONObject json = new JSONObject();

        if (jsonAble is IsoUnityType) {
            json = ((JSONAble)jsonAble).toJSONObject();
        } else if (jsonAble is JSONAble) {
            json.AddField("_class", jsonAble.GetType().ToString());
            json.AddField("_data", ((JSONAble)jsonAble).toJSONObject());
        } else if (jsonAble is UnityEngine.Object) {
            json = new JSONObject(((UnityEngine.Object)jsonAble).GetInstanceID());
        }

        return json;
    }

    public static JSONAble UnSerialize(JSONObject jsonObject) {
        JSONAble r = null;

        if (jsonObject.HasField("_class") && jsonObject.HasField("_data")) {
			string c = jsonObject.GetField("_class").str;
			//string[] splitted_class = jsonObject.GetField("_class").str.Split('.');
			//string c = splitted_class[splitted_class.Length-1];

            Type t = Type.GetType(c);
			List<Type> interfaces = new List<Type>(t.GetInterfaces());

			if (interfaces.Contains(typeof(JSONAble))) {
                if (t.IsSubclassOf(typeof(ScriptableObject))) {
                    ScriptableObject so = ScriptableObject.CreateInstance(t);
					r = so as JSONAble;
                    r.fromJSONObject(jsonObject.GetField("_data"));
                }
            }
        } else if (jsonObject.IsArray) {
            r = ScriptableObject.CreateInstance<IsoUnityCollectionType>();
            r.fromJSONObject(jsonObject);
        } else if (jsonObject.IsString || jsonObject.IsNumber || jsonObject.IsBool) {
            r = ScriptableObject.CreateInstance<IsoUnityBasicType>();
            r.fromJSONObject(jsonObject);
        }

        return r;
    }

}
