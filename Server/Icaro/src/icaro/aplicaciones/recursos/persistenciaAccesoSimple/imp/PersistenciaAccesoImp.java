/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.recursos.persistenciaAccesoSimple.imp;
import com.larvalabs.megamap.MegaMapManager;
import com.larvalabs.megamap.MegaMap;
import com.larvalabs.megamap.MegaMapException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Properties;

public class PersistenciaAccesoImp implements Serializable{
    public static MegaMapManager manager ;
    public static MegaMap tablaUsuriosAutorizados;

public PersistenciaAccesoImp(){
        
     try {
            // Get Manager singleton
             manager = MegaMapManager.getMegaMapManager();
            {
                // Create MegaMap with name "map1"
                //  (the other two parameters will be discussed later)
                 tablaUsuriosAutorizados = manager.createMegaMap("BdUsuriosAutorizados", true, true);
                // tablaUsuriosAutorizados = manager.getMegaMap("BdUsuriosAutorizados");
                // Add some objects with Integer keys
                tablaUsuriosAutorizados.put("usuario1", "Test Object 1");
                tablaUsuriosAutorizados.put("usuario2", "Test Object 2");
//                tablaUsuriosAutorizados.put(new Integer(3), "Test Object 3");
//                tablaUsuriosAutorizados.put(new Integer(4), "Test Object 4");
                // Retrieve and display one of the objects
                String s = (String) tablaUsuriosAutorizados.get("usuario2");
                tablaUsuriosAutorizados.remove("usuario2");
                tablaUsuriosAutorizados.remove("usuario1");
                System.out.println(s);
                // Shutdown the manager
 //               manager.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
         public static void crearUsuariosAutorizados()
        throws IOException
    {

           try {
            // Get Manager singleton
             manager = MegaMapManager.getMegaMapManager();
            {
                // Create MegaMap with name "map1"
                //  (the other two parameters will be discussed later)
                 tablaUsuriosAutorizados = manager.createMegaMap("BdUsuriosAutorizados", true, true);
               //  tablaUsuriosAutorizados = manager.getMegaMap("BdUsuriosAutorizados");
                // Add some objects with Integer keys
                tablaUsuriosAutorizados.put("usuario1", "Test Object 1");
                tablaUsuriosAutorizados.put("usuario2", "Test Object 2");
//                tablaUsuriosAutorizados.put(new Integer(3), "Test Object 3");
//                tablaUsuriosAutorizados.put(new Integer(4), "Test Object 4");
                // Retrieve and display one of the objects
                String s = (String) tablaUsuriosAutorizados.get("usuario2");
                tablaUsuriosAutorizados.remove("usuario2");
                tablaUsuriosAutorizados.remove("usuario1");
                System.out.println(s);
                // Shutdown the manager
 //               manager.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // insert keys and values
        System.out.println();
        System.out.println( "Adding usuarios autorizados ..." );
        tablaUsuriosAutorizados.put( "root", "root" );
        tablaUsuriosAutorizados.put( "user1", "usuario1" );
        tablaUsuriosAutorizados.put( "user2", "user2" );

//        showBDusuarios();


    }
public static boolean compruebaUsuario(String usuario, String password)
			throws IOException {

   try {
//      manager = MegaMapManager.getMegaMapManager();
//     tablaUsuriosAutorizados = manager.getMegaMap("BdUsuriosAutorizados");
   if ( tablaUsuriosAutorizados.hasKey(usuario)){
       String valorTabla =(String) tablaUsuriosAutorizados.get(usuario);
       return (valorTabla.equals(password));
   
    } else return false;
   }
    catch (MegaMapException e) {
                        e.printStackTrace();
                        System.out.println( "Problema al comprobar usn y pasw  " + e.getCause() );

                        }

    return false;

}
    

public static void insertaUsuario(String usuario, String password)
			throws IOException {
       System.out.println( "Adding usuarios autorizados ..." );
        tablaUsuriosAutorizados.put( usuario, password );

}

    public void showBDusuarios()
        throws IOException
    {
      
        System.out.println();
        System.out.print( "Fruit basket contains: " );
        Iterator iter = tablaUsuriosAutorizados.getKeys().iterator();
        String usn = (String) iter.next();
        while ( iter.hasNext()) {
            System.out.print( " " + usn );
            usn = (String)iter.next();
        }
        System.out.println();
    }
    public static void terminar(){
         // Shutdown the manager
             manager.shutdown();
    }
}
