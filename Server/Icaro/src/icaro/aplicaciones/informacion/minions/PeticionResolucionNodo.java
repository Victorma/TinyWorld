package icaro.aplicaciones.informacion.minions;

import icaro.aplicaciones.informacion.minions.ArbolObjetivos.NodoArbol;

public class PeticionResolucionNodo {

    private NodoArbol nodo;
    
    
    public PeticionResolucionNodo(NodoArbol nodo) {
        this.nodo = nodo;
    }

    public NodoArbol getNodo() {
        return nodo;
    }

    public void setNodo(NodoArbol nodo) {
        this.nodo = nodo;
    }

    
    
}
