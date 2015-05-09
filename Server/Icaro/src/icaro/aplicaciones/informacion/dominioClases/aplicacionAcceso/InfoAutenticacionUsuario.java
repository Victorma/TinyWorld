package icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso;

import java.io.Serializable;

public class InfoAutenticacionUsuario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * @uml.property  name="usuario"
	 */
	private String usuario;
	/**
	 * @uml.property  name="password"
	 */
	private String clave;
		
	
	public InfoAutenticacionUsuario (String usuario, String clave) {
		
		this.usuario = usuario;
		this.clave = clave;
		
	}
	
	public String getClave() {
		return clave;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
}