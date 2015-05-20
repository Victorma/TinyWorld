package icaro.aplicaciones.informacion.minions;

import icaro.aplicaciones.informacion.minions.ArbolObjetivos.ListaIntegrantes;

import java.util.ArrayList;
import java.util.List;

public class ListaEncuestas {
    
    private List<EncuestaNodo> encuestas = new ArrayList<EncuestaNodo>();
    
    public void addEncuesta(EncuestaNodo encuesta){
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
    
    public boolean contieneTodas(ListaIntegrantes integrantes){
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
            if(menorValor > encuestas.get(i).estimacion){
                menor = i;
                menorValor = encuestas.get(i).estimacion;
            }
        }
            
        return menor == -1 ? null : encuestas.get(menor);
    }

}
