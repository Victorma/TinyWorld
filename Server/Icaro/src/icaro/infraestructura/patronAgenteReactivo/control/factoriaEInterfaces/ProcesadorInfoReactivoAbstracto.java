package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;

public abstract class ProcesadorInfoReactivoAbstracto extends Thread implements ItfControlAgteReactivo {

    public boolean DEBUG = false;
    public AccionesSemanticasAgenteReactivo accionesSemanticasAgenteCreado;
    public int estado = InterfazGestion.ESTADO_CREADO;
    public String identAgte;
    public AgenteReactivoAbstracto agente;
    protected ItfUsoAgenteReactivo itfUsoGestorAreportar;
    public ItfConsumidorPercepcion percepcionConsumidor;
    public ItfProductorPercepcion percepcionProductor;

    public ProcesadorInfoReactivoAbstracto(String string) {
        super(string);
    }

    @Override
    public void arranca() {
        if (DEBUG) {
            System.out.println(identAgte + ": arranca()");
        }
        if (estado == InterfazGestion.ESTADO_CREADO) {
            estado = InterfazGestion.ESTADO_ACTIVO;
        }
    }

    @Override
    public void continua() {
        estado = InterfazGestion.ESTADO_ACTIVO;
    }

    @Override
    public synchronized int obtenerEstado() {
        if (DEBUG) {
            System.out.println(identAgte + ": obtenerEstado()");
        }
        return estado;
    }

    @Override
    public synchronized void para() {
        estado = InterfazGestion.ESTADO_PARADO;
    }

    @Override
    public synchronized void termina() {
        if (DEBUG) {
            System.out.println(identAgte + ":terminando ...");
        }
        estado = InterfazGestion.ESTADO_TERMINADO;
    }

    @Override
    public void setGestorAReportar(ItfUsoAgenteReactivo itfUsoGestor) {
        itfUsoGestorAreportar = itfUsoGestor;
    }

    @Override
    public ItfUsoAgenteReactivo getGestorAReportar() {
        return itfUsoGestorAreportar;
    }

    public abstract void inicializarInfoGestorAcciones(String identAgte, ItfProductorPercepcion itfEvtosInternos);
}
