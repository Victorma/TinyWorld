/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.informacion.game_manager;

import java.util.List;

//Clase para almacenar y obtener la informacion de los minions así como la vida que tienen, fuerza, conocimientos... 
/**
 *
 * @author jzamorano
 */
public class InformacionMinions {
    private String nombre;
    private int vida, fuerza;
    private List<String> conocimientos; //Lista de conocimientos 
    
    
    public String ObtenerNombreMinion (){
        return this.nombre;
    }
    
    public void EstablecerVidaDisponible (int vida){
        this.vida = vida;
    }
    
    public int ObtenerVida (){
        return this.vida;
    }
    
    public void EstablecerFuerza (int fuerza){
        this.fuerza=fuerza;
    }
    
    public int ObtenerFuerza (){
        return this.fuerza;
    }
    
    public void EstablecerConocimientos (List<String> rc){
        for (int i=0; i<rc.size(); i++){
            this.conocimientos.add(i, rc.get(i));
        }
    }
    
    public List<String> ObtenerConocimientos (){
        return this.conocimientos;
    }
    
    @Override
    
    public String toString (){
        return "Minion : Nombre ->" + this.nombre +
                " Fuerza -> " + this.ObtenerFuerza() +
                " Vida -> " + this.ObtenerVida() +
                " Conocimientos -> " + this.ObtenerConocimientos();
    }
    
    
    
    
}
