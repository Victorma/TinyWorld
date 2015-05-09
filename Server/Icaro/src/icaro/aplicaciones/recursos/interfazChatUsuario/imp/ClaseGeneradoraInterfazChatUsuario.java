package icaro.aplicaciones.recursos.interfazChatUsuario.imp;

import icaro.aplicaciones.recursos.interfazChatUsuario.imp.gui.PanelChatUsuario;
import icaro.aplicaciones.recursos.interfazChatUsuario.ItfUsoInterfazChatUsuario;
import icaro.aplicaciones.recursos.interfazChatUsuario.imp.gui.PanelAccesoAltaUsuario;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;



/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class ClaseGeneradoraInterfazChatUsuario extends ImplRecursoSimple implements ItfUsoInterfazChatUsuario {

	private static final long serialVersionUID = 1L;

	//Interfaz de uso del agente de acceso
	private InterfazUsoAgente itfUsoAgenteAReportar;
	
	//Ventana que gestiona este visualizador
	private PanelAccesoAltaUsuario ventanaAltaUsuario;
        private PanelChatUsuario ventanaAccesoUsuario;
        private NotificacionesEventosVisAccesoAlta notifAgente;
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
        private InterfazUsoAgente itfUsoAgteAreportar;
	private String nombreAgenteAreportar ;
        private String tipoAgenteAreportar = "Reactivo" ;
        private String nombredeEsteRecurso;
        private String agenteaReportar;
  	public ClaseGeneradoraInterfazChatUsuario(String id) throws Exception{
  		super(id);
                nombredeEsteRecurso = id;
                trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
                notifAgente = new NotificacionesEventosVisAccesoAlta(nombredeEsteRecurso,nombreAgenteAreportar);
                this.ventanaAccesoUsuario = new PanelChatUsuario(notifAgente);
                this.ventanaAltaUsuario = new PanelAccesoAltaUsuario(notifAgente );
  		this.inicializa();
	}
  	
  	private void inicializa() throws Exception {
            ventanaAccesoUsuario.setPosicion(850,100);
            trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Inicializando recurso",
  				InfoTraza.NivelTraza.debug));
  		
  	}
  	
  	
	/**
	 * @return Returns the itfUsoAgenteAcceso.
	 */
//	public ItfUsoAgenteReactivo getItfUsoAgenteAcceso() {
//		return itfUsoAgenteAReportar;
//	}
	
	public ItfUsoRepositorioInterfaces getItfUsoRepositorioInterfaces() {
		return itfUsoRepositorioInterfaces;
	}

	/**
	 * @param itfUsoAgenteAcceso The itfUsoAgenteAcceso to set.
	 */
//	public void setItfUsoAgenteAcceso(ItfUsoAgenteReactivo itfUsoAgenteAcceso) {
//		this.itfUsoAgenteAcceso = itfUsoAgenteAcceso;
//	}
    @Override
        public void setIdentAgenteAReportar(String identAgente) {
		this.nombreAgenteAreportar = identAgente;
	}
	public void mostrarVisualizadorAcceso(String nombreAgente, String tipo) {
                this.identAgenteAReportar = nombreAgente;
                this.notifAgente.setIdentificadorAgenteAReportar(identAgenteAReportar);
		this.ventanaAccesoUsuario.mostrar();
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
	public void cerrarVisualizadorAcceso() {
		this.ventanaAccesoUsuario.ocultar();
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Cerrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
	public void mostrarVisualizadorAccesoAlta(String nombreUsuario) {
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Mostrando recurso...",
  				InfoTraza.NivelTraza.debug));
		this.ventanaAltaUsuario.mostrar();
                this.ventanaAltaUsuario.setInfoUsname(nombreUsuario);
	}
 
	public void cerrarVisualizadorAccesoAlta() {
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Cerrando recurso...",
  				InfoTraza.NivelTraza.debug));
		this.ventanaAltaUsuario.ocultar();
	}  
  
	public void mostrarMensajeInformacion(String titulo,String mensaje) {
	/*Muestra el mensaje y avisa al gestor para finalizar*/
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Mostrando mensaje de informaci�n",
  				InfoTraza.NivelTraza.debug));
		JOptionPane.showMessageDialog(ventanaAccesoUsuario,mensaje,titulo,JOptionPane.INFORMATION_MESSAGE);
		
		/*try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz("AgenteAccesoUso");
			itfUsoAgente.aceptaEvento(new EventoInput("termina","AgenteAccesoUso","AgenteAccesoUso"));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento termina al agente");
			e.printStackTrace();
		}
		*/
	}
  
	public void mostrarMensajeAviso(String titulo,String mensaje) {
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	JOptionPane.showMessageDialog(ventanaAccesoUsuario,mensaje,titulo,JOptionPane.WARNING_MESSAGE);
	}
	
	public void mostrarMensajeError(String titulo,String mensaje) {
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	JOptionPane.showMessageDialog(ventanaAccesoUsuario,mensaje,titulo,JOptionPane.ERROR_MESSAGE);
	}	
	
	@Override
	public void termina() {
		this.ventanaAccesoUsuario.destruir();
		try {
			trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
	  				"Terminando recurso",
	  				InfoTraza.NivelTraza.debug));
			super.termina();
			
			
		} catch (Exception e) {
			this.itfAutomata.transita("error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        public String getNombreAgenteAReportar() {
		return nombreAgenteAreportar;
	}
        public InterfazUsoAgente getItfUsoAgenteAReportar(String identAgteAreportar) {

             try {
			itfUsoAgteAreportar = (InterfazUsoAgente) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz
		(NombresPredefinidos.ITF_USO+identAgteAreportar);
		}
		catch (Exception e) {
			Logger.getLogger(NotificacionesEventosVisAccesoAlta.class.getName()).log(Level.SEVERE, null, e);
    //      Logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor);
			NombresPredefinidos.RECURSO_TRAZAS_OBJ.aceptaNuevaTraza(new InfoTraza(this.nombredeEsteRecurso,
									"Ha habido un problema al obtener la interfaz de uso del agente: "+identAgteAreportar,
									InfoTraza.NivelTraza.error));
			}
             return itfUsoAgteAreportar;
	}
        public String getNombredeEsteRecurso() {
		return nombredeEsteRecurso;
	}
	/**
	 * @param nombreAgenteAcceso
	 * @uml.property  name="nombreAgenteAcceso"
	 */
	public void setNombreAgenteAreportar(String nombreAgenteAcceso) {
		this.nombreAgenteAreportar = nombreAgenteAcceso;
	}

	/**
	 * @return
	 * @uml.property  name="tipoAgenteAcceso"
	 */
	public String getTipoAgenteAreportar() {
		return tipoAgenteAreportar;
	}

	/**
	 * @param tipoAgenteAcceso
	 * @uml.property  name="tipoAgenteAcceso"
	 */
//	public void setTipoAgenteAreportar(String tipoAgenteaReportar) {
//		this.tipoAgenteaReportar = tipoAgenteaReportar;
//	}
        @Override
        public void mostrarVisualizadorChatUsuario(String nombreAgente,String tipo) throws Exception{
            
        }
        @Override
        public void mostrarInfoMensajeEnviado(String mensaje) throws Exception{
            
        }
        @Override
        public void mostrarInfoMensajeRecibido(String mensaje) throws Exception{
            
        }
        @Override
       public void cerrarVisualizadorChatUsuario() throws Exception{
            
        }
public void informeError(String msgError) {
		this.itfAutomata.transita("error");
        trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
	  				"se produjo un error al enviar un evento ",
	  				InfoTraza.NivelTraza.debug));
	}
}