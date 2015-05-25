using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
using UnityEngine;

/// <summary>
/// This class represents the menu behaviour inside the simulation.
/// </summary>
public class MenuBehaviour : MonoBehaviour {
    //****************************************************************************************************
    // Types:
    //****************************************************************************************************

    /// <summary>
    /// The menu screen types.
    /// </summary>
    public enum MenuScreenType {
        None, Menu, Game
    }

    //****************************************************************************************************
    // Constants:
    //****************************************************************************************************

    private const float TOP_HEIGHT_FACTOR = 0.16f;
    private const float BUTTON_WIDTH = 64.0f;
    private const float BUTTON_HEIGHT = 64.0f;

    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************

    /// <summary>
    /// The current menu screen type.
    /// </summary>
    public MenuScreenType CurrentScreen = MenuScreenType.Menu;

    /// <summary>
    /// The use escape key flag.
    /// </summary>
    public bool UseEscape = true;

    /// <summary>
    /// The font of the menu.
    /// </summary>
    private Font pressStart2P = null;

    /// <summary>
    /// The scroll position in the received text.
    /// </summary>
    private Vector2 scrollPosition = Vector2.zero;

    // GUI custom styles:
    private GUIStyle menuLabelStyle = null;
    private GUIStyle menuButtonStyle = null;
    private GUIStyle gameLabelStyle = null;
    private GUIStyle gameTextFieldStyle = null;
    private GUIStyle gameButtonStyle = null;

    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************

    /// <summary>
    /// The received text in the "chat" of the simulation.
    /// </summary>
    public string ReceivedText { get; private set; }

    /// <summary>
    /// The message text to send in the "chat" of the simulation.
    /// </summary>
    public string MessageText { get; private set; }

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    /// <summary>
    /// Hides the menu and load a level.
    /// </summary>
    /// <param name="index"></param>
    private void loadLevel(int index) {
        CurrentScreen = MenuScreenType.None;
        UseEscape = true;
        Application.LoadLevel(index);
        CurrentScreen = (index == 0) ? MenuScreenType.Menu : MenuScreenType.Game;
        updateBlockedAreas();
    }

    /// <summary>
    /// The menu on GUI event of the behaviour.
    /// </summary>
    private void onMenuGUI() {
        if (menuLabelStyle == null) {
            menuLabelStyle = new GUIStyle(GUI.skin.label);
            menuLabelStyle.font = pressStart2P;
            menuLabelStyle.alignment = TextAnchor.MiddleCenter;
            menuButtonStyle = new GUIStyle(GUI.skin.button);
            menuButtonStyle.font = pressStart2P;
        }

        var halfWidth = Screen.width / 2;

        GUI.Box(new Rect(0, 0, Screen.width, Screen.height), "");
        GUI.Label(new Rect(0, 0, Screen.width, 100), "Tiny World", menuLabelStyle);

        if (GUI.Button(new Rect(halfWidth - 200, 200, 400, 100), "Simulación 1", menuButtonStyle)) {
            Debug.Log("Starting simulation one...");
            loadLevel(1);
        }

        if (GUI.Button(new Rect(halfWidth - 200, 350, 400, 100), "Simulación 2", menuButtonStyle)) {
            // TODO: Call the simulation 1...
            Debug.Log("The Gods made Heavy Metal!");

            loadLevel(2);
        }

        if (GUI.Button(new Rect(halfWidth - 200, 500, 400, 100), "Simulación 3", menuButtonStyle)) {
            // TODO: Call the simulation 1...
            Debug.Log("Metal Meltdown!");
            //...
        }
    }

