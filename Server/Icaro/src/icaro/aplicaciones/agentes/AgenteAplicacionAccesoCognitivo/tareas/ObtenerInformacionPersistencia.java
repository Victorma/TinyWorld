/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.AgenteAplicacionAccesoCognitivo.tareas;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.aplicaciones.recursos.persistenciaAccesoBD.ItfUsoPersistenciaAccesoBD;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;


/**
 *
 * @author jzamorano
 */
public class ObtenerInformacionPersistencia extends Tarea{
        private String identAgenteOrdenante;
        private Objetivo contextoEjecucionTarea = null;
        @Override
	public void ejecutar(Object... params) {
            String identDeEstaTarea=this.getIdentTarea();
            try
            {
                //Sacamos la información del usuario 
                
            }
            catch (Exception e){
                 
            }
                    
        }
    
}
