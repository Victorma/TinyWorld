/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.comunicacionChat;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author FGarijo
 */
public class ClaseGeneradoraComunicacionChatTest {
    private ClaseGeneradoraComunicacionChat instance ;
    public ClaseGeneradoraComunicacionChatTest() {
        instance =null;
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            instance = new ClaseGeneradoraComunicacionChat("prueba" );
        } catch (Exception ex) {
            Logger.getLogger(ClaseGeneradoraComunicacionChatTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of conectar method, of class ClaseGeneradoraComunicacionChat.
     */
    @Test
    public void testConectar() {
        System.out.println("conectar");
//        url="irc.freenode.net";
//        nickname="pacopa";
        String url = "irc.freenode.net";
        String canal = "";
        String nick = "pacopa";
        Boolean expResult = true;
        Boolean result = null;
//        ClaseGeneradoraComunicacionChat instance;
        try {
            
            
             result = instance.conectar(url, canal, nick);
        } catch (Exception ex) {
            Logger.getLogger(ClaseGeneradoraComunicacionChatTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of enviarMensage method, of class ClaseGeneradoraComunicacionChat.
     */
    @Test
    public void testEnviarMensage() {
        System.out.println("enviarMensage");
        String mensaje = "hola hola";
//        ClaseGeneradoraComunicacionChat instance = null;
        try {
            instance.enviarMensagePrivado(mensaje);
        } catch (Exception ex) {
            Logger.getLogger(ClaseGeneradoraComunicacionChatTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Fallo el test.");
        }
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of termina method, of class ClaseGeneradoraComunicacionChat.
     */
    @Test
    public void testTermina() {
        System.out.println("termina");
//        instance = null;
//        instance.termina();
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
