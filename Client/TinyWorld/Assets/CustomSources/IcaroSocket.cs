using UnityEngine;
using System.Collections.Generic;

public abstract class IcaroSocket {

	private static IcaroSocket imp;
	public static IcaroSocket Instance{
		get {
			if(imp == null) 
				imp = new IcaroSocketImp();
			return imp;
		}
	}

	public abstract bool isConnected();
	public abstract void connect();
	public abstract void sendMessage(string message);
	public abstract List<string> getMessages();
	public abstract void disconnect();
}
