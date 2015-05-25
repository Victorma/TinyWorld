package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.RecogerObjeto;
import icaro.aplicaciones.informacion.minions.ArbolObjetivos.EstadoNodo;
import icaro.aplicaciones.informacion.minions.ItemData;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.aplicaciones.informacion.minions.PeticionResolucionNodo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ResolverPeticionRecogerObjeto extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        MinionInfo mi = (MinionInfo) params[0];
        PeticionResolucionNodo peticion = (PeticionResolucionNodo) params[1];

        RecogerObjeto recogerObjeto = (RecogerObjeto) peticion.getNodo().getSubobjetivo();

        ItemData item = null;
        Float distance = Float.MAX_VALUE;
        for (ItemData ci : mi.getAvailableItems()) {
            Float currentDist = mi.getCoords().distanceTo(ci.getCoords()) * 1.0f;
            if (ci.getName().equals(recogerObjeto.itemName) && currentDist < distance) {
                distance = currentDist;
                item = ci;
            }
        }

        if (item != null) {
            peticion.getNodo().setNewOwner(identAgente);
            recogerObjeto.setItem(item);
            peticion.getNodo().setEstado(EstadoNodo.Validado);
        } else {
            peticion.getNodo().setEstado(EstadoNodo.Irresoluble);
        }

        this.getEnvioHechos().actualizarHecho(peticion);

    }

}
