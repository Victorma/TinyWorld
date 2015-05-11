package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.aplicaciones.informacion.minions.Item;

public class ObtenerObjeto extends Objetivo {

    public String objeto;

    public ObtenerObjeto(String objeto) {
        super.setgoalId("ObtenerObjeto");
        this.objeto = objeto;
    }

}
