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

        public void addCombinacion(String[] combinacion) {
            combinaciones.add(combinacion);
        }

        public String[] getCombinacion(List<String> notUsing) {

            String[] combinacion = null;

            for (String[] posible : combinaciones) {
                boolean esPosible = true;
                List<String> posibleArray = Arrays.asList(posible);

                for (String item : notUsing) {
                    if (posibleArray.contains(item)) {
                        esPosible = false;
                        break;
                    }
                }

                if (esPosible) {
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

        ListaCombinaciones combinacionesHacha = new ListaCombinaciones();
        combinacionesHacha.addCombinacion(new String[]{"palo", "roca afilada"});
        combinaciones.put("hacha", combinacionesHacha);
    }

}
