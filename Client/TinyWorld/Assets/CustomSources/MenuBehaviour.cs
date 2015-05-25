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

    public bool ShowMenu = false;
    public bool UseEscape = true;

    private Font pressStart2P = null;

    public void Start() {
        pressStart2P = Resources.Load<Font>("PressStart2P-Regular");

    }

    public void Update() {
        if (UseEscape && Input.GetKeyDown(KeyCode.Escape)) {
            ShowMenu = !ShowMenu;
            Game.main.ShowGUI = !ShowMenu;
        }
    }

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

        if (GUI.Button(new Rect(halfWidth - 200, 200, 400, 100), "Simulation 1")) {

            Debug.Log("Starting simulation one...");

            UseEscape = true;
            ShowMenu = false;

            Application.LoadLevel(1);
        }

        if (GUI.Button(new Rect(halfWidth - 200, 350, 400, 100), "Simulation 2")) {
            // TODO: Call the simulation 1...
            Debug.Log("The Gods made Heavy Metal!");

            UseEscape = true;
            ShowMenu = false;

            Application.LoadLevel(2);
        }

        if (GUI.Button(new Rect(halfWidth - 200, 500, 400, 100), "Simulation 3")) {
            // TODO: Call the simulation 1...
            Debug.Log("Metal Meltdown!");
            //...
        }

        GUI.skin.label.font = labelFont;
        GUI.skin.label.alignment = labelAlignment;
        GUI.skin.button.font = buttonFont;
    }

}
