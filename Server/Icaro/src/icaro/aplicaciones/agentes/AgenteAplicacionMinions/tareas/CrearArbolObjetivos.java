package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.Subobjetivo;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.ListaIntegrantes;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.MinionContext;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

import java.rmi.RemoteException;

public class CrearArbolObjetivos extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        MinionContext mc = (MinionContext) params[0];
        Subobjetivo obj = (Subobjetivo) params[1];

        ArbolObjetivos arbol = new ArbolObjetivos(obj);

        // Creo una nueva lista indicando mi participacion
        ListaIntegrantes lista = arbol.new ListaIntegrantes(identAgente);

        // Genero el evento de solicitud
        GameEvent solicitudParticipacionAgentes = new GameEvent();
        solicitudParticipacionAgentes.setName("solicitud participacion");
        solicitudParticipacionAgentes.setParameter("arbol", arbol);
        solicitudParticipacionAgentes.setParameter("listaInicial", lista);

        // Activo el envío del evento al resto de minions
        solicitudParticipacionAgentes.setParameter("toMinions", true);

        try {
            mc.getItfAgenteGameManager().aceptaMensaje(new MensajeSimple(solicitudParticipacionAgentes, identAgente, mc.getIdAgenteGameManager()));

            // Evito la regeneración del objetivo
            this.getEnvioHechos().eliminarHecho(obj);

        } catch (RemoteException e) {
            e.printStackTrace(System.err);
        }

    }

}
