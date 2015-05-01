package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import org.apache.log4j.Logger;

public interface ItfUsoAutomataEFE {
    public boolean estasEnEstadoFinal();
    public String getEstadoAutomata();
    public boolean procesaInput(String input, Object[] parametros);
    public boolean procesaInput(Object input);
    public void volverAEstadoInicial();
    public void cambiaEstado(String estado);
    public void transita(String input);
    public void setLogger(Logger logger);
    public Logger getLogger();
}
