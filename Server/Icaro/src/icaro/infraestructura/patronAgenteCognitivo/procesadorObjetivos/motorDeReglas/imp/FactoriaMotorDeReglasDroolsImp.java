/*
 * FactoriaAdaptadorMotorReglasDrools.java
 *
 * Creado 18 de abril de 2007, 11:56
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.imp;

import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.FactoriaMotorDeReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfMotorDeReglas;
import java.io.InputStream;



/**
 * Drools Rule Engine Factory 
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 * @author Carlos Celorrio
 */
public class FactoriaMotorDeReglasDroolsImp extends FactoriaMotorDeReglas {
    
	/**
	 * Returns the DROOLS rule engine
	 */
    @Override
    public ItfMotorDeReglas crearMotorDeReglas(AgenteCognitivo agent){
   //     return new MotorDeReglasDroolsImp(agent);
   //       return new MotorDeReglasDroolsImp2(agent);
        return new MotorDeReglasDroolsImp4(agent);
    }
    @Override
    public ItfMotorDeReglas crearMotorDeReglas(AgenteCognitivo agent,InputStream reglas,String ficheroReglas){
   //     return new MotorDeReglasDroolsImp(agent);
        
   //       return new MotorDeReglasDroolsImp2(agent);
        return new MotorDeReglasDroolsImp4(agent);
    }
    
}
