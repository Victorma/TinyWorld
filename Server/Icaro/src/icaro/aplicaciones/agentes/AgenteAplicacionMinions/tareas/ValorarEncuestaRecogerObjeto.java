package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.RecogerObjeto;
import icaro.aplicaciones.informacion.minions.EncuestaNodo;
import icaro.aplicaciones.informacion.minions.ItemData;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ValorarEncuestaRecogerObjeto extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        MinionInfo mi = (MinionInfo) params[0];
        EncuestaNodo encuesta = (EncuestaNodo) params[1];
        
        RecogerObjeto recogerObjeto = (RecogerObjeto) encuesta.nodo.getSubobjetivo();
        
        ItemData item = null;
        Float distance = Float.MAX_VALUE;
        for(ItemData ci : mi.getAvailableItems()){
            Float currentDist = mi.getCoords().distanceTo(ci.getCoords()) * 1.0f;
            if(ci.getName().equals(recogerObjeto.itemName) && currentDist < distance){
                distance = currentDist;
                item = ci;
            }
        }

        
        if(item == null){
            encuesta.estimacion = -1;
        }else{
            encuesta.estimacion = distance;
        }
        
        encuesta.setResuelta();
        this.getEnvioHechos().actualizarHecho(encuesta);

    }

}
