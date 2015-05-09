/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.comunicacion;

import java.io.Serializable;

/**
 *
 * @author FGarijo
 */
public class InfoContParaAgteReactivo implements Serializable{
     private  Object msg = null;
//    Object valorParametroAccion;
  private  Object[] valoresParametrosAccion = {};

 public InfoContParaAgteReactivo(Object msg){
        this.msg= msg;
        this.valoresParametrosAccion = null;
    }

public  InfoContParaAgteReactivo(String msg,Object valorParametroAccion){
        this.msg= msg;
//        this.valorParametroAccion = valorParametroAccion;
        if (valorParametroAccion == null)
            this.valoresParametrosAccion = null;
            else {
                valoresParametrosAccion= new Object[1];
                valoresParametrosAccion[0]=valorParametroAccion;
            }
    }

public  InfoContParaAgteReactivo(String input,Object[] valoresParametrosAccion){
        this.msg= input;
        this.valoresParametrosAccion = valoresParametrosAccion;
    }
public  InfoContParaAgteReactivo(Object input,Object... valoresParamAccion){
        this.msg= input;
        int i=0;
        valoresParametrosAccion= new Object[(valoresParamAccion).length];
        for (Object param: valoresParamAccion){           
                valoresParametrosAccion[i]=param;
                i++;
        }
    }

    public InfoContParaAgteReactivo() {

    }

    public Object getInput() {
        return this.msg;
    }
    public void  setInput(Object input) {
        this.msg = input;
    }
    public Object getmsg() {
        return this.msg;
    }
    public void  setmsg(Object input) {
        this.msg = input;
    }
    public Object[] getvaloresParametrosAccion() {

        return this.valoresParametrosAccion;
    }
    public void  setvaloresParametrosAccion(Object[] listaValoresParametrosAccion) {
        this.valoresParametrosAccion= listaValoresParametrosAccion;
    }
    
    @Override
     public String toString(){
        if ( valoresParametrosAccion == null )
            return " Mensaje: "+msg+", Parametros: null ";
        else 
            return "                 Mensaje: "+msg+"\n " +toStringParametros();
    }
     public String toStringParametros(){
//        Object parametro = null;
        String msgParametros = null;
        for ( int i = 0; i < valoresParametrosAccion.length ; i++ ) {
            msgParametros = "                 Parametro " + i+1 + " :  ";
//            parametro =  valoresParametrosAccion[i]; 
//                msgParametros =  msgParametros + valoresParametrosAccion[i] + "\n" ;
            msgParametros =  msgParametros + valoresParametrosAccion[i]  ;
                    
                }
        
            return msgParametros;
    }
}
