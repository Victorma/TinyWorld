package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.AutomataEFEImp;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import java.io.Serializable;
import java.rmi.RemoteException;

public class ProcesadorInfoReactivoImp extends ProcesadorInfoReactivoAbstracto implements Serializable {

    private AutomataEFEImp automataControl;

    public ProcesadorInfoReactivoImp(AutomataEFEImp automata, AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas,
            AgenteReactivoAbstracto implItfsagente) throws RemoteException {
        super("Agente reactivo " + implItfsagente.getIdentAgente());
        automataControl = automata;
        accionesSemanticasAgenteCreado = accionesSemanticasEspecificas;
        agente = implItfsagente;
        this.arranca();
    }

    @Override
    public synchronized void procesarInfoControlAgteReactivo(Object infoParaProcesar) {
        if (this.estado == InterfazGestion.ESTADO_ACTIVO) {
            if (infoParaProcesar instanceof InfoContEvtMsgAgteReactivo) {
                InfoContEvtMsgAgteReactivo infoParaAutomata = (InfoContEvtMsgAgteReactivo) infoParaProcesar;
                automataControl.procesaInput(infoParaAutomata.getInput(), infoParaAutomata.getvaloresParametrosAccion());
            } else {
                System.out.println(identAgte + ": El input debe ser de clase InfoContEvtMsgAgteReactivo y el objeto es clase" +
                        infoParaProcesar.getClass() + " Cambiar el contenido del evento");
            }
        }
    }

    @Override
    public synchronized String getEstadoControlAgenteReactivo() {
        return automataControl.getEstadoAutomata();
    }

    @Override
    public void procesarInput(Object input, Object... paramsAccion) {
        if (paramsAccion == null) {
            automataControl.procesaInput(input);
        }
        automataControl.procesaInput(input, paramsAccion);
    }

    public synchronized int getEstado() {
        return estado;
    }

    public synchronized void setEstado(int e) {
        this.estado = e;
    }

    @Override
    public synchronized void setGestorAReportar(ItfUsoAgenteReactivo itfUsoGestorAReportar) {
        accionesSemanticasAgenteCreado.setItfUsoGestorAReportar(itfUsoGestorAReportar);
    }

    @Override
    public void inicializarInfoGestorAcciones(String identAgte, ItfProductorPercepcion itfEvtosInternos) {
        if (accionesSemanticasAgenteCreado != null) {
            accionesSemanticasAgenteCreado.inicializarAcciones(identAgte, this, itfEvtosInternos);
        }
    }

    @Override
    public String toString() {
        return identAgte;
    }
}
