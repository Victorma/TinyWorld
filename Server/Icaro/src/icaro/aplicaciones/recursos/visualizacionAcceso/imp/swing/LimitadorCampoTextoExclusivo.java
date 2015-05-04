package icaro.aplicaciones.recursos.visualizacionAcceso.imp.swing;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;



@SuppressWarnings("serial")
public class LimitadorCampoTextoExclusivo extends LimitadorCampoTexto {
	
	public LimitadorCampoTextoExclusivo(JTextField componenteTexto, int numeroMaximoCaracteres){
		super(componenteTexto, numeroMaximoCaracteres);
	}
	
	public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
		/*
		char[] cadena = arg1.toCharArray();
		for (char c : cadena){
			if (!Character.isLetter(c)){
				return;
			}
		}
		*/
		try{
			Integer.parseInt(arg1);
			return;
		} catch (NumberFormatException e){
			
		}
		super.insertString(arg0, arg1, arg2);
	}
}