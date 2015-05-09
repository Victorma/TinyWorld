/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.informacion.gestionCitas;

import java.io.Serializable;

/**
 *
 * @author Francisco J Garijo
 */
public class PeticionUsuario implements Serializable {
    // Mensajes validos en las propuestas : Ver vocabulario de la aplicacion
    
    
    public String identUsuario;
    public String mensajePeticion;
    public Object justificacion;
    public String identObjectRefPeticion;
    public PeticionUsuario( ) {
        
    }
    
    public PeticionUsuario(String usuarioId) {
        identUsuario= usuarioId;
        mensajePeticion =null;
        justificacion = null;
    }
    public String   getidentObjectRefPeticion(){
        return identObjectRefPeticion;
    }
    public void   setidentObjectRefPeticion(String identObjRef){
         identObjectRefPeticion = identObjRef;
    }
    public void   setMensajePeticion(String msgPeticion){
        mensajePeticion =msgPeticion;
    }
    
    public String   getMensajePeticion(){
        return mensajePeticion;
    }

    public String   getIdentAgente(){
        return identUsuario;
    }
    
    public void   setJustificacion(Object contJustificacion){
        justificacion =contJustificacion;
    }

    public Object   getJustificacion(){
        return justificacion;
    }
    
    @Override
    public String toString(){
        if ( justificacion == null )
            return "Agente Emisor :"+identUsuario+ " MensajePropuesta :+" + mensajePeticion+ "  Justificacion: null "+"\n ";
        else 
            return "Agente Emisor :"+identUsuario+ " MensajePropuesta :+" + mensajePeticion+ "  Justificacion: "+justificacion.toString() +"\n ";
    }
    
}
