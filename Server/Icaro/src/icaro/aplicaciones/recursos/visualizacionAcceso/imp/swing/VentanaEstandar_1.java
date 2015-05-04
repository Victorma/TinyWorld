package icaro.aplicaciones.recursos.visualizacionAcceso.imp.swing;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class VentanaEstandar_1 extends JFrame {

	private static final long serialVersionUID = 1L;

	
	public void mostrar() {
		this.setVisible(true);
	}
	
	public void ocultar() {
		this.setVisible(false);
	}
	
	public void destruir() {
		this.dispose();
	}
	
	public void setDimension(int ancho,int alto) {
		this.setSize(ancho,alto);
	}
	
	public void setPosicion(int horizontal,int vertical) {
		this.setLocation(horizontal,vertical);
	}
	
	public void setPosicionCentrada(int ancho,int alto) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)((screenSize.getWidth()-ancho)/2),(int)((screenSize.getHeight()-alto)/3));
	}
	
	public void setOpcionMaximizar(boolean estado) {
		this.setResizable(estado);
	}
	
	public void setTitulo(String titulo) {
		this.setTitle(titulo);
	}
	
	public void setMenu(JMenuBar menu) {
		this.setJMenuBar(menu);
	}
	
	public void setPanel(JPanel panel) {
		this.setContentPane(panel);
	}
}
