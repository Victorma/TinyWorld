package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;

public abstract class EjecutorDeAccionesAbstracto extends java.lang.Thread {

    public EjecutorDeAccionesAbstracto(String string) {
        super(string);
    }

    public EjecutorDeAccionesAbstracto() {
    }

    public abstract void ejecutarAccion(String accion, Object[] parametros, boolean modoBloqueante) throws ExcepcionEjecucionAcciones;
}
