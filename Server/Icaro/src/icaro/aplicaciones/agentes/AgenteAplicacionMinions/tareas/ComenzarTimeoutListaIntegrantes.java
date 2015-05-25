package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.minions.ArbolObjetivos.ListaIntegrantes;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ComenzarTimeoutListaIntegrantes extends TareaSincrona {

    long milisToTimeout = 1000; // 1s

    @Override
    public void ejecutar(Object... params) {

        Objetivo obj = (Objetivo) params[0];
        ListaIntegrantes lista = (ListaIntegrantes) params[1];

        // Solicito que se me reenvíe la lista en un tiempo, si al recibirla de vuelta en el informe,
        // Si al recibirla de vuelta en el informe, es la misma que tengo, se dará por finalizada la lista
        this.generarInformeTemporizado(milisToTimeout, this.identTarea, obj, this.identAgente, lista);
    }

}
