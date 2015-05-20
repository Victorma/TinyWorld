package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.aplicaciones.informacion.minions.ArbolObjetivos.ListaIntegrantes;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class CrearListaIntegrantes extends Objetivo {

    public ListaIntegrantes lista;
    
    public CrearListaIntegrantes() {
        super.setgoalId("CrearListaIntegrantes");
    }
}
