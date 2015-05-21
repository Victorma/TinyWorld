using UnityEngine;
using System.Collections.Generic;

public class IcaroEventManager : EventManager {

    void OnEnable() {

        IcaroSocket.Instance.connect();

        GameEvent login = ScriptableObject.CreateInstance<GameEvent>();
        login.Name = "login";

        Game.main.enqueueEvent(login);
    }


    public override void ReceiveEvent(GameEvent ev) {
        if (ev.name == "event finished" && secuencesStarted.ContainsKey(((GameEvent)ev.getParameter("event")).GetInstanceID())) {
            GameEvent ge = ev.getParameter("event") as GameEvent;
            secuencesStarted.Remove(ge.GetInstanceID());
            Secuence se = ge.getParameter("secuence") as Secuence;
            Secuence.DestroyImmediate(se);
        } else if (eventsSendedToGame.ContainsKey(ev.GetInstanceID())) {
            eventsSendedToGame.Remove(ev.GetInstanceID());
        } else if (IcaroSocket.Instance.isConnected()) {

            if (ev.name == "IniciarPartida") {
                MinionScript[] minions = GameObject.FindObjectsOfType<MinionScript>();
                List<object> minionList = new List<object>();
                foreach (var ms in minions)
                    minionList.Add(ms);

                ev.setParameter("minions", minionList);
            }
            ev.setParameter("fromClient", true);

            IcaroSocket.Instance.sendMessage(ev.toJSONObject().ToString());
        }

    }

    private Dictionary<int, GameEvent> secuencesStarted = new Dictionary<int, GameEvent>();
    private Dictionary<int, GameEvent> eventsSendedToGame = new Dictionary<int, GameEvent>();

    public override void Tick() {

        if (IcaroSocket.Instance.isConnected()) {
            List<string> messages = IcaroSocket.Instance.getMessages();
            if (messages.Count == 0)
                return;

            Secuence secuence = null;
            Dialog dialog = null;

            foreach (string s in messages) {
                GameEvent ge = GameEvent.CreateInstance<GameEvent>();
                ge.fromJSONObject(JSONObject.Create(s));

                // TODO Maybe this showmessage thing will be in another event manager
                if (ge.name == "show message") {
                    if (secuence == null && dialog == null) {
                        secuence = ScriptableObject.CreateInstance<Secuence>();
                        secuence.init();
                        dialog = ScriptableObject.CreateInstance<Dialog>();
                        secuence.Root.Content = dialog;
                    }

                    dialog.addFragment();
                    Dialog.Fragment[] fragments = dialog.getFragments();
                    Dialog.Fragment fragment = fragments[fragments.Length - 1];
                    fragment.Name = "ChatterBotten";
                    fragment.Msg = (string)ge.getParameter("message");
                } else if (ge.name == "receive.text") {
                    var msg = (string)ge.getParameter("message");
                    var menu = GameObject.FindObjectOfType<MenuBehaviour>();
                    if (menu) {
                        menu.AddLineToReceivedText(msg);
                    }
                } else {
                    Game.main.enqueueEvent(ge);
                    eventsSendedToGame.Add(ge.GetInstanceID(), ge);
                }
            }

            if (secuence != null) {
                GameEvent secuenceGE = new GameEvent();
                secuenceGE.Name = "start secuence";
                secuenceGE.setParameter("Secuence", secuence);
                secuenceGE.setParameter("syncronized", true);
                Game.main.enqueueEvent(secuenceGE);
                secuencesStarted.Add(secuenceGE.GetInstanceID(), secuenceGE);
            }

        }

    }

    void OnDestroy() {

        GameEvent logout = ScriptableObject.CreateInstance<GameEvent>();
        logout.Name = "logout";

        this.ReceiveEvent(logout);

        Debug.Log("Destroyed");
        IcaroSocket.Instance.disconnect();
    }

}
