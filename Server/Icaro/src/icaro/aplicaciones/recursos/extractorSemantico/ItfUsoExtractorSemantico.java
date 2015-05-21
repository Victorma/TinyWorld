package icaro.aplicaciones.recursos.extractorSemantico;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import java.util.HashSet;

public interface ItfUsoExtractorSemantico extends ItfUsoRecursoSimple {
    public HashSet extraerAnotaciones(HashSet anotaciones, String textoUsuario) throws Exception;
}
