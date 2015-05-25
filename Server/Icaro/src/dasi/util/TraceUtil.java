package dasi.util;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

public final class TraceUtil {
    public static void acceptNewExecRules(String agentId, String rule) {
        ItfUsoRecursoTrazas recursoTrazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        recursoTrazas.aceptaNuevaTrazaEjecReglas(agentId, "EJECUTAR REGLA: " + rule);
    }
    
    public static void acceptNewExecRules(String agentId, String rule, String premsg) {
        ItfUsoRecursoTrazas recursoTrazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        recursoTrazas.aceptaNuevaTrazaEjecReglas(agentId, premsg + " -> EJECUTAR REGLA: " + rule);
    }
    
    public static void acceptNew(String agentId, String taskId, Exception e) {
        ItfUsoRecursoTrazas recursoTrazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        String content = "Error al ejecutar la tarea: " + taskId + " " + e;
        recursoTrazas.aceptaNuevaTraza(new InfoTraza(agentId, content, InfoTraza.NivelTraza.error));
    }
}
