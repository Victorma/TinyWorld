package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.MinionInfo;

public class StaticUtil {
    
    public static boolean EventMoveCheck(GameEvent report, MinionInfo mi){
        
        GameEvent event = (GameEvent)report.getParameter("event");
        boolean nameMove = event.getName().equals("move");
        boolean entityEqualsMiid = nameMove && event.getParameter("entity").equals(mi.getInstanceId());
        
        return nameMove && entityEqualsMiid;
    }

}
