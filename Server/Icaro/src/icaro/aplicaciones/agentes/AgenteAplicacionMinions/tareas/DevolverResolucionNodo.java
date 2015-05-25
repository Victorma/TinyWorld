package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import java.rmi.RemoteException;

import icaro.aplicaciones.informacion.minions.PeticionResolucionNodo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.informes.InformeDeTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

public class DevolverResolucionNodo extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        Objetivo obj = (Objetivo) params[0];
        PeticionResolucionNodo peticion = (PeticionResolucionNodo) params[1];

        if (peticion.getEmisor().equalsIgnoreCase(identAgente)) {
            this.generarInformeOK(this.identTarea, obj, peticion.getEmisor(), peticion);
        } else {
            InformeDeTarea informe = new InformeDeTarea(this.identTarea, peticion.getEmisor(), peticion);
            MensajeSimple ms = new MensajeSimple(informe, this.getIdentAgente(), peticion.getEmisor());

            try {
                ((ItfUsoAgenteCognitivo) repoInterfaces.obtenerInterfazUso(peticion.getEmisor())).aceptaMensaje(ms);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        this.getEnvioHechos().eliminarHecho(peticion);
    }

}
