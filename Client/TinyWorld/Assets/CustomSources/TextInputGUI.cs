using UnityEngine;
using System.Collections.Generic;

public class TextInputGUI : IsoGUI
{
	private Rect drawRect;
	private string message = "";
	private bool pushed = false;

	public override bool captureEvent (ControllerEventArgs args)
	{
		bool capture = false;
		if (this.drawRect != null) {
			capture = this.drawRect.Contains(args.mousePos);
		}
		return capture;
	}

	public override void fillControllerEvent (ControllerEventArgs args)
	{
		args.send = false;

	}

	private Rect sumRect(Rect a,Rect b){
		return new Rect (a.x + b.x, a.y + b.y, a.width + b.width, a.height + b.height);
	}

	public override void draw ()
	{
		drawRect = new Rect (10.0f, 2.0f * Screen.height / 3.0f, Screen.width-10.0f, Screen.height / 3.0f);

        message = GUI.TextField(sumRect(drawRect, new Rect(0.0f, 0.0f, -drawRect.width/4.0f, 0.0f)), message);
        pushed = GUI.Button(sumRect(drawRect, new Rect(drawRect.width - drawRect.width / 4.0f, 0.0f, drawRect.width / 4.0f, 0.0f)), "Enviar");

		if (pushed && message != "") {
            GameEvent ge = new GameEvent();
			ge.Name = "SendMessageToIcaro";
			ge.setParameter("Message", message);
			ge.setParameter("Syncronized", true);
			message = "";
			Game.main.enqueueEvent(ge);
		}
	}
}

