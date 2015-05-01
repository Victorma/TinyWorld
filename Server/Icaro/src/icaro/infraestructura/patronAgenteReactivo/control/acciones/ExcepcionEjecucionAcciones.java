package icaro.infraestructura.patronAgenteReactivo.control.acciones;

import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;

public class ExcepcionEjecucionAcciones extends ExcepcionEnComponente {
    public ExcepcionEjecucionAcciones(String parteAfectada, String causa, String contextoExcepcion) {
        super(parteAfectada, causa, contextoExcepcion);
        identComponente = "Acciones Semanticas";
        identSuperComponente = "patronAgenteReactivo";
    }
}
