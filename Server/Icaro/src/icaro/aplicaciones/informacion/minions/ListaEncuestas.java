package icaro.aplicaciones.informacion.minions;

import icaro.aplicaciones.informacion.minions.ArbolObjetivos.ListaIntegrantes;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.NodoArbol;

import java.util.ArrayList;
import java.util.List;

public class ListaEncuestas {
    
    private List<EncuestaNodo> encuestas = new ArrayList<EncuestaNodo>();
    
    private NodoArbol nodo;
    private ListaIntegrantes integrantes;
    private boolean esperandoResolucion;
    
    public ListaEncuestas(NodoArbol nodo, ListaIntegrantes integrantes){
    	this.nodo = nodo;
    	this.integrantes = integrantes;
    }
    
    
    
    public boolean isEsperandoResolucion() {
        return esperandoResolucion;
    }



    public void setEsperandoResolucion(boolean esperandoResolucion) {
        this.esperandoResolucion = esperandoResolucion;
    }



    public NodoArbol getNodo() {
        return nodo;
    }



    public void setNodo(NodoArbol nodo) {
        this.nodo = nodo;
    }



    public void addEncuesta(EncuestaNodo encuesta){
    	if(encuesta.nodo == this.nodo)
    		encuestas.add(encuesta);
    }
    
    private static int hayEncuestaDe(String nombre, List<EncuestaNodo> listaParcial){
        int i = 0;
        for (EncuestaNodo encuestaNodo : listaParcial) {
            if(encuestaNodo.encuestado == nombre)
                return i;
            i++;
        }
        
        return -1;
    }
    
    public boolean contieneTodas(){
        List<EncuestaNodo> clon = new ArrayList<EncuestaNodo>(encuestas);
        boolean contieneTodas = true;
        
        int pos;
        for (String integrante : integrantes.getLista()) {
            pos = hayEncuestaDe(integrante, clon);
            if(pos != -1)
                // Al ir removiendo cada vez optimizamos las búsquedas
                clon.remove(pos);
            else{
                contieneTodas = false;
                break;
            }
        }
        
        return contieneTodas;
    }
    
    public EncuestaNodo getMejorEncuesta(){
        int menor = -1;
        
        float menorValor = Float.MAX_VALUE;
        for(int i = 0; i < encuestas.size(); i++){
            if(menorValor > encuestas.get(i).estimacion && encuestas.get(i).estimacion >= 0){
                menor = i;
                menorValor = encuestas.get(i).estimacion;
            }
        }
            
        return menor == -1 ? null : encuestas.get(menor);
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" +this.hashCode() + "):" + this.encuestas.toString();
    }

}
