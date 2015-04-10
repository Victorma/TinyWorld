/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.informacion.gestionCitas;

/**
 *
 * @author FGarijo
 */
public class Notificacion {
    public String identNotificador;
    public String mensajeNotificacion;
    public Object contexto;
    public String identObjectRefNotificacion;
    public String tipoNotificacion;
    public Notificacion( ) {
        
    }
    
    public Notificacion(String usuarioId) {
        identNotificador= usuarioId;
        tipoNotificacion=
        mensajeNotificacion =null;
        contexto = null;
    }
    public String   getidentObjectRefNotificacion(){
        return identObjectRefNotificacion;
    }
    public void   setidentObjectRefNotificacion(String identObjRef){
         identObjectRefNotificacion = identObjRef;
    }
    public void   setMensajeNotificacion(String notificacion){
        mensajeNotificacion =notificacion;
    }
    
    public String   getMensajeNotificacion(){
        return mensajeNotificacion;
    }
    public void   setTipoNotificacion(String notifTipo){
        tipoNotificacion =notifTipo;
    }
    
    public String   getTipoNotificacion(){
        return tipoNotificacion;
    }

    public String   getidentNotificador(){
        return identNotificador;
    }
    
    public void   setcontexto(Object contContexto){
        contexto =contContexto;
    }

    public Object   getcontexto(){
        return contexto;
    }
    
    @Override
    public String toString(){
        if ( contexto == null )
            return "Ident Notificador :"+identNotificador+ " Tipo Notificacion :"+tipoNotificacion+" MensajeNotificacion :+" + mensajeNotificacion+ "  Contexto: null "+"\n ";
        else 
            return "Agente Emisor :"+identNotificador+ " MensajePropuesta :+" + mensajeNotificacion+ "  Justificacion: "+contexto.toString() +"\n ";
    }
    
}
