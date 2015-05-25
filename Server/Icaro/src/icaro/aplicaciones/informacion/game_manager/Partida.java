package icaro.aplicaciones.informacion.game_manager;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.ObtenerObjeto;
import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.Subobjetivo;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.MinionContext;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Partida {

    public enum EstadoPartida {

        SIN_COMPLETAR, COMPLETADA
    }

    //Clase privada
    private class ObjPartida {

        public GameEvent evento;
        public boolean completado;

        ObjPartida(GameEvent evento) {
            this.evento = evento;
            this.completado = false;
        }

        ObjPartida(GameEvent evento, boolean estado) {
            this.evento = evento;
            this.completado = estado;
        }
    }

    //Parametros
    public List<ObjPartida> objetivos;
    public List<String> minions;
    public EstadoPartida estado = EstadoPartida.SIN_COMPLETAR;

    //Metodos
    public Partida(AgenteCognitivo agente, ItfUsoRepositorioInterfaces repoInterfaces, GameEvent event) {
        GameEvent[] objtmp = (GameEvent[]) event.getParameter("objetivos");
        if (objtmp == null) {
            objtmp = new GameEvent[0];
        }

        List<MinionInfo> mintmp = new ArrayList<MinionInfo>();
        for (Object o : (List<Object>) event.getParameter("minions")) {
            mintmp.add((MinionInfo) o);
        }

        objetivos = new ArrayList<ObjPartida>();

        for (GameEvent ge : objtmp) {
            objetivos.add(new ObjPartida(ge));
        }


        this.estado = objetivosCompletados() ? EstadoPartida.COMPLETADA : EstadoPartida.SIN_COMPLETAR;

        try {
            DescComportamientoAgente dca = ClaseGeneradoraConfiguracion.instance().getDescComportamientoAgente("AgenteAplicacionMinion");
            minions = new ArrayList<String>();
            MinionContext mc = new MinionContext(agente, agente.getIdentAgente());
            
            
            Subobjetivo obtenerObjeto = new ObtenerObjeto("Roca Afilada");
            boolean primero = true;
            
            for (MinionInfo mi : mintmp) {
                DescInstanciaAgente descInstanciaAgente = new DescInstanciaAgente();

                String minionName = "AgentMinion(" + mi.getName() + "_" + mi.getInstanceId() + "@" + agente.getIdentAgente() + ")";
                descInstanciaAgente.setId(minionName);
                descInstanciaAgente.setDescComportamiento(dca);
                FactoriaAgenteCognitivo.instance().crearAgenteCognitivo(descInstanciaAgente);
                minions.add(descInstanciaAgente.getId());

                ItfUsoAgenteCognitivo itfMinion = (ItfUsoAgenteCognitivo) repoInterfaces.obtenerInterfazUso(minionName);
                AgenteCognitivo gestionMinion = (AgenteCognitivo) repoInterfaces.obtenerInterfazGestion(minionName);
                gestionMinion.arranca();
                
                itfMinion.aceptaMensaje(new MensajeSimple(mi, agente.getIdentAgente(), minionName));
                itfMinion.aceptaMensaje(new MensajeSimple(mc, agente.getIdentAgente(), minionName));
                
                if(primero){
                    itfMinion.aceptaMensaje(new MensajeSimple(obtenerObjeto, agente.getIdentAgente(), minionName));
                    primero = false;
                }
                
            }
        } catch (Exception ex) {
        	// 
        }
    }

    public void addObjetivo(GameEvent objetivo) {
        objetivos.add(new ObjPartida(objetivo));
    }

    public void addMinion(String minion) {
        minions.add(minion);
    }

    public void validarObjetivo(GameEvent objetivo) {
        for (ObjPartida o : objetivos) {
            if (o.evento.name.equalsIgnoreCase(objetivo.name)) {

                Collection<String> parameters = o.evento.getParameters();

                boolean c = true;
                for (String p : parameters) {
                    if (objetivo.getParameter(p) != null) {
                        if (!((String) objetivo.getParameter(p)).equalsIgnoreCase((String) o.evento.getParameter(p))) {
                            c = false;
                            break;
                        }
                    } else {
                        c = false;
                        break;
                    }
                }

                o.completado = c;
                break;
            }
        }

        this.estado = objetivosCompletados() ? EstadoPartida.COMPLETADA : EstadoPartida.SIN_COMPLETAR;
    }

    public boolean objetivosCompletados() {
    	// TODO change to true when objectives are planned
        boolean completados = false;
        for (ObjPartida o : objetivos) {
            if (o.completado == false) {
                completados = false;
                break;
            }
        }
        return completados;
    }

    public void terminaPartida() {

    }
}
