package icaro.aplicaciones.recursos.persistenciaAccesoBD.imp;
import icaro.aplicaciones.recursos.persistenciaAccesoBD.imp.util.ScriptRunner;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.io.FileReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 * Proporciona los servicios de acceso a la bbdd con mysql
 *
 * @author Juan Zamorano
 *
 */
public class PersistenciaAccesoImp {
    private Connection conexion;
    
    private Statement consulta;
    private static Connection conn=null;
    static public   String nombreBD;
    
    private ResultSet resultado;
    public Connection getConexion() {
    return conexion;
}    
public void setConexion(Connection conexion) {
        this.conexion = conexion;
}    
public PersistenciaAccesoImp conectar() {
    try {
            Class.forName("com.mysql.jdbc.Driver");
            String BaseDeDatos = "jdbc:mysql://localhost/test?user=usuario&password=123";
            setConexion(DriverManager.getConnection(BaseDeDatos));
            if(getConexion() != null){
                System.out.println("Conexion Exitosa!");
            }else{
                System.out.println("Conexion Fallida!");                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
   
 }
public boolean ejecutar(String sql) {
        try {
            consulta = getConexion().createStatement(resultado.TYPE_FORWARD_ONLY, resultado.CONCUR_READ_ONLY);
            consulta.executeUpdate(sql);
            consulta.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }        return true;
    }

//Ahora vamos a crear el método para realizar consultas a la BBDD
public ResultSet consultar(String sql) {
        ResultSet resultado;
        try {
            consulta = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = consulta.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }        return resultado;
    }

public void insertaUsuario (String usuario, String password) throws ErrorEnRecursoException {

		boolean estado = false;

		try {
      		conectar();
     // 		crearQuery();
                consulta = conn.createStatement();
      		consulta.executeUpdate("INSERT INTO "+ this.nombreBD +".tb_acceso VALUES ('"+
      				usuario+"','"+password+"')");
		desconectar();
		}

		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}

public static void desconectar() {

		try	{
			conn.close();
		}
		catch (SQLException e) {
			System.out.println("\nNo se ha podido cerrar la conexi�n con la base de datos: " + e.getMessage());
			e.printStackTrace();
		}
        }
public boolean compruebaUsuario(String usuario, String password) throws ErrorEnRecursoException {
    boolean estado = false;
    try{
                conectar();
                consulta= conn.createStatement();
      		resultado = consulta.executeQuery("SELECT * FROM "+ this.nombreBD +".tb_acceso U where U.user = '"
      					+ usuario + "' and U.password = '" + password + "'");
			if (resultado.next()) estado = true;
			else estado = false;
			resultado.close();
			desconectar();
			return estado;
    }
    catch (Exception e){
        throw new ErrorEnRecursoException(e.getMessage());
    }
   }

public boolean compruebaNombreUsuario(String usuario) throws ErrorEnRecursoException {
    boolean estado = false;
    try{
                conectar();
                consulta= conn.createStatement();
      		resultado = consulta.executeQuery("SELECT * FROM "+ this.nombreBD +".tb_acceso U where U.user = '"
      					+ usuario + "'");
			if (resultado.next()) estado = true;
			else estado = false;
			resultado.close();
			desconectar();
			return estado;
    }
    catch (Exception e){
        throw new ErrorEnRecursoException(e.getMessage());
    }
   }
// la consulta hay que cambiarla según campos de la base de datos que defina Rico
ResultSet obtieneDatosUsuario (String usuario) throws ErrorEnRecursoException{
    try{
        conectar();
        consulta = conn.createStatement();
        resultado = consulta.executeQuery("SELECT * FROM " + this.nombreBD + ".tb_acceso U where U.user = '" + usuario + "'"); 
        return resultado;
    }
    catch (Exception e){
         throw new ErrorEnRecursoException(e.getMessage());       
            }
}
}