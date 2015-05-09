package icaro.aplicaciones.recursos.interfazChatUsuario.imp;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoSinValidar;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 
 *@author    Francisco J Garijo
 *@created    Febrero 2009
 */

public class NotificacionesEventosVisAccesoAlta {
	
    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ItfUsoRecursoTrazas trazas;
    protected ClaseGeneradoraInterfazChatUsuario generadoraVisualizador;
    protected InterfazUsoAgente itfUsoAgenteaReportar;
    protected String identificadorAgenteaReportar;
    protected String identificadordeEsteRecurso;
	
public NotificacionesEventosVisAccesoAlta (String identRecurso, String identAgteAreportar)throws Exception {
// Obtenemos las informaciones que necesitamos de la clase generadora del recurso
//        generadoraVisualizador = generadoraVis;
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        identificadordeEsteRecurso = identRecurso;
        identificadorAgenteaReportar = identAgteAreportar;
        if (identificadorAgenteaReportar != null){
            itfUsoAgenteaReportar = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.ITF_USO+identificadorAgenteaReportar);
            }
            else{itfUsoAgenteaReportar = null;}
  // se busca la interfaz de uso del agente a reportar en el repositorio de interfaces
   
   }
//public NotificacionesEventosVisAccesoAlta (ClaseGeneradoraVisualizacionAccesoAlta generadoraVis)throws Exception {
//// Obtenemos las informaciones que necesitamos de la clase generadora del recurso
//        generadoraVisualizador = generadoraVis;
//        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
//        identificadordeEsteRecurso = "Recurso:"+generadoraVis.getNombredeEsteRecurso();
//        identificadorAgenteaReportar = generadoraVis.getNombreAgenteAReportar();
//        if (identificadorAgenteaReportar != null){
//            itfUsoAgenteaReportar = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.ITF_USO+identificadorAgenteaReportar);
//            }
//            else{itfUsoAgenteaReportar = null;}
//  // se busca la interfaz de uso del agente a reportar en el repositorio de interfaces
//
//   }
	
//	public ClaseGeneradoraVisualizacionAccesoAlta getVisualizador(){
		
//		throw new Exception("hola");
//	}
 public ClaseGeneradoraInterfazChatUsuario getVisualizador() {
        return generadoraVisualizador;

    }

 public void peticionCierreSistema() {
     EventoRecAgte eventoaEnviar = new EventoRecAgte("peticion_terminacion_usuario",identificadordeEsteRecurso,identificadorAgenteaReportar);
     enviarEvento(eventoaEnviar);
 }
 public void notificacionCierreSistema() throws Exception{
	//cierre de ventanas que genera cierre del sistema
	
		if (identificadorAgenteaReportar == null){
                System.out.println("El identificador del agente al que  hay que enviar los eventos es null");
                NombresPredefinidos.RECURSO_TRAZAS_OBJ.aceptaNuevaTraza(new InfoTraza(this.identificadordeEsteRecurso,
					"No se puede enviar el evento pq el identificador del  agente a reportar   es null ",
					InfoTraza.NivelTraza.error));
                }else {
                if (itfUsoAgenteaReportar == null){
			 itfUsoAgenteaReportar = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.ITF_USO+identificadorAgenteaReportar);
                         if (itfUsoAgenteaReportar == null)

            NombresPredefinidos.RECURSO_TRAZAS_OBJ.aceptaNuevaTraza(new InfoTraza(this.identificadordeEsteRecurso,
															  "No se puede enviar el evento pq la interfaz de uso del agente "+identificadorAgenteaReportar + " es null ",
															  InfoTraza.NivelTraza.error));
                                    }
                else {
        try {
			//me aseguro de crear las interfaces si han sido registradas ya
	//		if (itfUsoRepositorioInterfaces == null){
	//			itfUsoRepositorioInterfaces = visualizador.getItfUsoRepositorioInterfaces();
	//		}
	//		ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
	//		.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta1");

            itfUsoAgenteaReportar.aceptaEvento(new EventoRecAgte("peticion_terminacion_usuario",identificadordeEsteRecurso,identificadorAgenteaReportar));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento termina al agente");
			e.printStackTrace();
                    }
                }
            }
        }

 public void PeticionDarAlta (String username, String password) throws Exception {
     InfoAccesoSinValidar infoAutenticacion = new  InfoAccesoSinValidar(username,password);
     EventoRecAgte eventoaEnviar = new EventoRecAgte("peticion_alta",infoAutenticacion, identificadordeEsteRecurso,identificadorAgenteaReportar);
     enviarEvento(eventoaEnviar);
 }
	public void PeticionDarAlta2 (String username, String password) throws Exception {
		// Lo dejamos de esta forma para que se vea otra manera diferente de hacerlo aunque la forma
        // correcta seria crear un objeto con la info 
        // InfoAccesoSinValidar InfoAutenticacion = new  InfoAccesoSinValidar(username,password);

            Object[] datosEnvio = new Object[]{username, password};
    //    InfoAccesoValidada datosEnvio = new InfoAccesoValidada (username, password);
    //    if (itfUsoAgenteaReportar == null){
         // Logger.getLogger(NotificacionesAgenteAccesoAlta.class.getName()).log(Level.SEVERE, null, e);
    //      Logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor);
            itfUsoAgenteaReportar = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.ITF_USO+identificadorAgenteaReportar);
            if (itfUsoAgenteaReportar == null){
                NombresPredefinidos.RECURSO_TRAZAS_OBJ.aceptaNuevaTraza(new InfoTraza(this.identificadordeEsteRecurso,
                                                         "No se puede enviar el evento pq la interfaz de uso del agente "+identificadorAgenteaReportar + " es null ",
															  InfoTraza.NivelTraza.error));
            }
            else {
                try {

                itfUsoAgenteaReportar.aceptaEvento(new EventoRecAgte("peticion_alta",datosEnvio,identificadordeEsteRecurso,identificadorAgenteaReportar));
			
                } catch (Exception e) {
                System.out.println("Ha habido un error al enviar el usuario y el password al agente de acceso ");
                e.printStackTrace();
                generadoraVisualizador.informeError("error al enviar peticion de alta  al agente de acceso ");
                    }
                }
    }
   public void peticionAutentificacion(String username, String password) {

      InfoAccesoSinValidar InfoAutenticacion = new  InfoAccesoSinValidar(username,password);
      enviarEvento(new EventoRecAgte("autenticacion", InfoAutenticacion, identificadordeEsteRecurso, identificadorAgenteaReportar));

    }
  
