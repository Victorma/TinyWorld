
package icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp;

import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.PercepcionAgenteCognitivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;

import org.apache.log4j.Logger;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;

/**
 * Implementation for Cognitive Agent
 * @author carf
 * @author Carlos Celorrio
 */
public class AgenteCognitivotImp2 extends AgenteCognitivo implements Serializable{

//    private ItfEstado state = null;

    private String identAgente;
    private String estadoAgente;

    private PercepcionAgenteCognitivo percepcion;
    private ProcesadorObjetivos procObjetivos;
    private ItfUsoAutomataEFsinAcciones itfAutomataEstadoAgente ;
    private ItfUsoRecursoTrazas trazas= NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    private Logger log = Logger.getLogger(AgenteCognitivotImp2.class);

    /**
     * Constructor for Cognitive Agent
     * @param name Agent name
     * @throws Exception
     */
    public AgenteCognitivotImp2(String nombreAgenteNormalizado) throws Exception {

                this.identAgente =nombreAgenteNormalizado;
		procObjetivos = null;
		percepcion = null;
                estadoAgente = NombresPredefinidos.ESTADO_EN_CONSTRUCCION;
		trazas.aceptaNuevaTraza(new InfoTraza(identAgente,
				"Estado: "+ estadoAgente,
				InfoTraza.NivelTraza.debug));
    }
    @Override
    public void setComponentesInternos(ItfUsoAutomataEFsinAcciones itfAutomataCiclVidaAgente,PercepcionAgenteCognitivo percepcion,ProcesadorObjetivos procObjetivos){
        this.itfAutomataEstadoAgente = itfAutomataCiclVidaAgente;
        this.percepcion = percepcion;
        this.procObjetivos = procObjetivos;
    }
    @Override
    public void setEstado(String estado){
        this.estadoAgente= estado;
    }
    /**
     * Processes a message depending on the current state
     */
    public void receiveMessage(MensajeSimple message) {
        if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))){
            try {
                percepcion.aceptaMensaje(message);
            } catch (RemoteException ex) {
                java.util.logging.Logger.getLogger(AgenteCognitivotImp2.class.getName()).log(Level.SEVERE, null, ex);
            }
                }else
                    {
                  trazas.aceptaNuevaTraza(new InfoTraza(identAgente,
                ": El agente se encuentra en el estado: " + estadoAgente + " y no puede procesar el mensaje recibido",
                InfoTraza.NivelTraza.debug));
                }
    }
    @Override
    public void aceptaMensaje(MensajeSimple mensaje) {
        if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))){
            try {
                percepcion.aceptaMensaje(mensaje);
                trazas.aceptaNuevaTrazaMensajeRecibido(mensaje);
            } catch (RemoteException ex) {
                java.util.logging.Logger.getLogger(AgenteCognitivotImp2.class.getName()).log(Level.SEVERE, null, ex);
            }
                }else
                    {
                  trazas.aceptaNuevaTraza(new InfoTraza(identAgente,
                ": El agente se encuentra en el estado: " + estadoAgente + " y no puede procesar el mensaje recibido",
                InfoTraza.NivelTraza.debug));
                }

        trazas.aceptaNuevaTraza(new InfoTraza(identAgente,
				"Estado: "+ estadoAgente + " recibo el mensaje con contenido:"+mensaje.getContenido().toString(),
				InfoTraza.NivelTraza.debug));
    }
    /**
     * Processes an event depending on the current state
     */
	public void receiveEvent(EventoRecAgte event) {
		if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))){
                   try {
			percepcion.aceptaEvento(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                }else
                    {
                  trazas.aceptaNuevaTraza(new InfoTraza(identAgente,
                ": El agente se encuentra en el estado: " + estadoAgente + " y no puede procesar el mensaje recibido",
                InfoTraza.NivelTraza.debug));
                }
	}
    @Override
    public void aceptaEvento(EventoRecAgte evento) {
		if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))){
                   try {
			percepcion.aceptaEvento(evento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                }else
                    {
                  trazas.aceptaNuevaTraza(new InfoTraza(identAgente,
                ": El agente se encuentra en el estado: " + estadoAgente + " y no puede procesar el mensaje recibido",
                InfoTraza.NivelTraza.debug));
                }
	}

    //JM: El siguiente metodo lo he añadido para poder enviar eventos simples
    @Override
    public void aceptaEvento(EventoSimple evento) {
		if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))){
                   try {
			percepcion.aceptaEvento(evento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                }else
                    {
                  trazas.aceptaNuevaTraza(new InfoTraza(identAgente,
                ": El agente se encuentra en el estado: " + estadoAgente + " y no puede procesar el mensaje recibido",
                InfoTraza.NivelTraza.debug));
                }
	}
    
    @Override
    public void arranca() {

        trazas.aceptaNuevaTraza(new InfoTraza(identAgente,
                ": arranca()",
                InfoTraza.NivelTraza.debug));
     //   itfAutomataEstadoAgente.transita("arrancar");
   //     itfAutomataCicloVida.transita("ok");


  //     estadoAgente =  itfAutomataEstadoAgente.estadoActual();
       if (  estadoAgente.equals( NombresPredefinidos.ESTADO_CREADO)){
           itfAutomataEstadoAgente.transita(NombresPredefinidos.INPUT_OK);
           estadoAgente= itfAutomataEstadoAgente.estadoActual();
           percepcion.arranca();
           procObjetivos.arranca();

       }else {
           itfAutomataEstadoAgente.transita(NombresPredefinidos.INPUT_ERROR);
           estadoAgente= itfAutomataEstadoAgente.estadoActual();
       }
    }

    public void para() {
        this.setEstado(NombresPredefinidos.ESTADO_PARADO);
    }

    public void termina() {
        this.percepcion.termina();
    }

    public void continua() {
       this.setEstado(NombresPredefinidos.ESTADO_ACTIVO);
    }

    public int obtenerEstado() {
  //  	estadoAgente = itfAutomataEstadoAgente.estadoActual();
//
//        trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
//    			"Estado: "+ estadoAgente,
//    		InfoTraza.NivelTraza.debug));
//
//	return itfAutomataEstadoAgente.estadoActualInt();
        estadoAgente = this.itfAutomataEstadoAgente.estadoActual();
                log.debug(identAgente + ": monitoriza()Mi estado es "+estadoAgente );
                trazas.aceptaNuevaTraza(new InfoTraza(identAgente, ": monitoriza()monitoriza()Mi estado es "+estadoAgente,InfoTraza.NivelTraza.debug));

		if (estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO)
				|| estadoAgente.equals(NombresPredefinidos.ESTADO_ARRANCADO))
			return InterfazGestion.ESTADO_ACTIVO;
		if (estadoAgente.equals(NombresPredefinidos.ESTADO_TERMINADO))
			return InterfazGestion.ESTADO_TERMINADO;
		if (estadoAgente.equals(NombresPredefinidos.ESTADO_TERMINANDO))
			return InterfazGestion.ESTADO_TERMINANDO;

		if (estadoAgente.equals(NombresPredefinidos.ESTADO_CREADO))
			return InterfazGestion.ESTADO_CREADO;
		if (estadoAgente.equals(NombresPredefinidos.ESTADO_ERROR))
			return InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE;
		if (estadoAgente.equals(NombresPredefinidos.ESTADO_FALLO_TEMPORAL))
			return InterfazGestion.ESTADO_ERRONEO_RECUPERABLE;
		if (estadoAgente.equals(NombresPredefinidos.ESTADO_PARADO))
			return InterfazGestion.ESTADO_PARADO;
		return InterfazGestion.ESTADO_OTRO;
    }


    public PercepcionAgenteCognitivo getPercepcion() {
		return this.percepcion;
	}

	public void setPercepcion(PercepcionAgenteCognitivo perception) {
		this.percepcion = perception;
	}

	public ProcesadorObjetivos getControl() {
		return this.procObjetivos;
	}

	public void setControl(ProcesadorObjetivos control) {
		this.procObjetivos = control;
	}

	@Override
	public String getIdentAgente() {
		return this.identAgente;
	}
}
