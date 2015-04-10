package icaro.aplicaciones.recursos.extractorSemantico;

import gate.AnnotationSet;
import icaro.aplicaciones.recursos.persistenciaAccesoSimple.*;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import java.util.List;
import java.util.HashSet;

public interface ItfUsoExtractorSemantico extends ItfUsoRecursoSimple {
	public HashSet extraerAnotaciones(HashSet anotaciones,String textoUsuario)throws Exception;
}