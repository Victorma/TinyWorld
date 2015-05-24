package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import java.util.ArrayList;
import java.util.List;

import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.ItemData;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 * @param 0: myInfo MinionInfo
 * @param 2: ge GameEvent
 * 
 * @author Ivan
 *
 */
public class AlmacenarObservado extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        // Common parameters
        MinionInfo myInfo = (MinionInfo) params[0];

        try {
            GameEvent ge = (GameEvent) params[1];
            
            List<Object> tmpavi = (List<Object>) ge.getParameter("items_floor");
            List<Object> tmpuni = (List<Object>) ge.getParameter("items_hand");
            
            List<ItemData> avi = new ArrayList<ItemData>();
            List<ItemData> uni = new ArrayList<ItemData>();
            
            for(Object o : tmpavi)
                avi.add((ItemData) o);
            
            for(Object o : tmpuni)
                uni.add((ItemData) o);
            
            myInfo.setAvailableItems(avi);
            myInfo.setUnavailableItems(uni);
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
    }

}
