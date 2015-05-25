package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.minions.EncuestaNodo;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.ListaIntegrantes;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class DevolverEncuesta extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {


        EncuestaNodo encuesta = (EncuestaNodo) params[0];
        
        ItfUsoRepositorioInterfaces repo = repoInterfaces;
        
        if(!encuesta.emisor.equalsIgnoreCase(agente.getIdentAgente())){
            MensajeSimple ms = new MensajeSimple(encuesta, agente.getIdentAgente(), encuesta.emisor);
            try {
                getEnvioHechos().eliminarHechoWithoutFireRules(encuesta);
                ((ItfUsoAgenteCognitivo ) repo.obtenerInterfazUso(encuesta.emisor)).aceptaMensaje(ms);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
    }

}
