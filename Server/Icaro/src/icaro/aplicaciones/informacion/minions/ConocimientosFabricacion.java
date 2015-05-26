package icaro.aplicaciones.informacion.minions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConocimientosFabricacion {
    
    public class ListaCombinaciones {
        
        private List<String[]> combinaciones;
        
        protected ListaCombinaciones() {
            combinaciones = new ArrayList<String[]>();
        }
        
        public void addCombinacion(String[] combinacion){
            combinaciones.add(combinacion);
        }
        
        public String[] getCombinacion(List<String> notUsing){
            
            String[] combinacion = null;
            
            for(String[] posible : combinaciones){
                boolean esPosible = true;
                List<String> posibleArray = Arrays.asList(posible);
                
                for(String item : notUsing){
                    if(posibleArray.contains(item)){
                        esPosible = false;
                        break;
                    }
                }
                
                if(esPosible){
                    combinacion = posible;
                    break;
                }
            }
            
            return combinacion;
        }
    }
    
    private Map<String, ListaCombinaciones> combinaciones;
    
    public ConocimientosFabricacion() {
        combinaciones = new HashMap<String, ListaCombinaciones>();
        
        ListaCombinaciones combinacionesMadera = new ListaCombinaciones();
        
        combinacionesMadera.addCombinacion(new String[]{"Hacha", "Arbol"});
        combinaciones.put("Madera", combinacionesMadera);
        
        ListaCombinaciones combinacionesHacha = new ListaCombinaciones();
        
        combinacionesHacha.addCombinacion(new String[]{"Roca Afilada", "Palo"});
        combinaciones.put("Hacha", combinacionesHacha);
        
        ListaCombinaciones combinacionesMartillo= new ListaCombinaciones();
        
        combinacionesMartillo.addCombinacion(new String[]{"Roca", "Palo"});
        combinaciones.put("Martillo", combinacionesMartillo);
        
        ListaCombinaciones combinacionesRocaAfilada = new ListaCombinaciones();
        
        combinacionesRocaAfilada.addCombinacion(new String[]{"Roca", "Roca"});
        combinaciones.put("Roca Afilada", combinacionesRocaAfilada);
        
        ListaCombinaciones combinacionesRoca= new ListaCombinaciones();
        
        combinacionesRoca.addCombinacion(new String[]{"Martillo", "Cantera"});
        combinaciones.put("Roca", combinacionesRoca);
    }
    
    public String[] getCombinacion(String item, List<String> notUsing){
        
        ListaCombinaciones lista = combinaciones.get(item);
        
        String[] combinacion = null;
        
        if(lista != null){
            combinacion = lista.getCombinacion(notUsing);
        }
        
        return combinacion;
    }

}
