package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.FactoriaProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.ItfGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.TaskManagerFactory;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.FactoriaMotorDeReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfMotorDeReglas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.InputStream;
import org.apache.log4j.Logger;

/**
 * Implementation for Cognitive Agent Control Factory
 *
 * @author Carlos Celorrio Modificacion F Garijo 28/08/2012
 *
 */
public class FactoriaProcesadorObjetivosImp1 extends FactoriaProcesadorObjetivos {

    private Logger log = Logger.getLogger(FactoriaProcesadorObjetivosImp1.class);

    @Override
    public ProcesadorObjetivos crearProcesadorObjetivos(AgenteCognitivo agente, String ficheroResolucionObjetivos) throws ExcepcionEnComponente {
        String identAgte = agente.getIdentAgente();
        InputStream reglas;
        reglas = this.getClass().getResourceAsStream(ficheroResolucionObjetivos);
        ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        try {
            if (reglas != null) {
                ItfMotorDeReglas motorDeReglas = FactoriaMotorDeReglas.instance().crearMotorDeReglas(agente, reglas, ficheroResolucionObjetivos);
                ProcesadorObjetivosImp1 procObj = new ProcesadorObjetivosImp1();
                procObj.SetAgentId(identAgte);
                procObj.SetItfMotorDeReglas(motorDeReglas);
                ItfProcesadorObjetivos itfProcObj = procObj;
                ItfGestorTareas taskManager = TaskManagerFactory.instance().createTaskManager(agente, itfProcObj);
                procObj.SetItfGestorTareas(taskManager);
                procObj.inicializaVariablesGlobales();
                return procObj;
            } else {
                String msg = "Rules not found for " + identAgte + "\n Check the " + ficheroResolucionObjetivos + " is in classpath";
                trazas.trazar(identAgte, msg, InfoTraza.NivelTraza.error);
                log.error(msg);
                throw new ExcepcionEnComponente("FactoriaMotorDeReglas", msg, "MotorDeReglas", "Creacion del Motor de Reglas");
            }
        } catch (Exception e) {
            log.error("Errors compiling rules for agent " + identAgte, e);
            throw new ExcepcionEnComponente("FactoriaMotorDeReglas", "Errors compiling rules for agent " + identAgte, "MotorDeReglas", "Creacion del Motor de Reglas");
        }
    }
}
