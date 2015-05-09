package icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivo.comportamientoDist;


import icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivo.comportamiento.*;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoValidada;
import icaro.aplicaciones.recursos.persistenciaAccesoBD.ItfUsoPersistenciaAccesoBD;
import icaro.aplicaciones.recursos.persistenciaAccesoSimple.ItfUsoPersistenciaAccesoSimple;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AccionesSemanticasAgenteAplicacionAcceso extends AccionesSemanticasAgenteReactivo {
	
	/**
	 * @uml.property  name="visualizacion"
	 * @uml.associationEnd  
	 */
	private ItfUsoVisualizadorAcceso visualizacion;
	/**
	 * @uml.property  name="persistencia1"
	 * @uml.associationEnd  
	 */
	private ItfUsoPersistenciaAccesoSimple Persistencia1;
	/**
	 * @uml.property  name="agenteAcceso"
	 * @uml.associationEnd  
	 */
	private ItfUsoAgenteReactivo agenteAcceso;
        private String identRecursoPersistencia = "Persistencia1";
        private String identRecursoVisualizacionAcceso ="VisualizacionAcceso1";

	public void arranque(){
		
		try {
//			visualizacion = (ItfUsoVisualizadorAcceso) itfUsoRepositorio.obtenerInterfaz
//			(NombresPredefinidos.ITF_USO+"VisualizacionAcceso1");

                        visualizacion = (ItfUsoVisualizadorAcceso) obtenerIntfRecurso(identRecursoVisualizacionAcceso, NombresPredefinidos.ITF_USO);
			if (visualizacion != null){
                        visualizacion.mostrarVisualizadorAcceso(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
                        }else
                            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"No se puede acceder al visualizador porque no se encuentra su interfaz de uso--"+identRecursoVisualizacionAcceso,InfoTraza.NivelTraza.error));
                        }

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Ha habido un problema al abrir el visualizador de Acceso en accion semantica 'arranque()'", 
                                                                InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public void valida(InfoAccesoSinValidar infoUsuario) {
		boolean ok = false;

		
//		InfoAccesoSinValidar datos = new InfoAccesoSinValidar(username,password);
		try {
//			Persistencia1 = (ItfUsoPersistenciaAccesoSimple) itfUsoRepositorio.obtenerInterfaz
//			(NombresPredefinidos.ITF_USO+"Persistencia1");
                        Persistencia1 = (ItfUsoPersistenciaAccesoSimple)obtenerIntfRecurso(identRecursoPersistencia,NombresPredefinidos.ITF_USO);
			if (Persistencia1 != null){
                            ok = Persistencia1.compruebaUsuario(infoUsuario.tomaUsuario(),infoUsuario.tomaPassword());
                            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,   "Comprobando usuario...",  InfoTraza.NivelTraza.debug));
			}else
                            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,   "No se ha podido obtener la interfaz de uso del recurso"+identRecursoPersistencia,  InfoTraza.NivelTraza.error));
		}

		catch (Exception ex){		
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Ha habido un problema en la Persistencia1 al comprobar el usuario y el password", 
						InfoTraza.NivelTraza.error));
		}
		try {
//			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
//			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			Object[] datosEnvio = new Object[]{infoUsuario.tomaUsuario(), infoUsuario.tomaPassword()};
			if(ok){
                             this.informaraMiAutomata("usuarioValido", datosEnvio);
//				this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("usuarioValido",datosEnvio,this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Acceso"));
			}
			else
                            this.informaraMiAutomata("usuarioNoValido", datosEnvio);
//				itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("usuarioNoValido", datosEnvio,this.nombreAgente,this.nombreAgente));
			
		}
		catch (Exception e) {
			
                    trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente",
                                            InfoTraza.NivelTraza.error));
		}
	}

        public void validaConRecRemoto(InfoAccesoSinValidar infoUsuario) {
		boolean ok = false;

//		InfoAccesoSinValidar datos = new InfoAccesoSinValidar(username,password);
		try {
                   ItfUsoConfiguracion  config = (ItfUsoConfiguracion) itfUsoRepositorio.obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.CONFIGURACION);
                         String nombreRec = "Persistencia1";
			 String identHostRecurso= config.getDescInstanciaRecursoAplicacion(nombreRec).getNodo().getNombreUso();
                           ItfUsoPersistenciaAccesoSimple itfUsoRec = (ItfUsoPersistenciaAccesoSimple) AdaptadorRegRMI.getItfUsoRecursoRemoto(identHostRecurso, nombreRec);
                            if (itfUsoRec == null)// la intf  es null El recruso no ha sido registrado
                            {
                             logger.debug("AgenteAcceso: No se puede dar la orden de arranque al recurso "+ nombreRec + ". Porque su interfaz es null");
                             trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
					" No se puede dar la orden de arranque al recurso "+ nombreRec + ". Porque su interfaz es null",
					InfoTraza.NivelTraza.debug));
