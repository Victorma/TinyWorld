using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
using UnityEngine;

public class MenuBehaviour : MonoBehaviour {

    /// <summary>
    /// The show menu flag.
    /// </summary>
    public bool ShowMenu = false;

    /// <summary>
    /// The use escape key flag.
    /// </summary>
    public bool UseEscape = true;

    /// <summary>
    /// The font of the menu.
    /// </summary>
    private Font pressStart2P = null;

    /// <summary>
    /// Hides the menu and load a level.
    /// </summary>
    /// <param name="index"></param>
    private void loadLevel(int index) {
        UseEscape = true;
        ShowMenu = false;
        Application.LoadLevel(index);
    }

    /// <summary>
    /// The on start event of the behaviour.
    /// </summary>
    public void Start() {
        pressStart2P = Resources.Load<Font>("PressStart2P-Regular");
    }

    /// <summary>
    /// The on update event of the behaviour.
    /// </summary>
    public void Update() {
        if (UseEscape && Input.GetKeyDown(KeyCode.Escape)) {
            ShowMenu = !ShowMenu;
            Game.main.ShowGUI = !ShowMenu;
        }
    }

    /// <summary>
    /// The on GUI event of the behaviour.
    /// </summary>
    public void OnGUI() {
        if (!ShowMenu) return;

        var labelFont = GUI.skin.label.font;
        var labelAlignment = GUI.skin.label.alignment;
        var buttonFont = GUI.skin.button.font;

        var halfWidth = Screen.width / 2;

        GUI.skin.label.font = pressStart2P;
        GUI.skin.label.alignment = TextAnchor.MiddleCenter;
        GUI.skin.button.font = pressStart2P;

        GUI.Box(new Rect(0, 0, Screen.width, Screen.height), "");
        GUI.Label(new Rect(0, 0, Screen.width, 100), "Tiny World");

        if (GUI.Button(new Rect(halfWidth - 200, 200, 400, 100), "Simulación 1")) {
            Debug.Log("Starting simulation one...");
            loadLevel(1);
        }

        if (GUI.Button(new Rect(halfWidth - 200, 350, 400, 100), "Simulación 2")) {
            // TODO: Call the simulation 1...
            Debug.Log("The Gods made Heavy Metal!");
            //...
        }

        if (GUI.Button(new Rect(halfWidth - 200, 500, 400, 100), "Simulación 3")) {
            // TODO: Call the simulation 1...
            Debug.Log("Metal Meltdown!");
            //...
        }

        GUI.skin.label.font = labelFont;
        GUI.skin.label.alignment = labelAlignment;
        GUI.skin.button.font = buttonFont;
    }
}
