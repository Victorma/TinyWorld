package icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso;

import java.io.Serializable;

public class InfoAccesoSinValidar implements Serializable {
	
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
	private String password;
		
	
	public InfoAccesoSinValidar (String usuario, String password) {
		
		this.usuario = usuario;
		this.password = password;
		
	}
	
	public String tomaPassword() {
		return password;
	}
	
	public String tomaUsuario() {
		return usuario;
	}
	
}