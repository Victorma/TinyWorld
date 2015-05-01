package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItfUsoAutomataEFE;
import icaro.infraestructura.patronAgenteReactivo.control.GestorAccionesAgteReactivoImp;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.Serializable;
import java.rmi.RemoteException;

public class ProcesadorInfoReactivoAutInObjImp0 extends ProcesadorInfoReactivoAbstracto implements Serializable {

    private ItfUsoAutomataEFE automataControl;
    private GestorAccionesAgteReactivoImp gestorAcciones;
    private boolean resultadoProcesoInput;

    public ProcesadorInfoReactivoAutInObjImp0(ItfUsoAutomataEFE interpAutom, GestorAccionesAgteReactivoImp gestAcciones,
            AgenteReactivoAbstracto implItfsagente) throws RemoteException {
        super("Agente reactivo ");
        identAgte = implItfsagente.getIdentAgente();
        automataControl = interpAutom;
        gestorAcciones = gestAcciones;
        agente = implItfsagente;
    }

    @Override
    public synchronized void procesarInfoControlAgteReactivo(Object infoParaProcesar) {
        NombresPredefinidos.RECURSO_TRAZAS_OBJ.trazar(identAgte, "Se procesa el  input " + infoParaProcesar, InfoTraza.NivelTraza.debug);
        if (this.estado == InterfazGestion.ESTADO_ACTIVO) {
            if (infoParaProcesar instanceof InfoContEvtMsgAgteReactivo) {
                InfoContEvtMsgAgteReactivo infoParaAutomata = (InfoContEvtMsgAgteReactivo) infoParaProcesar;
                resultadoProcesoInput = automataControl.procesaInput(infoParaAutomata.getInput(), infoParaAutomata.getvaloresParametrosAccion());
            } else {
                resultadoProcesoInput = automataControl.procesaInput(infoParaProcesar);
            }
        }
        if (!resultadoProcesoInput) {
            NombresPredefinidos.RECURSO_TRAZAS_OBJ.trazar(identAgte, "No hay transicion asociada al input " + infoParaProcesar, InfoTraza.NivelTraza.debug);
        }
    }

    @Override
    public synchronized String getEstadoControlAgenteReactivo() {
        return automataControl.getEstadoAutomata();
    }

    public void setDebug(boolean d) {
        this.DEBUG = d;
    }

    public boolean getDebug() {
        return this.DEBUG;
    }

    @Override
    public synchronized void procesarInput(Object input, Object... paramsAccion) {
        NombresPredefinidos.RECURSO_TRAZAS_OBJ.trazar(identAgte, "Se procesa el  input " + input, InfoTraza.NivelTraza.debug);
        String inputAutomata;
        Object paramPosicion0 = null;
        Object[] valoresParametrosAccion;
        int posparametros = 0;
        if (input == null) {
            NombresPredefinidos.RECURSO_TRAZAS_OBJ
                    .aceptaNuevaTraza(new InfoTraza(this.getClass().getSimpleName(),
                            "ERROR: El input a procesar no puede ser null ", InfoTraza.NivelTraza.error));
        }
        if (!(input instanceof String)) {
            inputAutomata = input.getClass().getSimpleName();
            paramPosicion0 = input;
            posparametros++;
        } else {
            inputAutomata = (String) input;
        }
        if (paramsAccion == null) {
            valoresParametrosAccion = new Object[1];
            valoresParametrosAccion[0] = null;
        } else if ((paramsAccion.length == 1 && paramsAccion[0] == null)) {
            valoresParametrosAccion = new Object[1];
            valoresParametrosAccion[0] = null;
        } else {
            valoresParametrosAccion = new Object[(paramsAccion).length + posparametros];
            if (posparametros == 1) {
                valoresParametrosAccion[0] = paramPosicion0;
            }
            for (Object param : paramsAccion) {
                valoresParametrosAccion[posparametros] = param;
                posparametros++;
            }
        }
        automataControl.procesaInput(inputAutomata, valoresParametrosAccion);
    }

    @Override
    public void inicializarInfoGestorAcciones(String identAgte, ItfProductorPercepcion itfEvtosInternos) {
        if (gestorAcciones != null) {
            percepcionProductor = itfEvtosInternos;
            gestorAcciones.inicializarInfoAccionesAgteReactivo(identAgte, percepcionProductor, this);
        }
    }

    public synchronized int getEstado() {
        return estado;
    }

    public synchronized void setEstado(int e) {
        this.estado = e;
    }
}
