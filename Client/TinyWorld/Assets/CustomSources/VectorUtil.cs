using System;
using UnityEngine;
using System.Collections.Generic;
using System.Linq;
using System.Text;

class VectorUtil {
    public static object getVQ(string vq) {
        if (vq[0] != '(' || vq[vq.Length - 1] != ')')
            return null;

        string[] temp = vq.Substring(1, vq.Length - 2).Split(',');
        object r = null;
        switch (temp.Length) {
            case 2: r = getVector2(temp); break;
            case 3: r = getVector3(temp); break;
            case 4: r = getVector4(temp); break;
        }

        return r;
    }

    public static Vector3 getVector3(string[] temp) {
        float x = float.Parse(temp[0]);
        float y = float.Parse(temp[1]);
        float z = float.Parse(temp[2]);
        Vector3 rValue = new Vector3(x, y, z);
        return rValue;
    }

    public static Vector4 getVector4(string[] temp) {
        float x = float.Parse(temp[0]);
        float y = float.Parse(temp[1]);
        float z = float.Parse(temp[2]);
        float w = float.Parse(temp[3]);
        Vector4 rValue = new Vector4(x, y, z, w);
        return rValue;
    }

    public static Vector2 getVector2(string[] temp) {
        float x = float.Parse(temp[0]);
        float y = float.Parse(temp[1]);
        Vector2 rValue = new Vector2(x, y);
        return rValue;
    }
}
