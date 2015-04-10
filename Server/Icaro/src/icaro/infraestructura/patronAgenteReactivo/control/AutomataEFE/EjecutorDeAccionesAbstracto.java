package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;



/**
 *  
 *
 *@author     Felipe Polo
 *@created    3 de Diciembre de 2007
 */
// public abstract class EjecutroDeAccionesAbstracto extends java.lang.Thread implements ItfAccionesSemanticas {
public abstract class EjecutorDeAccionesAbstracto extends java.lang.Thread  {
	public EjecutorDeAccionesAbstracto(String string) {
		super(string);
	}

	public EjecutorDeAccionesAbstracto() {}
        public abstract void ejecutarAccion(String accion, Object[] parametros, boolean modoBloqueante) throws ExcepcionEjecucionAcciones ;
        ;
	
}
