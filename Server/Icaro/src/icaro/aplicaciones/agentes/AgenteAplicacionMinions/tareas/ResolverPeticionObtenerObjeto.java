package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.FabricarObjeto;
import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.ObtenerObjeto;
import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.RecogerObjeto;
import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.Subobjetivo;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.EstadoNodo;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.NodoArbol;
import icaro.aplicaciones.informacion.minions.PeticionResolucionNodo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

import java.util.HashMap;
import java.util.Map.Entry;

public class ResolverPeticionObtenerObjeto extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        PeticionResolucionNodo peticion = (PeticionResolucionNodo) params[0];
        
        ObtenerObjeto obtenerObjeto = (ObtenerObjeto) peticion.getNodo().getSubobjetivo();

        HashMap<Class<? extends Subobjetivo>, Float> opciones = new HashMap<Class<? extends Subobjetivo>, Float>();
        opciones.put(RecogerObjeto.class, 1.0f);
        opciones.put(FabricarObjeto.class, 2.0f);

        for (NodoArbol hijo : peticion.getNodo().getHijos()) {
            if (hijo.getEstado() == EstadoNodo.Irresoluble)
                opciones.put(hijo.getSubobjetivo().getClass(), -1.0f);
        }

        Class<? extends Subobjetivo> toCreate = null;
        Float minValue = Float.MAX_VALUE;

        for (Entry<Class<? extends Subobjetivo>, Float> e : opciones.entrySet()) {
            if (e.getValue() < minValue) {
                minValue = e.getValue();
                toCreate = e.getKey();
            }
        }

        peticion.getNodo().setNewOwner(identAgente);

        peticion.getNodo().setEstado(EstadoNodo.Resuelto);

        if (toCreate == null) {
            
        } else if(toCreate == RecogerObjeto.class) {
            Subobjetivo s = new RecogerObjeto(obtenerObjeto.objeto);
            peticion.getNodo().addHijo(s);
        } else if(toCreate == FabricarObjeto.class) {
            Subobjetivo s = new FabricarObjeto(obtenerObjeto.objeto);
            peticion.getNodo().addHijo(s);
        }

        
        this.getEnvioHechos().actualizarHecho(peticion);

    }

}
