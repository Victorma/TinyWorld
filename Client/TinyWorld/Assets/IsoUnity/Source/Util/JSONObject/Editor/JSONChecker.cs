//#define PROFILER_TEST

using UnityEditor;
using UnityEngine;

public class JSONChecker : EditorWindow {

    /// <summary>
    /// The JSON content to check.
    /// </summary>
    string JSON = @"{
        ""TestObject"": {
		    ""SomeText"": ""Blah"",
		    ""SomeObject"": {
			    ""SomeNumber"": 42,
			    ""SomeBool"": true,
			    ""SomeNull"": null
		    },
		    ""SomeEmptyObject"": { },
		    ""SomeEmptyArray"": [ ]
	    }
    }";

    /// <summary>
    /// The JSON object with the content loaded.
    /// </summary>
    JSONObject j;

    /// <summary>
    /// Initializes the editor window.
    /// </summary>
    [MenuItem("Window/JSONChecker")]
    static void Init() {
        GetWindow(typeof(JSONChecker));
    }

    /// <summary>
    /// The on GUI event handler of the editor window.
    /// </summary>
    void OnGUI() {
        JSON = EditorGUILayout.TextArea(JSON);
        GUI.enabled = !string.IsNullOrEmpty(JSON);
        if (GUILayout.Button("Check JSON")) {
#if PROFILER_TEST
            // For testing performance of parse/stringify.
            // Turn on editor profiling to see how we're doing.
            Profiler.BeginSample("JSONParse");
			j = JSONObject.Create(JSON);
            Profiler.EndSample();
            Profiler.BeginSample("JSONStringify");
            j.ToString(true);
            Profiler.EndSample();
#else
            j = JSONObject.Create(JSON);
#endif
            Debug.Log(j.ToString(true));
        }
        if (j) {
            //Debug.Log(System.GC.GetTotalMemory(false) + "");
            if (j.type == JSONObject.Type.NULL) {
                GUILayout.Label("JSON fail:\n" + j.ToString(true));
            } else {
                GUILayout.Label("JSON success:\n" + j.ToString(true));
            }
        }
    }
}
