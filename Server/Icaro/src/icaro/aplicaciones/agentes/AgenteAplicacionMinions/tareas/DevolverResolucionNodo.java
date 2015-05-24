package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.minions.PeticionResolucionNodo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class DevolverResolucionNodo extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {
        
        Objetivo obj = (Objetivo) params[0];
        PeticionResolucionNodo peticion = (PeticionResolucionNodo) params[1];
        
        this.generarInformeOK(this.identTarea, obj, peticion.getEmisor(), peticion);
        this.getEnvioHechos().eliminarHecho(peticion);
    }

}
