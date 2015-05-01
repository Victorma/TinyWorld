package icaro.infraestructura.patronAgenteReactivo.control.acciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoInternoAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import org.apache.log4j.Logger;

public abstract class AccionesSemanticasAgenteReactivo {

    public ItfUsoAgenteReactivo itfUsoGestorAReportar;
    protected ItfControlAgteReactivo itfControlAgente;
    protected ItfGestionAgenteReactivo itfGestionAgte;
    protected String nombreAgente;
    protected AgenteReactivoAbstracto ctrlGlobalAgenteReactivo;
    protected Logger logger;
    public ItfUsoRecursoTrazas trazas;
    public ItfUsoRepositorioInterfaces itfUsoRepositorio;
    private String identEstaClase = this.getClass().getSimpleName();
    private boolean recursoTrazasDefinido = false;
    private ItfUsoAgenteReactivo itfGestorAreportar;
    protected ComunicacionAgentes comunicator;
    protected ItfProductorPercepcion itfEnvioEventosInternos;

    public AccionesSemanticasAgenteReactivo() {
    }

    public void inicializarAcciones(String identAgte, ItfControlAgteReactivo itfControl, ItfProductorPercepcion itfProductPercept) {
        this.logger = Logger.getLogger(this.getClass().getCanonicalName());
        if (NombresPredefinidos.RECURSO_TRAZAS_OBJ != null) {
            this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
            recursoTrazasDefinido = true;
        } else {
            this.infoParaDesarrollador("El recuros de trazas no ha sido creado Ver mensajes del Logger");
        }
        if (NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ != null) {
            this.itfUsoRepositorio = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
        } else {
            this.infoParaDesarrollador("El repositorio de interfaces  no ha sido creado Ver mensajes del Logger");
        }
        if (identAgte != null) {
            this.nombreAgente = identAgte;
        } else {
            this.infoParaDesarrollador("El nombre del agente no puede ser null ");
        }
        if (itfControl != null) {
            this.itfControlAgente = itfControl;
        } else {
            this.infoParaDesarrollador("La interfaz de Control del agente es  null y debe ser definida");
        }
        if (itfProductPercept != null) {
            this.itfEnvioEventosInternos = itfProductPercept;
        } else {
            this.infoParaDesarrollador("La interfaz de la percepcion que implementa el  Control del agente es  null y debe ser definida");
        }
        this.getComunicator();
    }

    public void infoParaDesarrollador(String mensaje) {
        if (recursoTrazasDefinido) {
            this.trazas.trazar(identEstaClase, mensaje, InfoTraza.NivelTraza.error);
        }
        logger.error(mensaje);
    }

    public void setNombreAgente(String nombreAgente) {
        this.nombreAgente = nombreAgente;
    }

    public ItfGestionAgenteReactivo getIftGestionAgente() {
        if (itfGestionAgte != null) {
            return this.itfGestionAgte;
        } else {
            this.infoParaDesarrollador("Se obtiene la interfaz de  Gestion del agente ");
            if (this.itfUsoRepositorio != null) {
                try {
                    itfGestionAgte = (ItfGestionAgenteReactivo) itfUsoRepositorio.obtenerInterfazGestion(nombreAgente);
                } catch (Exception ex) {
                    this.infoParaDesarrollador("Se ha producido un error al obtener la interfaz de gestion del agente " + ex);
                    return null;
                }
            }
            return itfGestionAgte;
        }
    }

    public void setCtrlGlobalAgenteReactivo(AgenteReactivoAbstracto agteReactivo) {
        this.ctrlGlobalAgenteReactivo = agteReactivo;
    }

    public void setItfUsoGestorAReportar(ItfUsoAgenteReactivo itfUsoA) {
        this.itfUsoGestorAReportar = itfUsoA;
    }

    public ItfUsoAgenteReactivo getItfUsoGestorAReportar() {
        if (itfGestorAreportar != null) {
            return itfGestorAreportar;
        }
        this.itfUsoGestorAReportar = itfControlAgente.getGestorAReportar();
        if (this.itfUsoGestorAReportar == null) {
            infoParaDesarrollador("El gestor a reportar es null. Es necesario definirlo");
        }
        return itfGestorAreportar;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void informaraMiAutomata(String input, Object... infoComplementaria) {
        Object paramsAccion[] = {};
        if (infoComplementaria == null || (infoComplementaria.length == 1 && infoComplementaria[0] == null)) {
            paramsAccion = new Object[0];
        } else {
            paramsAccion = new Object[infoComplementaria.length];
            for (int i = 0; (i < infoComplementaria.length); i++) {
                paramsAccion[i] = infoComplementaria[i];
            }
        }
        if (itfEnvioEventosInternos != null) {
            itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(nombreAgente, input, paramsAccion));
        } else {
            trazas.trazar(nombreAgente, " El interfaz para el envio de eventos internos no esta definida "
                    + " El input:  " + input + " No sera procesado ", InfoTraza.NivelTraza.error);
        }
    }

    public void informaraMiAutomata(String input) {
        Object paramsAccion[] = new Object[0];
        if (itfEnvioEventosInternos != null) {
            itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(nombreAgente, input, paramsAccion));
        } else {
            trazas.trazar(nombreAgente, " El interfaz para el envio de eventos internos no esta definida "
                    + " El input:  " + input + " No sera procesado ", InfoTraza.NivelTraza.error);
        }
    }

    public void procesarInput(Object input, Object[] infoComplementaria) {
        if (itfEnvioEventosInternos == null) {
            trazas.trazar(nombreAgente, " El interfaz para el envio de eventos internos no esta definida "
                    + " El input:  " + input + " No sera procesado ", InfoTraza.NivelTraza.error);
        } else {
            itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(nombreAgente, input, infoComplementaria));
        }
    }

    public abstract void clasificaError();

    public String getNombreAgente() {
        return nombreAgente;
    }

    public void generarTimeOut(long milis, String identInput, String origen, String destino) {
        GenerarEventoTimeOut timeout = new GenerarEventoTimeOut(milis, identInput, origen, destino, this.itfUsoRepositorio);
        logger.debug("Generando evento de timeout de " + milis + " milisegundos");
        timeout.start();
    }

    public ComunicacionAgentes getComunicator() {
        if (comunicator == null) {
            comunicator = new ComunicacionAgentes(nombreAgente);
        }
        return comunicator;
    }

    public void ejecutar(Object... params) {
    }
}
