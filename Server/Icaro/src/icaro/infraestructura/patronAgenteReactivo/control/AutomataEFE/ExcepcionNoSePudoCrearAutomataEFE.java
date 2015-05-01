package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;

public class ExcepcionNoSePudoCrearAutomataEFE extends ExcepcionEnComponente {
    public ExcepcionNoSePudoCrearAutomataEFE(String parteAfectada, String causa, String contextoExcepcion) {
        super(parteAfectada, causa, contextoExcepcion);
        identComponente = "Automata de Estados Finitos";
        identSuperComponente = "patronAgenteReactivo";
    }
}
