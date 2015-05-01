package icaro.infraestructura.patronAgenteReactivo.control.acciones;

import icaro.infraestructura.entidadesBasicas.informes.InformeTemporizacion;
import icaro.infraestructura.entidadesBasicas.informes.InformeError;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.accionesAutomataEF.GeneracionInputTimeout;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.gestorAcciones.ItfGestorAcciones;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoInternoAgteReactivo;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AccionSincrona {

    protected ItfProductorPercepcion itfEnvioEventosInternos;
    protected ItfUsoAgenteReactivo itfUsoAgente;
    protected String identAccion;
    protected Object[] params;
    protected boolean terminada;
    protected ItfUsoRecursoTrazas trazas;
    protected ItfUsoRepositorioInterfaces repoInterfaces;
    protected ItfUsoConfiguracion itfConfig;
    protected ItfGestorAcciones gestorAcciones;
    protected ComunicacionAgentes comunicator;
    protected String identAgte;

    public AccionSincrona() {
        this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
    }

    public void inicializar(String identAgte, ItfProductorPercepcion iftPercepAgte) {
        this.identAgte = identAgte;
        this.itfEnvioEventosInternos = iftPercepAgte;
    }

    public abstract void ejecutar(Object... params);

    public void generarInformeError(String idAccion, InformeError informe) {
    }

    public void generarInputAutomata(Object input) {
        if (itfEnvioEventosInternos != null) {
            itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(identAccion, input, null));
        }
    }

    public void generarInputTemporizador(long milis, String idAccion, String msgTimeout) {
        if (msgTimeout == null) {
            msgTimeout = NombresPredefinidos.PREFIJO_MSG_TIMEOUT;
        }
        InformeTemporizacion informeTemp = new InformeTemporizacion(idAccion, msgTimeout);
        GeneracionInputTimeout informeTemporizado = new GeneracionInputTimeout(milis, itfUsoAgente, informeTemp);
        informeTemporizado.start();
    }

    public void generarInformeTemporizadoFromConfigProperty(String identproperty, Objetivo contxtGoal, String idAgenteOrdenante, String msgTimeout) {
        try {
            int valorTimeout = this.getItfUsoConfiguracion().getValorNumericoPropiedadGlobal(NombresPredefinidos.PREFIJO_PROPIEDAD_TAREA_TIMEOUT + identproperty);
            if (valorTimeout <= 0) {
                trazas.trazar("Accion", "Se ejecuta la accion " + this.getIdentAccion()
                        + " No se puede obtener el nombre de la propiedad en la configuracion. Se intenta la propiedad por defecto"
                        + " Verifique el nombre de la propiedad en la descripcion de la organizacion :  " + NombresPredefinidos.PREFIJO_PROPIEDAD_TAREA_TIMEOUT + identproperty, NivelTraza.error);
                valorTimeout = this.getItfUsoConfiguracion().getValorNumericoPropiedadGlobal(NombresPredefinidos.PROPERTY_TIME_TIMEOUT_POR_DEFECTO);
                if (valorTimeout <= 0) {
                    trazas.trazar("Accion", "Se ejecuta la tarea " + this.getIdentAccion()
                            + " No se puede obtener el nombre de la propiedad en la configuracion.El valor de la propiedad por defecto NO esta definido"
                            + " Defina el nombre de la propiedad :" + NombresPredefinidos.PROPERTY_TIME_TIMEOUT_POR_DEFECTO
                            + "en la descripcion de la organizacion :  ", NivelTraza.error);
                }
            } else if (msgTimeout == null) {
                msgTimeout = NombresPredefinidos.PREFIJO_MSG_TIMEOUT;
            }
            this.generarInputTemporizador(valorTimeout, identproperty, msgTimeout);
            trazas.trazar("Accion", "Se ejecuta la accion " + this.getIdentAccion()
                    + " Se activa un informe temporizado :  " + msgTimeout, NivelTraza.debug);

        } catch (ExcepcionEnComponente ex) {
            trazas.trazar("Accion", "Se ejecuta la accion " + this.getIdentAccion()
                    + " No se puede obtener el nombre de la propiedad en la configuracion."
                    + " Verifique el nombre de la propiedad en la descripcion de la organizacion :  " + identproperty, NivelTraza.error);
            Logger.getLogger(AccionSincrona.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(AccionSincrona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ItfUsoConfiguracion getItfUsoConfiguracion() {
        if (itfConfig == null) {
            try {
                itfConfig = (ItfUsoConfiguracion) repoInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);
            } catch (Exception ex) {
                Logger.getLogger(AccionSincrona.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return itfConfig;
    }

    public void procesarInput(Object input, Object[] infoComplementaria) {
        if (itfEnvioEventosInternos == null) {
            trazas.trazar(identAgte, " El interfaz para el envio de eventos internos no esta definida "
                    + " El input:  " + input + " No sera procesado ", InfoTraza.NivelTraza.error);
        } else {
            itfEnvioEventosInternos.produceParaConsumirInmediatamente(new EventoInternoAgteReactivo(identAgte, input, infoComplementaria));
        }
    }

    public void setIdentAccion(String idAccion) {
        this.identAccion = idAccion;
    }

    public String getIdentAccion() {
        return identAccion;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object... params) {
        this.params = params;
    }

    public void setComunicator(ComunicacionAgentes comunicator) {
        this.comunicator = comunicator;
    }

    public ComunicacionAgentes getComunicator() {
        return this.comunicator;
    }

    public abstract void getInfoObjectInput(Object obj);
}
