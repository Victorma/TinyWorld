using UnityEngine;
using System.Collections.Generic;

public class IcaroEventManager : EventManager {

	public override void ReceiveEvent (GameEvent ev)
	{
		if (ev.Name == "SendMessageToIcaro") {
			if (IcaroSocket.Instance.isConnected ())
				IcaroSocket.Instance.sendMessage((string)ev.getParameter("Message"));
		}
	}

	public override void Tick ()
	{
		if(!IcaroSocket.Instance.isConnected())
			IcaroSocket.Instance.connect();

		if (IcaroSocket.Instance.isConnected ()) {
			List<string> messages = IcaroSocket.Instance.getMessages();
            if (messages.Count == 0)
                return;

            Secuence secuence = ScriptableObject.CreateInstance<Secuence>();
            secuence.init();
            Dialog dialog = ScriptableObject.CreateInstance<Dialog>();
            foreach (string m in messages)
            {
                dialog.addFragment();
                Dialog.Fragment[] fragments = dialog.getFragments();
                Dialog.Fragment fragment = fragments[fragments.Length-1];
                fragment.Name = "ChatterBotten";
                fragment.Msg = m;
                
            }
            secuence.Root.Content = dialog;
            GameEvent ge = new GameEvent();
            ge.Name = "start secuence";
            ge.setParameter("Secuence", secuence);
            Game.main.enqueueEvent(ge);
		}

	}

	void OnDestroy(){
		Debug.Log ("Destroyed");
		IcaroSocket.Instance.disconnect ();
	}

}
