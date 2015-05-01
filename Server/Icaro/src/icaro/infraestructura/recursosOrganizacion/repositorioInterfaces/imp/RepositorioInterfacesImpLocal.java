package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RepositorioInterfacesImpLocal extends ClaseGeneradoraRepositorioInterfaces {

    // Tabla con todos los interfaces
    private Map<String, Object> repositorio;

    // Depuracion del componente
    public final static boolean DEBUG = true;

    /**
     * Constructor. Registra el repositorio y lo deja accesible de forma remota.
     * @throws java.rmi.RemoteException
     */
    public RepositorioInterfacesImpLocal() throws RemoteException {
        super("RepositorioInterfaces");
        repositorio = new HashMap<String, Object>();
    }

    /**
     * Almacena una interfaz en el repositorio En caso de existir el nombre previamente, se
     * actualiza la referencia
     *
     * @param nombre
     * @param interfaz
     */
    @Override
    public synchronized void registrarInterfaz(String nombre, Object interfaz) {
        this.repositorio.put(nombre, interfaz);
        if (DEBUG) {
            System.out.println("Registrado en repositorio nombre=" + nombre + ", interfaz=" + interfaz);
        }
    }

    /**
     * Recupera una interfaz del repositorio
     *
     * @param nombre Nombre de la interfaz a recuperar
     * @return Interfaz asociada a ese nombre o null si no se ha encontrado ese nombre
     */
    @Override
    public synchronized Object obtenerInterfaz(String nombre) {
        if (!this.repositorio.containsKey(nombre)) {
            System.err.println("RepositorioInterfaces: No se pudo recuperar " + nombre + " porque no existe ningn objeto con ese nombre");
            System.err.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
            System.out.println("RepositorioInterfaces: No se pudo recuperar " + nombre + " porque no existe ningn objeto con ese nombre");
            System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
        }
        return this.repositorio.get(nombre);
    }

    @Override
    public synchronized Object obtenerInterfazUso(String nombre) throws Exception {
        String identInterfaz = NombresPredefinidos.ITF_USO + nombre;
        if (!this.repositorio.containsKey(identInterfaz)) {
            System.err.println("RepositorioInterfaces: No se pudo recuperar " + identInterfaz + " porque no existe ningn objeto con ese nombre");
            System.err.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
            System.out.println("RepositorioInterfaces: No se pudo recuperar " + identInterfaz + " porque no existe ningn objeto con ese nombre");
            System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
        }
        return this.repositorio.get(identInterfaz);
    }

    @Override
    public synchronized Object obtenerInterfazGestion(String nombre) throws Exception {
        String identInterfaz = NombresPredefinidos.ITF_GESTION + nombre;
        if (!this.repositorio.containsKey(identInterfaz)) {
            System.err.println("RepositorioInterfaces: No se pudo recuperar " + identInterfaz + " porque no existe ningn objeto con ese nombre");
            System.err.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
            System.out.println("RepositorioInterfaces: No se pudo recuperar " + identInterfaz + " porque no existe ningn objeto con ese nombre");
            System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
        }
        return this.repositorio.get(identInterfaz);
    }

    /**
     * Cancela el registro de una interfaz en el repositorio
     * @param nombre Nombre de la interfaz a eliminar del repositorio
     */
    @Override
    public synchronized void eliminarRegistroInterfaz(String nombre) {
        if (this.repositorio.containsKey(nombre)) {
            this.repositorio.remove(nombre);
            if (DEBUG) {
                System.out.println("Se elimino la referencia a " + nombre + " del repositorio de interfaces.");
            }
        } else if (DEBUG) {
            System.out.println("Se intento eliminar la referencia " + nombre + " del repositorio, pero no estaba definida.");
        }
    }

    /**
     * Devuelve una lista con los nombres de todos los interfaces registrados
     * @return
     */
    @Override
    public String listarNombresInterfacesRegistradas() {
        String ret = "";
        for (String item : this.repositorio.keySet()) {
            ret += item + " ";
        }
        return ret;
    }

    /**
     * Devuelve un ArrayList con los nombres de todos los interfaces registrados
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList nombresInterfacesRegistradas() throws Exception {
        return new ArrayList(this.repositorio.keySet());
    }

    @Override
    public Boolean estaRegistradoEsteNombre(String nombreEntidad) {
        return this.repositorio.containsKey(nombreEntidad);
    }

    @Override
    public ArrayList nombresAgentesAplicacionRegistrados() throws Exception {
        ArrayList ret = new ArrayList();
        String identTipoAgenteReActivo = NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE + NombresPredefinidos.TIPO_REACTIVO;
        String identTipoAgenteCognitivo = NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE + NombresPredefinidos.TIPO_COGNITIVO;
        for (String item : this.repositorio.keySet()) {
            String itf = this.repositorio.get(item).toString();
            String identItf = item.toString();
            if (((itf.startsWith(identTipoAgenteReActivo)) || (itf.startsWith(identTipoAgenteCognitivo))) && identItf.contains(NombresPredefinidos.ITF_USO)) {
                String identAgente = identItf.replaceFirst(NombresPredefinidos.ITF_USO, "");
                if (!(identAgente.contains("Gestor"))) {
                    ret.add(identAgente);
                }
            }
        }
        return ret;
    }

    @Override
    public ArrayList nombresRecursosRegistrados() throws Exception {
        ArrayList ret = new ArrayList();
        for (String item : this.repositorio.keySet()) {
            if (item.contains("Recurso")) {
                ret.add(item);
            }
        }
        return ret;
    }

    @Override
    public Boolean estaRegistradoEsteAgente(String nombreAgente) {
        // Verificamos que la entidad registrada es un agente
        if (!this.repositorio.containsKey(nombreAgente)) {
            return false;
        } else {
            String repoContenido = this.repositorio.get(nombreAgente).toString();
            if (repoContenido.matches("patronAgente")) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Boolean estaRegistradoEsteRecurso(String nombreRecurso) {
        // Verificamos que la entidad registrada es un recurso
        // La verificacion es bastante ligera y se hace comprabando que el nombre tiene una interfaz asociada y
        // que en el nombre de la interfaz aparece la cadena recurso. Esto es equivalente a que debe haber sido generado
        // por patron que verifica la ruta de la clase generadora
        if (!this.repositorio.containsKey(nombreRecurso)) {
            return false;
        } else {
            String repoContenido = this.repositorio.get(nombreRecurso).toString();
            return repoContenido.matches("recurso");
        }
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer("Listado de interfaces registrados Nombre/Interfaz");
        for (String key : this.repositorio.keySet()) {
            str.append("\n");
            str.append(key);
            str.append("->");
            str.append(this.repositorio.get(key));
        }
        return str.toString();
    }
}
