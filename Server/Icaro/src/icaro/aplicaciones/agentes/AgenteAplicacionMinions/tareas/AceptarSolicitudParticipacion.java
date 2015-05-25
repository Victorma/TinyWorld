package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.CrearListaIntegrantes;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.ListaIntegrantes;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class AceptarSolicitudParticipacion extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        Focus foco = (Focus) params[0];
        GameEvent evento = (GameEvent) params[1];

        ArbolObjetivos arbol = (ArbolObjetivos) evento.getParameter("arbol");
        ListaIntegrantes lista = (ListaIntegrantes) evento.getParameter("listaInicial");

        // Creación y focalización de la creación de la lista de participantes
        Objetivo miNuevoFoco = new CrearListaIntegrantes();
        miNuevoFoco.setSolving();
        foco.setFoco(miNuevoFoco);

        // Me inserto el arbol y la lista
        this.getEnvioHechos().insertarHechoWithoutFireRules(arbol);
        this.getEnvioHechos().insertarHechoWithoutFireRules(lista);

        // Ahora genero una nueva lista para que el agente trate de hacerle merge
        ListaIntegrantes miLista = arbol.new ListaIntegrantes(identAgente);
        this.getEnvioHechos().insertarHechoWithoutFireRules(miLista);
        this.getEnvioHechos().actualizarHechoWithoutFireRules(foco);

        this.getEnvioHechos().insertarHecho(miNuevoFoco);

    }

}
