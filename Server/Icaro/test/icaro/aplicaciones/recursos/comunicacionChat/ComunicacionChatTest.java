/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.comunicacionChat;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.fail;

/**
 *
 * @author FGarijo
 */
public class ComunicacionChatTest {
      private static ClaseGeneradoraComunicacionChat instance ;
    public static void main(String args[]) {
//        junit.textui.TestRunner.run(suite());
        
          testSuit("conexion y envio de mensaje");
       
//        ClaseGeneradoraComunicacionChat instance = null;
        
    }
        private static void testSuit(String identTestsuite) {
    try {
        System.out.println("enviarMensage");
        String url = "irc.freenode.net";
        String canal = "";
        String nick = "pacopa";
         String mensaje = "hola hola";
            instance = new ClaseGeneradoraComunicacionChat("prueba" );
            instance.enviarMensagePrivado(mensaje);
            Thread.sleep(5000);
            instance.desconectar();
        } catch (Exception ex) {
            Logger.getLogger(ClaseGeneradoraComunicacionChatTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Fallo el test.");
        }
}
    
}
