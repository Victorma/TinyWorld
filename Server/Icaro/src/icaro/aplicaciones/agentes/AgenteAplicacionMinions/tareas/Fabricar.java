package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.FabricarObjeto;
import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.ObtenerObjeto;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.EstadoNodo;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.ItemData;
import icaro.aplicaciones.informacion.minions.MinionContext;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.NodoArbol;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class Fabricar extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        MinionInfo mi = (MinionInfo) params[0];
        MinionContext mc = (MinionContext) params[1];
        NodoArbol nodo = (NodoArbol) params[2];
        FabricarObjeto obj = (FabricarObjeto) nodo.getSubobjetivo();

        Map<Integer, Boolean> buscados = new HashMap<Integer, Boolean>();
        List<Object> items = new ArrayList<Object>();

        for (NodoArbol hijo : nodo.getHijos()) {
            if (hijo.getEstado() == EstadoNodo.Realizado && hijo.getSubobjetivo() instanceof ObtenerObjeto) {
                ObtenerObjeto oo = (ObtenerObjeto) hijo.getSubobjetivo();
                buscados.put(oo.objetoObtenido.get_instanceID(), true);
                items.add(oo.objetoObtenido);
            }
        }

        // Lo busco en mis manos
        for (ItemData item : mi.getUnavailableItems()) {
            // Lo tengo en mis manos
            if (buscados.containsKey(item.get_instanceID()))
                buscados.put(item.get_instanceID(), false);
        }

        // Lo busco a distancia <= 1 de mi
        for (ItemData item : mi.getAvailableItems()) {
            if (buscados.containsKey(item.get_instanceID()))
                buscados.put(item.get_instanceID(), false);
        }
        
        boolean encontrado = true;
        
        for(Boolean value : buscados.values())
            if(value) {
                encontrado = false;
                break;
            }
        
        
        

        if (encontrado) {
            this.trazas.aceptaNuevaTrazaEjecReglas(identAgente, " RESUELTO :D:D:D");
            
            GameEvent ge = new GameEvent("use");
            ge.setParameter("minion_id", mi.getInstanceId());
            ge.setParameter("items", items);
            
            
            try {
                mc.getItfAgenteGameManager().aceptaMensaje(new MensajeSimple(ge, identAgente, mc.getIdAgenteGameManager()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            
        } else {
            this.trazas.aceptaNuevaTrazaEjecReglas(identAgente, " FALLIDO :C");
            obj.setFailed();
        }
        
        obj.esperandoReporteUso = true;

        getEnvioHechos().actualizarHecho(obj);
    }

}
