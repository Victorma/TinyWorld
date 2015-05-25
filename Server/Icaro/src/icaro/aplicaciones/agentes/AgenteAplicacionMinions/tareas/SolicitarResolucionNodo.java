package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.minions.EncuestaNodo;
import icaro.aplicaciones.informacion.minions.PeticionResolucionNodo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class SolicitarResolucionNodo extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {
        EncuestaNodo encuesta = (EncuestaNodo) params[0];

        ItfUsoRepositorioInterfaces repo = repoInterfaces;

        PeticionResolucionNodo peticion = new PeticionResolucionNodo(encuesta.nodo, this.identAgente);
        MensajeSimple ms = new MensajeSimple(peticion, agente.getIdentAgente(), encuesta.encuestado);
        try {
            ((ItfUsoAgenteCognitivo) repo.obtenerInterfazUso(encuesta.encuestado)).aceptaMensaje(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
