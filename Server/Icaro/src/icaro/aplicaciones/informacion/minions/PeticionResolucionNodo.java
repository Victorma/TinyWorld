package icaro.aplicaciones.informacion.minions;

import icaro.aplicaciones.informacion.minions.ArbolObjetivos.NodoArbol;

public class PeticionResolucionNodo {

    private NodoArbol nodo;
    private String emisor;

    public PeticionResolucionNodo(NodoArbol nodo, String emisor) {
        this.nodo = nodo;
        this.emisor = emisor;
    }

    public NodoArbol getNodo() {
        return nodo;
    }

    public void setNodo(NodoArbol nodo) {
        this.nodo = nodo;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

}
