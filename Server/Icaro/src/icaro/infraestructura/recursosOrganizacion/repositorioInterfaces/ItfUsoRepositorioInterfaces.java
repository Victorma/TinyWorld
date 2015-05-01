package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ItfUsoRepositorioInterfaces extends ItfUsoRecursoSimple {
    public void registrarInterfaz(String nombre, Object interfaz) throws RemoteException;
    public Object obtenerInterfaz(String nombre) throws Exception;
    public void eliminarRegistroInterfaz(String nombre) throws Exception;
    public String listarNombresInterfacesRegistradas() throws Exception;
    public ArrayList nombresInterfacesRegistradas() throws Exception;
    public ArrayList nombresAgentesAplicacionRegistrados() throws Exception;
    public Object obtenerInterfazUso(String nombre) throws Exception;
    public Object obtenerInterfazGestion(String nombre) throws Exception;
    public Boolean estaRegistradoEsteNombre(String nombreEntidad) throws Exception;
    public Boolean estaRegistradoEsteRecurso(String nombreRecurso) throws Exception;
    public ArrayList nombresRecursosRegistrados() throws Exception;
    public Boolean estaRegistradoEsteAgente(String nombreAgente) throws Exception;
}