//                                        errorEnArranque = true;
                            }
                            else {
                             //
			ok = itfUsoRec.compruebaUsuario(infoUsuario.tomaUsuario(),infoUsuario.tomaPassword());
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
															  "Comprobando usuario...",
															  InfoTraza.NivelTraza.debug));
			}catch(Exception e){e.printStackTrace();}
		}

                } catch (Exception ex) {
			try {

				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
							"Ha habido un problema en la Persistencia1 al comprobar el usuario y el password",
															  InfoTraza.NivelTraza.error));
				}catch(Exception e){e.printStackTrace();}
		}
		try {
			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			Object[] datosEnvio = new Object[]{infoUsuario.tomaUsuario(), infoUsuario.tomaPassword()};
			if(ok){
				agenteAcceso.aceptaEvento(new EventoRecAgte("usuarioValido",datosEnvio,this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Acceso"));
			}
			else{
				agenteAcceso.aceptaEvento(new EventoRecAgte("usuarioNoValido", datosEnvio,this.nombreAgente,this.nombreAgente));
			}
		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente",
															  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
		}
	}
	
	public void mostrarUsuarioAccede(String username, String password){
		InfoAccesoValidada dav = new InfoAccesoValidada(username ,password);
		
		try{
//			visualizacion = (ItfUsoVisualizadorAcceso) itfUsoRepositorio.obtenerInterfaz
//			(NombresPredefinidos.ITF_USO+"VisualizacionAcceso1");
			visualizacion.mostrarMensajeInformacion("AccesoCorrecto", "El usuario "+dav.tomaUsuario()+" ha accedido al sistema. \n A partir de aqui deberia continuar la aplicacion.");
			visualizacion.cerrarVisualizadorAcceso();

		}
		catch (Exception ex) {
		trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Ha habido un problema al abrir el visualizador de Acceso", 
						InfoTraza.NivelTraza.error));
				
		}
		pedirTerminacionGestorAgentes();
	}
	
	public void mostrarUsuarioNoAccede(String username, String password){
		InfoAccesoSinValidar dav = new InfoAccesoSinValidar(username ,password);
		
		try{
//			visualizacion = (ItfUsoVisualizadorAcceso) itfUsoRepositorio.obtenerInterfaz
//			(NombresPredefinidos.ITF_USO+"VisualizacionAcceso1");
			visualizacion.mostrarMensajeError("Acceso Incorrecto", "El usuario "+dav.tomaUsuario()+" no ha accedido al sistema.");
		}

		catch (Exception ex) {
		trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,  "Ha habido un problema al abrir el visualizador de Acceso", 
							InfoTraza.NivelTraza.error));
		}
		pedirTerminacionGestorAgentes();
	}
	
	public void terminacion() {
		
	trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Terminando agente: "+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Acceso1", 
						 InfoTraza.NivelTraza.debug));
