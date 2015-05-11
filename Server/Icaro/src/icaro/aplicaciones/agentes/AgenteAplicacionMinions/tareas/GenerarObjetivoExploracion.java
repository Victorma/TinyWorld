package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.Explorar;
import icaro.aplicaciones.informacion.gestionCitas.Notificacion;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class GenerarObjetivoExploracion extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        Notificacion notif = (Notificacion) params[0];
        this.getEnvioHechos().insertarHecho(new Explorar(notif));
    }

}
