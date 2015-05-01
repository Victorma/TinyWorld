package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.ConfiguracionTrazas;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestionPercepcion;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.EjecutorDeAccionesImp;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class AgenteReactivoImp2 extends AgenteReactivoAbstracto implements Serializable {

    private static final long serialVersionUID = 1L;
    protected ItfControlAgteReactivo itfControlAgteReactivo;
    protected ItfUsoAutomataEFsinAcciones itfAutomataCicloVida;
    protected InterfazGestion itfGesControl; //Control
    protected InterfazGestionPercepcion itfGestionPercepcion;
    protected ItfProductorPercepcion itfProductorPercepcion;
    protected String nombre;
    protected String estadoAgente;
    protected int estado = InterfazGestion.ESTADO_OTRO;
    protected EjecutorDeAccionesImp accionesSemanticas;
    private boolean DEBUG = false;
    protected ItfUsoAgenteReactivo itfUsoGestorAReportar;
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    protected ItfUsoRecursoTrazas trazas;

    public AgenteReactivoImp2(String nombreAgente, ItfControlAgteReactivo itfControlAgte, ItfProductorPercepcion itfProdPercepcion, InterfazGestionPercepcion itfConsumPercepcion) throws RemoteException {
        super();
        ConfiguracionTrazas.configura(logger);
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        nombre = nombreAgente;
        itfControlAgteReactivo = itfControlAgte;
        itfProductorPercepcion = itfProdPercepcion;
        itfGestionPercepcion = itfConsumPercepcion;
    }

    public AgenteReactivoImp2(String nombreAgente, ItfUsoAutomataEFsinAcciones itfAutomata, ItfControlAgteReactivo itfControlAgte, ItfProductorPercepcion itfProdPercepcion, InterfazGestionPercepcion itfConsumPercepcion) throws RemoteException {
        super();
        ConfiguracionTrazas.configura(logger);
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        nombre = nombreAgente;
        itfAutomataCicloVida = itfAutomata;
        itfControlAgteReactivo = itfControlAgte;
        itfProductorPercepcion = itfProdPercepcion;
        itfGestionPercepcion = itfConsumPercepcion;
    }

    public AgenteReactivoImp2(String nombreAgente) throws RemoteException {
        super();
        ConfiguracionTrazas.configura(logger);
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        this.nombre = nombreAgente;
    }

    @Override
    public void setParametrosLoggerAgReactivo(String archivoLog, String nivelLog) {
        ConfiguracionTrazas.configura(logger, archivoLog, nivelLog);
    }

    @Override
    public void setComponentesInternos(String nombreAgente, ItfUsoAutomataEFsinAcciones itfAutomata, ItfControlAgteReactivo itfControlAgte, ItfProductorPercepcion itfProdPercepcion, InterfazGestionPercepcion itfgestionPercepcion) {
        nombre = nombreAgente;
        itfAutomataCicloVida = itfAutomata;
        itfControlAgteReactivo = itfControlAgte;
        itfProductorPercepcion = itfProdPercepcion;
        itfGestionPercepcion = itfgestionPercepcion;
    }

    @Override
    public synchronized void arranca() {
        try {
            logger.debug(nombre + ": arranca()");
            trazas.aceptaNuevaTraza(new InfoTraza(nombre, NombresPredefinidos.TipoEntidad.Reactivo, ": arranca()", InfoTraza.NivelTraza.debug));
            itfAutomataCicloVida.transita("arrancar");
            itfGestionPercepcion.arranca();
            int estadoControl = itfControlAgteReactivo.obtenerEstado();
            if (estadoControl != itfControlAgteReactivo.ESTADO_ERRONEO_IRRECUPERABLE) {
                itfAutomataCicloVida.transita(NombresPredefinidos.INPUT_OK);
                estadoAgente = itfAutomataCicloVida.estadoActual();
                itfControlAgteReactivo.arranca();
                itfControlAgteReactivo.procesarInput(NombresPredefinidos.INPUT_COMENZAR, (Object) null);
            } else {
                itfAutomataCicloVida.transita(NombresPredefinidos.INPUT_ERROR);
                estadoAgente = itfAutomataCicloVida.estadoActual();
            }
        } catch (RemoteException ex) {
            java.util.logging.Logger.getLogger(AgenteReactivoImp2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void continua() {
    }

    @Override
    public int obtenerEstado() {
        estadoAgente = this.itfAutomataCicloVida.estadoActual();
        logger.debug(nombre + ": monitoriza()Mi estado es " + estadoAgente);
        trazas.aceptaNuevaTraza(new InfoTraza(nombre, ": monitoriza()monitoriza()Mi estado es " + estadoAgente, InfoTraza.NivelTraza.debug));
        if (estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO)
                || estadoAgente.equals(NombresPredefinidos.ESTADO_ARRANCADO)) {
            return InterfazGestion.ESTADO_ACTIVO;
        }
        if (estadoAgente.equals(NombresPredefinidos.ESTADO_TERMINADO)) {
            return InterfazGestion.ESTADO_TERMINADO;
        }
        if (estadoAgente.equals(NombresPredefinidos.ESTADO_TERMINANDO)) {
            return InterfazGestion.ESTADO_TERMINANDO;
        }
        if (estadoAgente.equals(NombresPredefinidos.ESTADO_CREADO)) {
            return InterfazGestion.ESTADO_CREADO;
        }
        if (estadoAgente.equals(NombresPredefinidos.ESTADO_ERROR)) {
            return InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE;
        }
        if (estadoAgente.equals(NombresPredefinidos.ESTADO_FALLO_TEMPORAL)) {
            return InterfazGestion.ESTADO_ERRONEO_RECUPERABLE;
        }
        if (estadoAgente.equals(NombresPredefinidos.ESTADO_PARADO)) {
            return InterfazGestion.ESTADO_PARADO;
        }
        return InterfazGestion.ESTADO_OTRO;
    }

    @Override
    public synchronized void para() {
    }

    @Override
    public synchronized void termina() {
        try {
            logger.debug(nombre + ": termina()");
            trazas.aceptaNuevaTraza(new InfoTraza(nombre, ": termina()", InfoTraza.NivelTraza.debug));
            itfAutomataCicloVida.transita("terminar");
            itfAutomataCicloVida.transita("ok");
            itfControlAgteReactivo.termina();
            itfGestionPercepcion.termina();
        } catch (RemoteException ex) {
            java.util.logging.Logger.getLogger(AgenteReactivoImp2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void aceptaEvento(EventoRecAgte evento) {
        logger.debug(nombre + ": Ha llegado un evento por el interfaz de Uso:" + evento.toString());
        trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": Ha llegado un nuevo evento desde " + evento.getOrigen(),
                InfoTraza.NivelTraza.debug));
        trazas.aceptaNuevaTrazaEventoRecibido(nombre, evento);
        if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))) {
            itfProductorPercepcion.produce(evento);
        } else {
            trazas.aceptaNuevaTraza(new InfoTraza(nombre, ": El agente se encuentra en el estado: " +
                    estadoAgente + " y no puede procesar el evento recibido", InfoTraza.NivelTraza.debug));
        }
    }

    @Override
    public synchronized void aceptaEvento(EventoSimple evento) {
        logger.debug(nombre + ": Ha llegado un evento por el interfaz de Uso:" + evento.toString());
        trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": Ha llegado un nuevo evento desde " + evento.getOrigen(),
                InfoTraza.NivelTraza.debug));
        trazas.aceptaNuevaTrazaEventoRecibido(this.nombre, evento);
        if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))) {
            itfProductorPercepcion.produce(evento);
        } else {
            trazas.aceptaNuevaTraza(new InfoTraza(nombre, ": El agente se encuentra en el estado: " +
                    estadoAgente + " y no puede procesar el evento recibido", InfoTraza.NivelTraza.debug));
        }
    }

    @Override
    public void aceptaMensaje(MensajeSimple mensaje) {
        logger.debug(nombre + ": Ha llegado un mensaje por el interfaz de Uso:" + mensaje.toString());
        trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": Ha llegado un mensaje por el interfaz de Uso:" + mensaje.toString(),
                InfoTraza.NivelTraza.debug));
        trazas.aceptaNuevaTrazaMensajeRecibido(mensaje);
        if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))) {
            itfProductorPercepcion.produce(mensaje);
        } else {
            trazas.aceptaNuevaTraza(new InfoTraza(nombre, ": El agente se encuentra en el estado: " +
                    estadoAgente + " y no puede procesar el mensaje recibido", InfoTraza.NivelTraza.debug));
        }
    }

    public void procesaEventoInterno(EventoSimple evtoInterno) {
        logger.debug(nombre + ": Se procesa un evento interno:" + evtoInterno.toString());
        trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": Se procesa un evento interno:" + evtoInterno.toString(), InfoTraza.NivelTraza.debug));
        trazas.aceptaNuevaTrazaEventoRecibido(nombre, evtoInterno);
        if ((estadoAgente.equals(NombresPredefinidos.ESTADO_ACTIVO))) {
            itfProductorPercepcion.produceParaConsumirInmediatamente(evtoInterno);
        } else {
            trazas.aceptaNuevaTraza(new InfoTraza(nombre, ": El agente se encuentra en el estado: " +
                    estadoAgente + " y no puede procesar el evento interno recibido", InfoTraza.NivelTraza.debug));
        }
    }

    @Override
    public void setDebug(boolean d) {
        this.DEBUG = d;
    }

    @Override
    public boolean getDebug() {
        return this.DEBUG;
    }

    @Override
    public int getEstado() {
        return estado;
    }

    @Override
    public void setEstado(int e) {
        this.estado = e;
    }

    @Override
    public AgenteReactivoAbstracto getAgente() {
        return this;
    }

    @Override
    public void setGestorAReportar(String nombreGestor) {
        try {
            this.logger.info("Estableciendo gestor a reportar. Agente: " + this.nombre + ". Gestor: " + nombreGestor);
            trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                    "Estableciendo gestor a reportar. Agente: " + this.nombre + ". Gestor: " + nombreGestor,
                    InfoTraza.NivelTraza.info));
            itfUsoGestorAReportar = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + nombreGestor);
            itfControlAgteReactivo.setGestorAReportar(itfUsoGestorAReportar);
        } catch (Exception e) {
            logger.error("Error al asignar el gestor a reportar", e);
            trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                    "Error al asignar el gestor a reportar",
                    InfoTraza.NivelTraza.error));
        }
    }

    @Override
    public String getIdentAgente() throws java.rmi.RemoteException {
        return this.nombre;
    }

    @Override
    public ItfControlAgteReactivo getItfControl() {
        return this.itfControlAgteReactivo;
    }

    @Override
    public ItfProductorPercepcion getItfProductorPercepcion() {
        return itfProductorPercepcion;
    }

    public AgenteReactivoAbstracto getPatron() {
        return this;
    }

    @Override
    public AgenteReactivoAbstracto AgenteReactivoImplementacion(ItfControlAgteReactivo itfGestionControlAgteReactivo, ItfProductorPercepcion itfProductorPercepcion, ItfConsumidorPercepcion itfConsumidorPercepcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
