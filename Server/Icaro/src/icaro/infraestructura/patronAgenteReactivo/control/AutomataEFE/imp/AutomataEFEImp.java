package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp;

import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.Operacion;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.TablaEstadosControl;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.XMLParserTablaEstados;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.EjecutorDeAccionesAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ExcepcionNoSePudoCrearAutomataEFE;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItfUsoAutomataEFE;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.util.Set;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class AutomataEFEImp implements ItfUsoAutomataEFE {

    public boolean DEBUG = false;
    protected int traza = 0;
    private EjecutorDeAccionesAbstracto acciones;
    protected String estadoActual;
    private TablaEstadosControl theTablaEstadosControl;
    protected String nombreAgente;
    public final static int NIVEL_TRAZA_DESACTIVADO = 0;
    public final static int NIVEL_TRAZA_SOLO_TRANSICIONES = 1;
    public final static int NIVEL_TRAZA_TODO = 2;
    private Logger logger = Logger.getRootLogger();
    protected ItfUsoRecursoTrazas trazas;
    private Set<String> conjuntoInputs;
    private String descripcionAutomata;

    public AutomataEFEImp(String NombreFicheroDescriptor,
            EjecutorDeAccionesAbstracto accionesSem, int nivelTraza, String nombreAgente) throws ExcepcionNoSePudoCrearAutomataEFE {
        try {
            trazas = (ItfUsoRecursoTrazas) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
        } catch (Exception e) {
            System.out.println("No se pudo usar el recurso de trazas");
        }
        try {
            XMLParserTablaEstados parser = new XMLParserTablaEstados(NombreFicheroDescriptor);
            theTablaEstadosControl = parser.extraeTablaEstados();
            conjuntoInputs = parser.extraeConjuntoInputs();
            descripcionAutomata = parser.extraerDescripcionTablaEstados();
            acciones = accionesSem;
            this.nombreAgente = nombreAgente;
            cambiaEstado(theTablaEstadosControl.dameEstadoInicial());
            if (nivelTraza == 2) {
                DEBUG = true;
            } else {
                DEBUG = false;
            }
            traza = nivelTraza;
            logger.debug("Usando el automata del fichero: " + NombreFicheroDescriptor);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                    "Usando el autmata del fichero: " + NombreFicheroDescriptor,
                    InfoTraza.NivelTraza.debug));
            logger.debug(this.toString());
        } catch (ExcepcionNoSePudoCrearAutomataEFE e) {
            e.putParteAfectada("XMLParser Tabla de Estados");
            if (trazas != null) {
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                        "no se pudo crear el automata EFE porque se produjo un error en " + e.getParteAfectada()
                        + "debido a:" + e.getCausa() + " en el contexto " + e.getContextoExcepcion(),
                        InfoTraza.NivelTraza.error));
            }
            throw e;
        }
    }

    @Override
    public boolean estasEnEstadoFinal() {
        return (theTablaEstadosControl.esEstadoFinal(estadoActual));
    }

    @Override
    public String getEstadoAutomata() {
        return estadoActual;
    }

    @Override
    public synchronized boolean procesaInput(String input, Object[] parametros) {
        Operacion op;
        if (conjuntoInputs.contains(input)) {
            if (theTablaEstadosControl.esInputValidoDeEstado(estadoActual, input)) {
                op = theTablaEstadosControl.dameOperacion(estadoActual, input);
                try {
                    logger.debug("Ejecutando accion: " + op.accionSemantica);
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                            "Ejecutando accion: " + op.accionSemantica,
                            InfoTraza.NivelTraza.debug));
                    String estadoAntesdeTransitar = estadoActual;
                    cambiaEstado(op.estadoSiguiente);
                    acciones.ejecutarAccion(op.accionSemantica, parametros, op.modoTransicionBloqueante);

                    logger.info("Transicion usando input:" + input + "  :" + estadoActual + " -> " + op.estadoSiguiente);
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                            "Transicion usando input '" + input + "'. ESTADO ACTUAL: "
                            + estadoAntesdeTransitar + " -> " + "ESTADO SIGUIENTE: " + op.estadoSiguiente,
                            InfoTraza.NivelTraza.info));
                    return true;
                } catch (ExcepcionEjecucionAcciones ex) {
                    java.util.logging.Logger.getLogger(AutomataEFEImp.class.getName()).log(Level.SEVERE, null, ex);
                    this.estadoActual = "errorInternoIrrecuperable";

                    logger.info("Error al procesar el " + input + "Ejecutando accion: " + op.accionSemantica + "se transita al estado" + estadoActual);
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                            "Error al procesar el " + input + "Ejecutando accion: " + op.accionSemantica + "se transita al estado" + estadoActual,
                            InfoTraza.NivelTraza.error));
                }
            } else {
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                        "AVISO: Input ignorado.El input: "
                        + input
                        + " no pertenece a los inputs validos para el estado actual: "
                        + estadoActual,
                        InfoTraza.NivelTraza.info));
                logger.info("AVISO: Input ignorado.El input: " + input
                        + " no pertenece a los inputs validos para el estado actual: " + estadoActual);
            }
        } else {
            logger.error("ERROR: Input " + input + " no valido para " + descripcionAutomata);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                    "ERROR: Input " + input + " no valido para " + descripcionAutomata,
                    InfoTraza.NivelTraza.error));
            logger.error(conjuntoInputs);
        }
        return false;
    }

    public boolean procesaInput(Object input, Object... parametros) {
        Object[] valoresParametrosAccion = {};
        int i = 0;
        valoresParametrosAccion = new Object[(parametros).length];
        for (Object param : parametros) {
            valoresParametrosAccion[i] = param;
            i++;
        }
        if (this.procesaInput(input, valoresParametrosAccion)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean procesaInput(Object input) {
        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                "ERROR: Operacion no soportada por este modelo de Automata- esta operacion  requiere otro tipo de automata de E.F. ",
                InfoTraza.NivelTraza.error));
        return false;
    }

    @Override
    public synchronized void transita(String input) {
        if (theTablaEstadosControl.esInputValidoDeEstado(estadoActual, input)) {
            Operacion op = theTablaEstadosControl.dameOperacion(estadoActual, input);
            logger.info("Transicion Directa input:" + input + "  :" + estadoActual + " -> " + op.estadoSiguiente);
            cambiaEstado(op.estadoSiguiente);
        } else {
            logger.info("AVISO: Input de Transicion directa .El input: " + input + " no pertenece a los inputs validos para el estado actual: " + estadoActual);
        }
    }

    @Override
    public String toString() {
        String dev = theTablaEstadosControl.toString();
        dev += "\nEstado actual= " + estadoActual;
        return dev;
    }

    @Override
    public synchronized void volverAEstadoInicial() {
        this.cambiaEstado(this.theTablaEstadosControl.dameEstadoInicial());
    }

    @Override
    public synchronized void cambiaEstado(String estado) {
        estadoActual = estado;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
