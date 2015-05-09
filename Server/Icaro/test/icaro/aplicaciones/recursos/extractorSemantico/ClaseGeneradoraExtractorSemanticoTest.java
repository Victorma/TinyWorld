/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.extractorSemantico;

import gate.AnnotationSet;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author FGarijo
 */
public class ClaseGeneradoraExtractorSemanticoTest extends TestCase {
    private  ClaseGeneradoraExtractorSemantico extractorPrueba;
    public ClaseGeneradoraExtractorSemanticoTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
//        super.setUp();
        if (extractorPrueba == null)
        extractorPrueba = new ClaseGeneradoraExtractorSemantico ( "extractorPrueba");
    }
    
    @Override
    protected void tearDown() throws Exception {
//        super.tearDown();
    }

    /**
     * Test of extraerAnotaciones method, of class ClaseGeneradoraExtractorSemantico.
     */
    public void testExtraerAnotaciones() {
        System.out.println("extraerAnotaciones");
        String textoUsuario = "dame una cita para mañana a las 5,30";
        
//        ClaseGeneradoraExtractorSemantico instance = null;
        HashSet annotTypesRequired = new HashSet();
      annotTypesRequired.add("InicioPeticion");
      annotTypesRequired.add("Location");
        HashSet result = null;
        try {
            result = extractorPrueba.extraerAnotaciones(annotTypesRequired,textoUsuario);
        } catch (Exception ex) {
            Logger.getLogger(ClaseGeneradoraExtractorSemanticoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals("Cita", result.toString());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of termina method, of class ClaseGeneradoraExtractorSemantico.
     */
    public void testTermina() {
        System.out.println("termina");
        if(extractorPrueba!=null)extractorPrueba.termina();
//        instance.termina();
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
