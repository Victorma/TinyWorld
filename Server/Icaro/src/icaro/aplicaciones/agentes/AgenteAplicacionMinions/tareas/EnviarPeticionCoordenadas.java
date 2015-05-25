package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.Subobjetivo;
import icaro.aplicaciones.informacion.minions.PeticionCoordenadas;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class EnviarPeticionCoordenadas extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {
        Subobjetivo obj = (Subobjetivo) params[0];
        
        ItfUsoRepositorioInterfaces repo = repoInterfaces;
        
        String destinatario = ((Subobjetivo)obj.getParent()).owner;
        
        PeticionCoordenadas peticion = new PeticionCoordenadas(this.identAgente);
        MensajeSimple ms = new MensajeSimple(peticion, agente.getIdentAgente(), destinatario);
        try {
            ((ItfUsoAgenteCognitivo ) repo.obtenerInterfazUso(destinatario)).aceptaMensaje(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
