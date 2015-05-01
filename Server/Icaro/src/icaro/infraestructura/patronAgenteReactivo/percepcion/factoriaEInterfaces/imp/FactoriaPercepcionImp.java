package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.imp;

import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.FactoriaPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.PercepcionAbstracto;

public class FactoriaPercepcionImp extends FactoriaPercepcion {
    
    @Override
    public PercepcionAbstracto crearPercepcion() {
        return new PercepcionAgenteReactivoImp();
    }

    @Override
    public PercepcionAbstracto crearPercepcion(AgenteReactivoAbstracto agente, ItfControlAgteReactivo itfControl) {
        return new PercepcionAgenteReactivoImp(agente, new ProcesadorItemsPercepReactivo(agente, itfControl));
    }
}