//		try {
//			this.hebra.finalizar(); // CUIDADO, SI FALLASE LA CREACION DE LOS
//									// RECURSOS ESTA HEBRA
//		} // NO ESTA INICIALIZADA
//		catch (Exception e) {
//			e.printStackTrace();
//			logger.error("GestorOrganizacion: La hebra no ha podido ser finalizada porque no hab�a sido creada.");
//			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
//					"La hebra no ha podido ser finalizada porque no hab�a sido creada.",
//					InfoTraza.NivelTraza.error));
//		}
		try {
                    visualizacion.cerrarVisualizadorAcceso();
//                    ((InterfazGestion) this.itfUsoRepositorio
//					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
//							+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES))
//					.termina();

		

                    // Buscar al Gestor de Agentes  y si falla el GO y decirle que el agente de identificador agxx ha terminado
			this.itfUsoGestorAReportar.aceptaEvento(new EventoRecAgte(
					"peticion_terminar_todo",
					NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));

                }catch(Exception e2){e2.printStackTrace();}
		logger.debug("Terminando agente: "+this.nombreAgente);
	}
	
	public void clasificaError(){
	/*
	 *A partir de esta funci�n se debe decidir si el sistema se puede recuperar del error o no.
	 *En este caso la pol�tica es que todos los errores son cr�ticos.  
	 */
		try {
			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteAcceso.aceptaEvento(new EventoRecAgte("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
		}
	}
	public void pedirTerminacionGestorAgentes(){
		try {
			/*ItfUsoAgenteReactivo itfUsoGestorOrgan = (ItfUsoAgenteReactivo)itfUsoRepositorio.obtenerInterfaz
			("Itf_Uso_Gestor_Organizacion");
			itfUsoGestorOrgan.aceptaEvento(new EventoRecAgte("terminar_gestores_y_gestor_organizacion","AgenteAccesoUso","AgenteAccesoUso"));*/
//			this.itfUsoGestorAReportar.aceptaEvento(new EventoRecAgte("peticion_terminar_todo",this.nombreAgente,NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
                         visualizacion.cerrarVisualizadorAcceso();
//
                         String identGestorAReportar =NombresPredefinidos.NOMBRE_GESTOR_AGENTES;
                         itfUsoGestorAReportar = (ItfUsoAgenteReactivo) obtenerIntfAgente(identGestorAReportar, NombresPredefinidos.ITF_USO);
                         if (itfUsoGestorAReportar == null){
                             identGestorAReportar = NombresPredefinidos.NOMBRE_GESTOR_NODO;
                             itfUsoGestorAReportar = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO+identGestorAReportar);

                         }
                         this.itfUsoGestorAReportar.aceptaMensaje(new MensajeSimple (new InfoContEvtMsgAgteReactivo("peticion_terminar_todo"), this.nombreAgente,identGestorAReportar));
		} catch (Exception e) {
			logger.error("Error al mandar el evento de peticion_terminar_todo",e);
                        trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Error al mandar el evento de terminar_todo",
									InfoTraza.NivelTraza.error));
			
			try{
				this.informaraMiAutomata("error");
//				itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("error",this.nombreAgente,this.nombreAgente));
			}
			catch(Exception exc){
				try {
					ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
							NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
							trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
                                                            "Fallo al enviar un evento error.", InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
				logger.error("Fallo al enviar un evento error.",exc);
			}
		}
	}

public Object obtenerIntfAgente (String identAgente, String tipoInterfaz){
        Object resultadoBusqueda= null;
         try {
            resultadoBusqueda = itfUsoRepositorio.obtenerInterfaz(tipoInterfaz + identAgente);
            if (resultadoBusqueda == null)
                resultadoBusqueda = AdaptadorRegRMI.getItfAgenteRemoto(identAgente, tipoInterfaz);
            else if (resultadoBusqueda == null) { // No se ha conseguido encontrar la interfaz del agente
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "No se ha podido encontrar la interfaz del agente : "+identAgente,
									InfoTraza.NivelTraza.error));
            }
        } catch (Exception ex) {
            Logger.getLogger(AccionesSemanticasAgenteAplicacionAcceso.class.getName()).log(Level.SEVERE, "Se prujo un error al buscar   la interfaz del agente : "+identAgente, ex);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Se produjo un error al buscar   la interfaz del agente : "+identAgente,
									InfoTraza.NivelTraza.error));
        }
       return resultadoBusqueda;
    }
public Object obtenerIntfRecurso (String identRecurso, String tipoInterfaz){
        Object resultadoBusqueda= null;
         try {
            resultadoBusqueda = itfUsoRepositorio.obtenerInterfaz(tipoInterfaz + identRecurso);
            if (resultadoBusqueda == null)
                resultadoBusqueda = AdaptadorRegRMI.getItfRecursoRemoto(identRecurso, tipoInterfaz);
            else if (resultadoBusqueda == null) { // No se ha conseguido encontrar la interfaz del agente
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "No se ha podido encontrar la interfaz del agente : "+identRecurso,
									InfoTraza.NivelTraza.error));
            }
        } catch (Exception ex) {
            Logger.getLogger(AccionesSemanticasAgenteAplicacionAcceso.class.getName()).log(Level.SEVERE, "Se prujo un error al buscar   la interfaz del agente : "+identRecurso, ex);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Se produjo un error al buscar   la interfaz del agente : "+identRecurso,
									InfoTraza.NivelTraza.error));
        }
       return resultadoBusqueda;
    }
}