/*
    public void peticionAutentificacion(String username, String password) throws Exception {
    
     Object[] datosEnvio = new Object[]{username, password};
    if (itfUsoAgenteaReportar == null)
              getInformacionAgenteaReportar();
     try {
			itfUsoAgenteaReportar.aceptaEvento(new EventoRecAgte("peticion_alta",datosEnvio,identificadordeEsteRecurso,identificadorAgenteaReportar));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el usuario y el password al agente de acceso ");
			e.printStackTrace();
              generadoraVisualizador.informeError("error al enviar el usuario y el password al agente de acceso ");
		}
     }
 */

 public void enviarEvento(EventoRecAgte eventoaEnviar){
     // se envia el evento al agente receptor predefinido   Para cambiarla es necesario hacerlo
     // con la operacion setItfAgenteReceptor
     if  (eventoaEnviar ==null)
         trazas.aceptaNuevaTraza(new InfoTraza(this.identificadordeEsteRecurso,
					"El evento a enviar en null "+ eventoaEnviar+"al agente "+identificadorAgenteaReportar,
					InfoTraza.NivelTraza.error));
     try {
            itfUsoAgenteaReportar.aceptaEvento(eventoaEnviar);
		}
		catch (Exception e) {
//			Logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor);
			Logger.getLogger(NotificacionesEventosVisAccesoAlta.class.getName()).log(Level.SEVERE, null, e);
            NombresPredefinidos.RECURSO_TRAZAS_OBJ.aceptaNuevaTraza(new InfoTraza(this.identificadordeEsteRecurso,
					"Ha habido un problema enviar el evento con informacion "+ eventoaEnviar+"al agente "+identificadorAgenteaReportar,
					InfoTraza.NivelTraza.error));
			}
 }
 public void enviarEventoaOtroAgente(EventoRecAgte eventoaEnviar,String IdentAgenteReceptor){

   // Este método crea un evento con la información de entrada y se le envía al agente REACTIVO que se indique por medio de
  // la  interfaz de uso
   //     EventoRecAgte eventoaEnviar = null;

   // Se verifica que la interfaz del aegente no es vacia
    //   if (itfUsoAgenteReceptor == null){
        try {
			itfUsoAgenteaReportar = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz
		(NombresPredefinidos.ITF_USO+IdentAgenteReceptor);
		}
		catch (Exception e) {
			Logger.getLogger(NotificacionesEventosVisAccesoAlta.class.getName()).log(Level.SEVERE, null, e);
    //      Logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor);
			NombresPredefinidos.RECURSO_TRAZAS_OBJ.aceptaNuevaTraza(new InfoTraza(this.identificadordeEsteRecurso,
															  "Ha habido un problema enviar el evento con informacion"+eventoaEnviar+"al   agente "+IdentAgenteReceptor,
															  InfoTraza.NivelTraza.error));
			}

      try {
			itfUsoAgenteaReportar.aceptaEvento(eventoaEnviar);
		}
		catch (Exception e) {
//			Logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor);
			Logger.getLogger(NotificacionesEventosVisAccesoAlta.class.getName()).log(Level.SEVERE, null, e);
            NombresPredefinidos.RECURSO_TRAZAS_OBJ.aceptaNuevaTraza(new InfoTraza(this.identificadordeEsteRecurso,
															  "Ha habido un problema enviar el evento con informacion "+ eventoaEnviar+"al   agente "+IdentAgenteReceptor,
															  InfoTraza.NivelTraza.error));
			}
        }
 public void setItfUsoAgenteAReportar (InterfazUsoAgente itfAgenteAreportar) {

     if (itfAgenteAreportar != null)
         itfUsoAgenteaReportar = itfAgenteAreportar;

 }
 public void setIdentificadorAgenteAReportar(String identAgenteAReportar){
        try {
            identificadorAgenteaReportar = identAgenteAReportar;
            getItfUsoAgenteaReportar();
        } catch (Exception ex) {
            Logger.getLogger(NotificacionesEventosVisAccesoAlta.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

  private void getItfUsoAgenteaReportar()throws Exception {
        if (identificadorAgenteaReportar != null ){
        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            itfUsoAgenteaReportar = (InterfazUsoAgente) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO+ identificadorAgenteaReportar);
        System.out.println("verificando la interfaz de uso");
        }
            catch (Exception e) {
			System.out.println("No se ha podido obtener la interfaz de uso del agente a reportar");
			e.printStackTrace();
            }
                        }
    else {
    throw   new  Exception ("los parametros de entradas son incorrectos. La interfaz del repositorio es nula o el identificador del agente es vacion. Con ellos no se pueden enviar los eventos");
     }
 }
  }