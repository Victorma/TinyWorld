/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.informacion.game_manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jzamorano
 */
public class Minion implements Serializable {
    private String nombre;
    public List<String> ObjetivosMinion = new ArrayList<String>();
    private int prioridad; //prioridad para alcanzar el objetivo
    private int vida, fuerza;
    
    public Minion(){
    }
    
    public Minion (String nombre, int prioridad, List<String> objetivos){
        this.nombre = nombre;
        this.prioridad = prioridad;
            for (int i=0; i<objetivos.size(); i++){
                this.ObjetivosMinion.add(objetivos.get(i));
            }
    }
    
    public List<String> obtenerObjetivos(){
        return this.ObjetivosMinion;
    }
    
    public synchronized String obtenerNombre(){
        return this.nombre;
    }
    
    public synchronized void establecerNombre(String nombreMinion){
        this.nombre = nombreMinion;
    }
    
    public void establecerObjetivos(List<String> objetivos){
        for (int i=0;i<objetivos.size();i++){
            this.ObjetivosMinion.add(objetivos.get(i));
        }
    }
    
    public synchronized void establecerVida(int vida){
         this.vida = vida;
    }
    
    public synchronized int obtenerVida(){
        return this.vida;
    }
    
    public synchronized void establecerFuerza(int fuerza){
        this.fuerza=fuerza;
    }
    
    public synchronized int obtenerFuerza(){
        return this.fuerza;
    }
    
    public synchronized void restaVida(){
        this.vida = this.vida -1;
    }
    
    
    
    @Override
    // Metodo para debug
    public String toString(){
        return "Minion: " + "nombre->" + this.obtenerNombre() + "; objetivos->" + this.obtenerObjetivos();
    }
}
