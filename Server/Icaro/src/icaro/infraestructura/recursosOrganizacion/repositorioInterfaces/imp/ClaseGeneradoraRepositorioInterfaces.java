package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp;

import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.rmi.RemoteException;
import java.util.ArrayList;

public abstract class ClaseGeneradoraRepositorioInterfaces extends ImplRecursoSimple
    implements ItfUsoRepositorioInterfaces {

    public ClaseGeneradoraRepositorioInterfaces(String idRecurso) throws RemoteException {
        super(idRecurso);
    }

    private static final long serialVersionUID = 1L;
    private static ClaseGeneradoraRepositorioInterfaces instance;

    public static ClaseGeneradoraRepositorioInterfaces instance() {
        if (instance == null) {
            try {
                instance = new RepositorioInterfacesImpGen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static ClaseGeneradoraRepositorioInterfaces instance(String implementacion) {
        if (instance == null) {
            try {
                Class imp = Class.forName(implementacion);
                instance = (ClaseGeneradoraRepositorioInterfaces) imp.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    @Override
    public abstract void registrarInterfaz(String nombre, Object interfaz);

    @Override
    public abstract Object obtenerInterfaz(String nombre) throws Exception;

    @Override
    public abstract Object obtenerInterfazUso(String nombre) throws Exception;

    @Override
    public abstract Object obtenerInterfazGestion(String nombre) throws Exception;

    @Override
    public abstract void eliminarRegistroInterfaz(String nombre) throws Exception;

    @Override
    public abstract String listarNombresInterfacesRegistradas() throws Exception;

    @Override
    public abstract Boolean estaRegistradoEsteNombre(String nombreEntidad) throws Exception;

    @Override
    public abstract Boolean estaRegistradoEsteRecurso(String nombreRecurso) throws Exception;

    @Override
    public abstract ArrayList nombresAgentesAplicacionRegistrados() throws Exception;

    @Override
    public abstract ArrayList nombresRecursosRegistrados() throws Exception;

    @Override
    public abstract Boolean estaRegistradoEsteAgente(String nombreAgente);
}
