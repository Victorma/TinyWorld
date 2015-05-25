package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.minions.Coord;
import icaro.aplicaciones.informacion.minions.PeticionCoordenadas;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.informes.InformeDeTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

public class DevolverPeticionCoordenadas extends TareaSincrona{

    @Override
    public void ejecutar(Object... params) {
        
        Coord c = (Coord) params[0];
        PeticionCoordenadas peticion = (PeticionCoordenadas) params[1];
        
        InformeDeTarea informe = new InformeDeTarea(this.identTarea, peticion.getEmisor(), c);
        MensajeSimple ms = new MensajeSimple(informe, this.getIdentAgente(), peticion.getEmisor());
        
        try {
            ((ItfUsoAgenteCognitivo ) repoInterfaces.obtenerInterfazUso(peticion.getEmisor())).aceptaMensaje(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.getEnvioHechos().eliminarHecho(peticion);
    }

}
