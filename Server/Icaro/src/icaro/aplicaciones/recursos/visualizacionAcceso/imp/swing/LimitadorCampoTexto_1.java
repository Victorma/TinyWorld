package icaro.aplicaciones.recursos.visualizacionAcceso.imp.swing;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitadorCampoTexto_1 extends PlainDocument {
	
	private static final long serialVersionUID = 1L;
    /**
	 * @uml.property  name="componenteTexto"
	 * @uml.associationEnd  
	 */
    private JTextField componenteTexto;
    /**
	 * @uml.property  name="numeroMaximoCaracteres"
	 */
    private int numeroMaximoCaracteres;

    public LimitadorCampoTexto_1(){
    	super();
    }
    
    /**
     * Crea una instancia de LimitadorCampoTexto.
     * 
     * @param componenteTexto Componente de Texto en el que se quieren limitar los caracteres.
     * @param numeroMaximoCaracteres N�mero m�ximo de caracteres que queremos en el editor.
     */
    public LimitadorCampoTexto_1(JTextField componenteTexto, int numeroMaximoCaracteres) {
        this.componenteTexto = componenteTexto;
        this.numeroMaximoCaracteres = numeroMaximoCaracteres;
    }
    
    /**
     * Metodo al que llama el editor cada vez que se intenta insertar caracteres.
     * El metodo comprueba que no se sobrepasa el limite. Si es asi, llama al
     * metodo de la clase padre para que se inserten los caracteres. Si se 
     * sobrepasa el limite, retorna sin hacer nada.
     */
    public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
        
    	if ((componenteTexto.getText().length() + arg1.length()) > this.numeroMaximoCaracteres) return;
        super.insertString(arg0,arg1,arg2);
    } 		
}