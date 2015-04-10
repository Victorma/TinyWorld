package icaro.aplicaciones.recursos.visualizacionAcceso.imp.gui;


import icaro.aplicaciones.recursos.visualizacionAcceso.imp.ClaseGeneradoraVisualizacionAcceso;
import icaro.aplicaciones.recursos.visualizacionAcceso.imp.swing.VentanaEstandar;
import icaro.aplicaciones.recursos.visualizacionAcceso.imp.NotificacionesEventosVisAcceso;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class PanelAccesoUsuario1_1 extends VentanaEstandar{

			private static final long serialVersionUID = 1L;
		
			/**
			 * @uml.property  name="usoAgente"
			 * @uml.associationEnd  multiplicity="(1 1)"
			 */
			private NotificacionesEventosVisAcceso usoAgente; //comunicaci�n con el agente (control)
			
			
			public PanelAccesoUsuario1_1(ClaseGeneradoraVisualizacionAcceso visualizador) {
				usoAgente = new NotificacionesEventosVisAcceso(visualizador);
				initComponents();
				this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				
			}
			
		    // <editor-fold defaultstate="collapsed" desc=" C�digo Generado  ">
		    private void initComponents() {
		    	this.addWindowListener(new WindowAdapter()
				{
		    		public void windowClosing(WindowEvent e)
		    		{
		    			//usoAgente.notificacionCierreSistema();
		    			JOptionPane.showMessageDialog(null,"Como el control est� en el agente, la ventana no podr� cerrar el sistema directamente.\n Si " +
		    										  "quisi�ramos que esto ocurriera, deber�amos enviar un Evento y modificar el aut�mata del Agente.");
		    		}
				});
		        labelUsr = new javax.swing.JLabel();
		        labelPwd = new javax.swing.JLabel();
		        textoUsr = new javax.swing.JTextField();
		        textoPwd = new javax.swing.JPasswordField();
		        botonAccess = new javax.swing.JButton();
		        botonBorrarDatos = new javax.swing.JButton();

		        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		        labelUsr.setText("User:");

		        labelPwd.setText("Password:");

		        botonAccess.setText("Access");
		        botonAccess.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                botonAccessActionPerformed(evt);
		            }
		        });

		        botonBorrarDatos.setText("Borrar datos");
		        botonBorrarDatos.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                botonCancelActionPerformed(evt);
		            }
		        });

		        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		        getContentPane().setLayout(layout);
		        layout.setHorizontalGroup(
		            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(layout.createSequentialGroup()
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                    .add(layout.createSequentialGroup()
		                        .add(65, 65, 65)
		                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                            .add(labelUsr)
		                            .add(labelPwd))
		                        .add(15, 15, 15)
		                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                            .add(textoPwd)
		                            .add(textoUsr, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
		                    .add(layout.createSequentialGroup()
		                        .add(97, 97, 97)
		                        .add(botonAccess, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                        .add(41, 41, 41)
		                        .add(botonBorrarDatos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
		                .addContainerGap(107, Short.MAX_VALUE))
		        );
		        layout.setVerticalGroup(
		            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(layout.createSequentialGroup()
		                .add(73, 73, 73)
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
		                    .add(labelUsr)
		                    .add(textoUsr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .add(49, 49, 49)
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
		                    .add(textoPwd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(labelPwd))
		                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 61, Short.MAX_VALUE)
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
		                    .add(botonAccess, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(botonBorrarDatos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .add(46, 46, 46))
		        );
		        pack();
		        
		        this.setOpcionMaximizar(false);
		        this.setTitle(" Agente Acceso");
		        this.setPosicionCentrada(300,100);
		        this.setDimension(350,350);
		        
		        
		    }// </editor-fold>

		    private void botonCancelActionPerformed(java.awt.event.ActionEvent evt) {
		    	//usoAgente.notificacionCierreSistema();
		    	textoUsr.setText("");
		    	textoPwd.setText("");
		    }

		    private void botonAccessActionPerformed(java.awt.event.ActionEvent evt) {
		    	String usr = textoUsr.getText();
		        String pwd = textoPwd.getText();
		    	if((usr.equals(""))||(pwd.equals("")))
		    		usoAgente.getVisualizador().mostrarMensajeError("Acceso no Valido", "Verifique que ha introducido un nombre de usuario y una clave correcta");
		    	else
		    		usoAgente.peticionAutentificacion(textoUsr.getText(),textoPwd.getText());
		    		
		    }
		    
		    /**
		     * @param args the command line arguments
		     */
		    public static void main(String args[]) {
		        java.awt.EventQueue.invokeLater(new Runnable() {
		            public void run() {
		               // new VentanaAcceso().setVisible(true);
		            }
		        });
		    }
		    
		    // Declaraci�n de varibales -no modificar
		    /**
			 * @uml.property  name="botonAccess"
			 * @uml.associationEnd  multiplicity="(1 1)"
			 */
		    private javax.swing.JButton botonAccess;
		    /**
			 * @uml.property  name="botonCancel"
			 * @uml.associationEnd  multiplicity="(1 1)"
			 */
		    private javax.swing.JButton botonBorrarDatos;
		    /**
			 * @uml.property  name="labelPwd"
			 * @uml.associationEnd  multiplicity="(1 1)"
			 */
		    private javax.swing.JLabel labelPwd;
		    /**
			 * @uml.property  name="labelUsr"
			 * @uml.associationEnd  multiplicity="(1 1)"
			 */
		    private javax.swing.JLabel labelUsr;
		    /**
			 * @uml.property  name="textoPwd"
			 * @uml.associationEnd  multiplicity="(1 1)"
			 */
		    private javax.swing.JPasswordField textoPwd;
		    /**
			 * @uml.property  name="textoUsr"
			 * @uml.associationEnd  multiplicity="(1 1)"
			 */
		    private javax.swing.JTextField textoUsr;
		    // Fin de declaraci�n de variables
		    
		}
