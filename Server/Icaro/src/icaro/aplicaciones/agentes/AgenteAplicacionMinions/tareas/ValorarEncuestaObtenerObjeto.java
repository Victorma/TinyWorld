package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.FabricarObjeto;
import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.RecogerObjeto;
import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.Subobjetivo;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.EstadoNodo;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.NodoArbol;
import icaro.aplicaciones.informacion.minions.EncuestaNodo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

import java.util.HashMap;
import java.util.Map.Entry;

public class ValorarEncuestaObtenerObjeto extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        EncuestaNodo encuesta = (EncuestaNodo) params[0];
        
        HashMap<Class<? extends Subobjetivo>, Float> opciones = new HashMap<Class<? extends Subobjetivo>, Float>();
        opciones.put(RecogerObjeto.class, 1.0f);
        opciones.put(FabricarObjeto.class, 2.0f);
        
        for(NodoArbol nodo : encuesta.nodo.getHijos()){
            if(nodo.getEstado() == EstadoNodo.Irresoluble)
                opciones.put(nodo.getSubobjetivo().getClass(), -1.0f);
        }
        
        Class<? extends Subobjetivo> toCreate = null;
        Float minValue = Float.MAX_VALUE;
        
        for(Entry<Class<? extends Subobjetivo>, Float> e : opciones.entrySet()){
            if(e.getValue() < minValue && e.getValue() >= 0){
                minValue = e.getValue();
                toCreate = e.getKey();
            }
        }
        
        if(toCreate == null){
            encuesta.estimacion = -1;
        }else{
            encuesta.estimacion = minValue;
        }
        
        encuesta.setResuelta();
        this.getEnvioHechos().actualizarHecho(encuesta);
        
    }

}
