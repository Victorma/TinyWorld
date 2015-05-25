package icaro.aplicaciones.informacion.minions;

import icaro.aplicaciones.informacion.minions.ArbolObjetivos.NodoArbol;

public class EncuestaNodo {

    public float estimacion;
    public NodoArbol nodo;
    public String encuestado;
    public String emisor;
    public boolean resuelta;

    public EncuestaNodo(String encuestado, NodoArbol nodo, String emisor) {
        this.encuestado = encuestado;
        this.nodo = nodo;
        this.emisor = emisor;
        this.resuelta = false;
    }

    public void setResuelta() {
        this.resuelta = true;
    }

    @Override
    public String toString() {
        return "Encuesta(" + encuestado + ", " + estimacion + "," + resuelta + ")";
    }

}
