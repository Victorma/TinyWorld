package icaro.aplicaciones.informacion.game_manager;

import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.MinionContext;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.util.ArrayList;
import java.util.List;

public class Partida {
    //****************************************************************************************************
    // Types:
    //****************************************************************************************************

    public enum EstadoPartida {
        SIN_COMPLETAR, COMPLETADA
    }

    public class ObjPartida {

        public GameEvent evento;
        public boolean completado;

        ObjPartida(GameEvent evento) {
            this(evento, false);
        }

        ObjPartida(GameEvent evento, boolean estado) {
            this.evento = evento;
            this.completado = estado;
        }
    }

    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************

    public List<ObjPartida> objetivos;
    public List<String> minions;
    public EstadoPartida estado = EstadoPartida.SIN_COMPLETAR;

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************

    public Partida(AgenteCognitivo agente, ItfUsoRepositorioInterfaces repoInterfaces, GameEvent event) {
        GameEvent[] objtmp = (GameEvent[]) event.getParameter("objetivos");
        if (objtmp == null) {
            objtmp = new GameEvent[0];
        }

        List<MinionInfo> mintmp = new ArrayList<>();
        for (Object o : (List<Object>) event.getParameter("minions")) {
            mintmp.add((MinionInfo) o);
        }

        objetivos = new ArrayList<>();
        for (GameEvent ge : objtmp) {
            objetivos.add(new ObjPartida(ge));
        }

        this.estado = objetivosCompletados() ? EstadoPartida.COMPLETADA : EstadoPartida.SIN_COMPLETAR;

        try {
            DescComportamientoAgente dca = ClaseGeneradoraConfiguracion.instance().getDescComportamientoAgente("AgenteAplicacionMinion");
            minions = new ArrayList<>();
            MinionContext mc = new MinionContext(agente, agente.getIdentAgente());
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
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    public void addObjetivo(GameEvent objetivo) {
        objetivos.add(new ObjPartida(objetivo));
    }

    public void addMinion(String minion) {
        minions.add(minion);
    }

    public void validarObjetivo(GameEvent objetivo) {
        for (ObjPartida item : objetivos) {
            if (item.evento.isNameEquals(objetivo.getName())) {
                boolean completed = true;
                for (String key : item.evento.getParameters()) {
                    String objParam = (String) objetivo.getParameter(key);
                    String eventParam = (String) item.evento.getParameter(key);
                    if (objParam == null || !objParam.equalsIgnoreCase(eventParam)) {
                        completed = false;
                        break;
                    }
                }
                item.completado = completed;
            }
        }
        this.estado = objetivosCompletados() ? EstadoPartida.COMPLETADA : EstadoPartida.SIN_COMPLETAR;
    }

    public boolean objetivosCompletados() {
        for (ObjPartida o : objetivos) {
            if (o.completado == false) {
                return false;
            }
        }
        // TODO: Objectives must be complete in the prototype before this function
        // can return the true value and fix this code...
        return false;
        //return true;
    }

    public void terminaPartida() {
    }
}
