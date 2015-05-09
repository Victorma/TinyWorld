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
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.io.InputStream;
import org.apache.log4j.Logger;

/**
 * Implementation for Cognitive Agent Control Factory
 * @author Carlos Celorrio
 *
 */
public class FactoriaProcesadorObjetivosImp extends FactoriaProcesadorObjetivos {
	private Logger log = Logger.getLogger(FactoriaProcesadorObjetivosImp.class);
	@Override
	public ProcesadorObjetivos crearProcesadorObjetivos( AgenteCognitivo agente,String ficheroResolucionObjetivos ) throws ExcepcionEnComponente {			
		String agentName = agente.getIdentAgente();
            	InputStream reglas ;
           //     Path rutaReglas = FileSystems.getDefault().getPath(ficheroResolucionObjetivos).getFileSystem().;
          //      URL reglas2 = rutaReglas.getFileName();
           //     URL reglasF ;
		      reglas = this.getClass().getResourceAsStream(ficheroResolucionObjetivos);
                      ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
             //       reglasF = this.getClass().getResource(ficheroResolucionObjetivos);
                     try {
	   	      if (reglas!=null) {
           //              if (reglasF!=null) {  
                        ProcesadorObjetivosImp1  procObj = new ProcesadorObjetivosImp1();
                        ItfProcesadorObjetivos itfProcObj = procObj;
			ItfMotorDeReglas motorDeReglas = FactoriaMotorDeReglas.instance().crearMotorDeReglas(agente);
			ItfGestorTareas taskManager = TaskManagerFactory.instance().createTaskManager(agente, itfProcObj);
			
                        ItfUsoRepositorioInterfaces repoIntfs = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
//                        log.info("Compiling rules...");
                        System.out.println("Compiling rules...");
                   //     motorDeReglas.compileRules2(reglasF);
                        motorDeReglas.compileRules(reglas);            			            
			motorDeReglas.addGlobalVariable(NombresPredefinidos.TASK_MANAGER_GLOBAL, taskManager);
                        motorDeReglas.addGlobalVariable(NombresPredefinidos.ITFUSO_RECURSOTRAZAS_GLOBAL, trazas);
                        motorDeReglas.addGlobalVariable(NombresPredefinidos.AGENT_ID_GLOBAL, agentName);
                        
                        int index = getNumberStartIndex(agentName);
                        String number = getNumber(agentName,index);
			log.info("Rules Compiled Successfully.");
                        procObj.SetItfGestorTareas(taskManager);
                        procObj.SetItfMotorDeReglas(motorDeReglas);
			return procObj;
		      } else {
			    String msg = "Rules not found for "+agentName +"\n Check the "+ ficheroResolucionObjetivos+" is in classpath";
			    trazas.trazar(agentName, msg, InfoTraza.NivelTraza.error) ;     
                                    log.error(msg);
                                        //                    ExcepcionEnComponente(String identComponente,String causa, String identParteAfectada,String contextoExcepcion )
			            throw new ExcepcionEnComponente("FactoriaMotorDeReglas",msg, "MotorDeReglas", "Creacion del Motor de Reglas");
		}
		} catch (Exception e) {
			log.error("Errors compiling rules for agent "+agentName, e);
			throw new ExcepcionEnComponente("FactoriaMotorDeReglas","Errors compiling rules for agent "+agentName, "MotorDeReglas", "Creacion del Motor de Reglas");
		}
	}
       
 //El string finaliza en un número.
    //Este método devuelve la posición en el que empieza el numero.
    private int getNumberStartIndex(String s){
    	
    	int index=0;
    	
    	for (int x=s.length()-1;x>=0;x--){
    		char ch = s.charAt(x);
    		String sch = "" + ch;
    		int chint = (int)ch;    		
    		int numberchint = chint - 48; //48 es el valor ascii del 0

    		if ((numberchint<0) || (numberchint >= 10)) //no es un numero
    		   {   
    			  return x+1;
    		}                   		
    	}
    	return index;
    }
    
    //El string finaliza en un número.
    //Este método devuelve el substring que contiene el numero.    
    private String getNumber(String s, int index){
    	String stringNumber;
    	stringNumber = s.substring(index);    	    	
    	return stringNumber;
    }

}
