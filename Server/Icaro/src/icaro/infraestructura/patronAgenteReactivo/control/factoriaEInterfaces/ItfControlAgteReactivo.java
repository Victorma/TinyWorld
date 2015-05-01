package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

public interface ItfControlAgteReactivo extends InterfazGestion {
    public void setGestorAReportar(ItfUsoAgenteReactivo itfUsoGestorAReportar);
    public ItfUsoAgenteReactivo getGestorAReportar();
    public void procesarInfoControlAgteReactivo(Object infoParaProcesar);
    public void procesarInput(Object input, Object... paramsAccion);
    public String getEstadoControlAgenteReactivo();
}
