/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas;

import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFconGesAcciones.InterpreteAutomataEFconGestAcciones;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author FGarijo
 */
public class FactoriaAutomatasNGTest {
    
    public FactoriaAutomatasNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of crearAutomataEFconGestAcciones method, of class FactoriaAutomatas.
     */
    @Test
    public void testCrearAutomataEFconGestAcciones() {
        System.out.println("crearAutomataEFconGestAcciones");
        String identPropietario = "";
        String rutaFicheroAutomata = "";
        String rutaCarpetaAcciones = "";
        Boolean trazar = null;
        FactoriaAutomatas instance = new FactoriaAutomatas();
        InterpreteAutomataEFconGestAcciones expResult = null;
        InterpreteAutomataEFconGestAcciones result = instance.crearAutomataParaControlAgteReactivo(identPropietario, rutaFicheroAutomata, rutaCarpetaAcciones, trazar);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class FactoriaAutomatas.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        FactoriaAutomatas.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}