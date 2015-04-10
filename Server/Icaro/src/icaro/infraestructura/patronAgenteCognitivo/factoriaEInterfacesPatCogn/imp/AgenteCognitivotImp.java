package icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp;

//import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
//import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
//import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
//import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
//import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
//import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ProcesadorObjetivos;
//import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.FactoriaProcesadorObjetivos;
//import icaro.infraestructura.patronAgenteCognitivo.management.states.ItfEstado;
//import icaro.infraestructura.patronAgenteCognitivo.management.states.imp.EstadoCreado;
//import icaro.infraestructura.patronAgenteCognitivo.percepcion.FactoriaPercepcionAgenteCognitivo;
//import icaro.infraestructura.patronAgenteCognitivo.percepcion.PercepcionAgenteCognitivo;
//import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
//import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
//import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
//
//import org.apache.log4j.Logger;
//
///**
// * Implementation for Cognitive Agent
// * @author carf
// * @author Carlos Celorrio
// */
//public class AgenteCognitivotImp extends AgenteCognitivo {
//
//    private ItfEstado state = null;
//    private String estadoAgente;
//    private String name;
//
//    private PercepcionAgenteCognitivo perception;
//    private ProcesadorObjetivos control;
//
//    private ItfUsoRecursoTrazas traces= NombresPredefinidos.RECURSO_TRAZAS_OBJ;
//    private Logger log = Logger.getLogger(AgenteCognitivotImp.class);
//
//    /**
//     * Constructor for Cognitive Agent
//     * @param name Agent name
//     * @throws Exception
//     */
//    public AgenteCognitivotImp(String name, String goalResolutionFile) throws Exception {
//
//    	this.name =name;
//
//		control = FactoriaProcesadorObjetivos
//				.instance().crearProcesadorObjetivos(this, goalResolutionFile);
//		perception = FactoriaPercepcionAgenteCognitivo
//				.instance().crearPercepcion(this);
//    	state = EstadoCreado.instancia();
//
//
//
//		traces.aceptaNuevaTraza(new InfoTraza(this.getLocalName(),
//				"State: "+ state.toString(),
//				InfoTraza.NivelTraza.debug));
//    }
//
//    /**
//     * Processes a message depending on the current state
//     */
//    public void receiveMessage(MensajeSimple message) {
//        state.aceptaMensaje(this, message);
//    }
//    public void aceptaMensaje(MensajeSimple mensaje) {
//        state.aceptaMensaje(this, mensaje);
//
//        traces.aceptaNuevaTraza(new InfoTraza(this.getLocalName(),
//				"State: "+ state.toString() + "recibo el mensaje con contenido:"+mensaje.getContenido().toString(),
//				InfoTraza.NivelTraza.debug));
//    }
//    /**
//     * Processes an event depending on the current state
//     */
//	public void receiveEvent(EventoRecAgte event) {
//		state.aceptaEvento(this,event);
//	}
//    public void aceptaEvento(EventoRecAgte event) {
//		state.aceptaEvento(this,event);
//	}
//
//
//    public void arranca() {
//        state.arranca(this);
//    }
//
//    public void para() {
//        state.para(this);
//    }
//
//    public void termina() {
//        state.termina(this);
//    }
//
//    public void continua() {
//        state.continua(this);
//    }
//
//    public int obtenerEstado() {
//    	traces.aceptaNuevaTraza(new InfoTraza(this.getLocalName(),
//    			"State: "+ state.toString(),
//    		InfoTraza.NivelTraza.debug));
//        return state.obtenerEstado(this);
//
//    }
//
//    public ItfEstado getEstado() {
//        return state;
//    }
//
//    public void setEstado(ItfEstado state) {
//        this.state = state;
//        traces.aceptaNuevaTraza(new InfoTraza(this.getLocalName(),
//        		"State Change: "+ state.toString(),
//        		InfoTraza.NivelTraza.debug));
//    }
//     public void setEstado(String state) {
//        this.estadoAgente = state;
//        traces.aceptaNuevaTraza(new InfoTraza(this.getLocalName(),
//        		"State Change: "+ estadoAgente,
//        		InfoTraza.NivelTraza.debug));
//    }
//	public PercepcionAgenteCognitivo getPercepcion() {
//		return perception;
//	}
//        public  void setComponentesInternos(ItfUsoAutomataEFsinAcciones itfAutomataCiclVidaAgente,PercepcionAgenteCognitivo percepcion,ProcesadorObjetivos procObjetivos){
//
//        }
//
//	public void setPerception(PercepcionAgenteCognitivo perception) {
//		this.perception = perception;
//	}
//
//	public ProcesadorObjetivos getControl() {
//		return control;
//	}
//
//	public void setControl(ProcesadorObjetivos control) {
//		this.control = control;
//	}
//
//	@Override
//	public String getLocalName() {
//		return this.name;
//	}
//}
