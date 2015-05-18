using UnityEngine;
using System.Collections.Generic;

public class GameInfo : MonoBehaviour {

    public GameObject lookTo;
    public List<Map> maps;

	// Use this for initialization
	void Start () {
        CameraManager.lookTo(lookTo);
        foreach (Map m in maps)
            MapManager.getInstance().setActiveMap(m);


        GameEvent start = GameEvent.CreateInstance<GameEvent>();
        start.Name = "IniciarPartida";
        Game.main.enqueueEvent(start);
	}
	
}