    /// <summary>
    /// The game on GUI event of the behaviour.
    /// </summary>
    private void onGameGUI() {
        if (gameLabelStyle == null) {
            gameLabelStyle = new GUIStyle(GUI.skin.label);
            gameLabelStyle.font = pressStart2P;
            gameLabelStyle.wordWrap = true;
            gameTextFieldStyle = new GUIStyle(GUI.skin.textField);
            gameTextFieldStyle.font = pressStart2P;
            gameTextFieldStyle.wordWrap = true;
            gameButtonStyle = new GUIStyle(GUI.skin.button);
            gameButtonStyle.font = pressStart2P;
        }

        bool enterPressed = false;
        Event e = Event.current;
        if (e.type == EventType.KeyDown && e.keyCode == KeyCode.Return &&
            GUI.GetNameOfFocusedControl() == "MessageTextInput") {
            enterPressed = true;
        }

        var rtp = new Rect(0, 0, Screen.width, Screen.height * TOP_HEIGHT_FACTOR);
        var size = gameTextFieldStyle.CalcHeight(new GUIContent(ReceivedText), rtp.width - 32.0f);
        var labelHeight = size < rtp.height ? rtp.height - 8.0f : size;
        scrollPosition = GUILayout.BeginScrollView(scrollPosition, false, true,
            GUILayout.Width(rtp.width), GUILayout.Height(rtp.height));
        GUILayout.Label(ReceivedText, gameTextFieldStyle, GUILayout.Height(labelHeight));
        GUILayout.EndScrollView();

        var mtp = new Rect(0, Screen.height - BUTTON_HEIGHT, Screen.width - BUTTON_WIDTH, BUTTON_HEIGHT);
        var mbp = new Rect(Screen.width - BUTTON_WIDTH, Screen.height - BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        GUI.SetNextControlName("MessageTextInput");
        MessageText = GUI.TextField(mtp, MessageText, gameTextFieldStyle);
        if (GUI.Button(mbp, "Enviar") && !string.IsNullOrEmpty(MessageText) || enterPressed) {
            GameEvent victim = GameEvent.CreateInstance<GameEvent>();
            victim.Name = GameEvent.SEND_TEXT_EVENT;
            victim.setParameter("message", cleanString(MessageText));
            victim.setParameter("Syncronized", true);
            Game.main.enqueueEvent(victim);
            MessageText = "";
        }
    }

    /// <summary>
    /// Cleans a string to avoid some problems with this crap of technology.
    /// </summary>
    /// <param name="victim">The string to mutilate.</param>
    /// <returns>A shadow of the string it was before the carnage.</returns>
    private String cleanString(String victim) {
        return victim.ToLower().Replace('á', 'a').Replace('í', 'i').Replace('ú', 'u')
                               .Replace('é', 'e').Replace('ó', 'o');
    }

    /// <summary>
    /// Updates the blocked areas in the controller manager.
    /// </summary>
    private void updateBlockedAreas() {
        Debug.Log("Reconfiguring the blocked areas in the controller manager...");
        ControllerManager.BlockedAreas.Clear();
        switch (CurrentScreen) {
            case MenuScreenType.Menu:
                ControllerManager.BlockedAreas.AddLast(new Rect(0, 0, Screen.width, Screen.height));
                break;
            case MenuScreenType.Game:
                ControllerManager.BlockedAreas.AddLast(new Rect(0, 0, Screen.width, Screen.height * TOP_HEIGHT_FACTOR));
                ControllerManager.BlockedAreas.AddLast(new Rect(0, Screen.height - BUTTON_HEIGHT, Screen.width, BUTTON_HEIGHT));
                break;
        }
    }

    /// <summary>
    /// Adds a line to the received text.
    /// </summary>
    /// <param name="line">The line to add.</param>
    public void AddLineToReceivedText(string line) {
        if (string.IsNullOrEmpty(ReceivedText)) {
            ReceivedText = line;
        } else {
            ReceivedText += '\n' + line;
        }
        scrollPosition.y = float.MaxValue;
    }

    //****************************************************************************************************
    // Events:
    //****************************************************************************************************

    /// <summary>
    /// The on start event of the behaviour.
    /// </summary>
    public void Start() {
        pressStart2P = Resources.Load<Font>("PressStart2P-Regular");
        ReceivedText = "";
        MessageText = "";
    }

    /// <summary>
    /// The on update event of the behaviour.
    /// </summary>
    public void Update() {
        if (UseEscape && Input.GetKeyDown(KeyCode.Escape)) {
            switch (CurrentScreen) {
                case MenuScreenType.Menu:
                    if (Application.loadedLevelName != "Main") {
                        Game.main.ShowGUI = true;
                        CurrentScreen = MenuScreenType.Game;
                    }
                    break;
                case MenuScreenType.Game:
                    Game.main.ShowGUI = false;
                    CurrentScreen = MenuScreenType.Menu;
                    break;
            }
            updateBlockedAreas();
        }
    }

    /// <summary>
    /// The on GUI event of the behaviour.
    /// </summary>
    public void OnGUI() {
        switch (CurrentScreen) {
            case MenuScreenType.Menu:
                onMenuGUI();
                break;
            case MenuScreenType.Game:
                onGameGUI();
                break;
        }
    }
}
