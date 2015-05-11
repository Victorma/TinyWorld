package icaro.aplicaciones.informacion.game_manager;

import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.recursos.comunicacionChat.ClientConfiguration;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.FactoriaAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Partida {
	
	public enum EstadoPartida{SIN_COMPLETAR,COMPLETADA}
	
	//Clase privada
	private class ObjPartida{
		public GameEvent evento;
		public boolean completado;
		
		ObjPartida(GameEvent evento){
			this.evento = evento;
			this.completado = false;
		}
		
		ObjPartida(GameEvent evento, boolean estado){
			this.evento = evento;
			this.completado = estado;
		}
	}
	
	//Parametros
	public List<ObjPartida> objetivos;
	public List<String> minions;
	public EstadoPartida estado = EstadoPartida.SIN_COMPLETAR;
	
	
	
	//Metodos
	public Partida(String fatherid, GameEvent event){
		GameEvent[] objtmp = (GameEvent[]) event.getParameter("objetivos");
		GameEvent[] mintmp = (GameEvent[]) event.getParameter("minions");
		objetivos = new ArrayList<ObjPartida>();
		
		for(GameEvent ge : objtmp)
			objetivos.add(new ObjPartida(ge));
		
		this.estado = objetivosCompletados()?EstadoPartida.COMPLETADA:EstadoPartida.SIN_COMPLETAR;
		
		try{
			DescComportamientoAgente dca = ClaseGeneradoraConfiguracion.instance().getDescComportamientoAgente("AgenteAplicacionMinion");
			minions = new ArrayList<String>();
			for(GameEvent ge : mintmp){
				DescInstanciaAgente descInstanciaAgente = new DescInstanciaAgente();
				descInstanciaAgente.setId("AgentMinion_" + ge.getParameter("nombre") + "(" + fatherid + ")");
				descInstanciaAgente.setDescComportamiento(dca);
				FactoriaAgenteCognitivo.instance().crearAgenteCognitivo(descInstanciaAgente);
				minions.add(descInstanciaAgente.getId());
			}
		}catch(Exception ex){}
	}
	
	public void addObjetivo(GameEvent objetivo){
		objetivos.add(new ObjPartida(objetivo));
	}
	
	public void addMinion(String minion){
		minions.add(minion);
	}
	
	public void validarObjetivo(GameEvent objetivo){
		for(ObjPartida o : objetivos){
			if(o.evento.name.equalsIgnoreCase(objetivo.name)){
				
				Collection<String> parameters = o.evento.getParameters();
				
				boolean c = true;
				for(String p : parameters){
					if(objetivo.getParameter(p)!=null){
						if(!((String)objetivo.getParameter(p)).equalsIgnoreCase((String)o.evento.getParameter(p))){
							c = false;
							break;
						}
					}else{
						c = false;
						break;
					}
				}
				
				o.completado = c;
				break;
			}
		}
		
		this.estado = objetivosCompletados()?EstadoPartida.COMPLETADA:EstadoPartida.SIN_COMPLETAR;
	}
	
	public boolean objetivosCompletados(){
		boolean completados = true;
		for(ObjPartida o : objetivos){
			if(o.completado == false){
				completados = false;
				break;
			}
		}
		return completados;
	}
	
	public void terminaPartida(){
		
	}
}
