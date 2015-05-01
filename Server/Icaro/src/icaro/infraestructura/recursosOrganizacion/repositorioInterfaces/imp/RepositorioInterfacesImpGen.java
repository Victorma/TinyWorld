package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RepositorioInterfacesImpGen extends ClaseGeneradoraRepositorioInterfaces {

    private Map<String, Object> repositorio;
    public final static boolean DEBUG = true;
    public boolean recursoConfiguracionCreado = false;
    public ItfUsoConfiguracion configuracion;

    public RepositorioInterfacesImpGen() throws RemoteException {
        super("RepositorioInterfaces");
        repositorio = new HashMap<String, Object>();
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    }

    @Override
    public synchronized void registrarInterfaz(String nombre, Object interfaz) {
        this.repositorio.put(nombre, interfaz);
        if (DEBUG) {
            System.out.println("Registrado en repositorio nombre=" + nombre + ", interfaz=" + interfaz);
        }
    }

    @Override
    public synchronized Object obtenerInterfaz(String nombre) {
        if (!this.repositorio.containsKey(nombre)) {
            Object itfRemota = null;
            if (nombre.startsWith(NombresPredefinidos.ITF_USO)) {
                itfRemota = obtenerItfEntidadRemota(nombre, NombresPredefinidos.ITF_USO);
            } else if (nombre.startsWith(NombresPredefinidos.ITF_GESTION)) {
                itfRemota = obtenerItfEntidadRemota(nombre, NombresPredefinidos.ITF_GESTION);
            }
            if (itfRemota != null) {
                registrarInterfaz(nombre, itfRemota);
                return itfRemota;
            } else {
                if (trazas != null) {
                    this.trazas.trazar(this.getId(), " No se pudo recuperar " + nombre + " porque no existe ningn objeto con ese nombre", InfoTraza.NivelTraza.error);
                }
                System.err.println("RepositorioInterfaces: No se pudo recuperar " + nombre + " porque no existe ningn objeto con ese nombre");
                System.err.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
                System.out.println("RepositorioInterfaces: No se pudo recuperar " + nombre + " porque no existe ningn objeto con ese nombre");
                System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
                return null;
            }
        }
        return this.repositorio.get(nombre);
    }

    @Override
    public synchronized Object obtenerInterfazUso(String nombre) throws Exception {
        String identInterfaz = NombresPredefinidos.ITF_USO + nombre;
        if (!this.repositorio.containsKey(identInterfaz)) {
            Object itfRemota = obtenerItfEntidadRemota(nombre, NombresPredefinidos.ITF_USO);
            if (itfRemota != null) {
                registrarInterfaz(nombre, itfRemota);
                return itfRemota;
            } else {
                if (trazas != null) {
                    this.trazas.trazar(this.getId(), " No se pudo recuperar " + nombre + " porque no existe ningn objeto con ese nombre", InfoTraza.NivelTraza.error);
                }
                System.out.println("RepositorioInterfaces: No se pudo recuperar " + identInterfaz + " porque no existe ningn objeto con ese nombre");
                System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
                return null;
            }
        }
        return this.repositorio.get(identInterfaz);
    }

    @Override
    public synchronized Object obtenerInterfazGestion(String nombre) throws Exception {
        String identInterfaz = NombresPredefinidos.ITF_GESTION + nombre;
        if (!this.repositorio.containsKey(identInterfaz)) {
            Object itfRemota = obtenerItfEntidadRemota(nombre, NombresPredefinidos.ITF_GESTION);
            if (itfRemota != null) {
                registrarInterfaz(nombre, itfRemota);
                return itfRemota;
            } else {
                if (trazas != null) {
                    this.trazas.trazar(this.getId(), " No se pudo recuperar " + nombre + " porque no existe ningn objeto con ese nombre", InfoTraza.NivelTraza.error);
                }
                System.err.println("RepositorioInterfaces: No se pudo recuperar " + identInterfaz + " porque no existe ningn objeto con ese nombre");
                System.err.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
                System.out.println("RepositorioInterfaces: No se pudo recuperar " + identInterfaz + " porque no existe ningn objeto con ese nombre");
                System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> " + this.listarNombresInterfacesRegistradas());
                return null;
            }
        }
        return this.repositorio.get(identInterfaz);
    }

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

    @Override
    public String listarNombresInterfacesRegistradas() {
        String ret = "";
        for (String item : this.repositorio.keySet()) {
            ret += item + " ";
        }
        return ret;
    }

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
        for (String identItf : this.repositorio.keySet()) {
            String itf = this.repositorio.get(identItf).toString();
            if (((itf.startsWith(identTipoAgenteReActivo)) || (itf.startsWith(identTipoAgenteCognitivo)))
                    && identItf.contains(NombresPredefinidos.ITF_USO)) {
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
        if (!this.repositorio.containsKey(nombreAgente)) {
            return false;
        } else {
            String repoContenido = this.repositorio.get(nombreAgente).toString();
            return repoContenido.matches("patronAgente");
        }
    }

    @Override
    public Boolean estaRegistradoEsteRecurso(String nombreRecurso) {
        if (!this.repositorio.containsKey(nombreRecurso)) {
            return false;
        } else {
            String repoContenido = this.repositorio.get(nombreRecurso).toString();
            return repoContenido.matches("recurso");
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Listado de interfaces registrados Nombre/Interfaz");
        for (String key : this.repositorio.keySet()) {
            str.append("\n");
            str.append(key);
            str.append("->");
            str.append(this.repositorio.get(key));
        }
        return str.toString();
    }

    private Object obtenerItfEntidadRemota(String identEntity, String tipoItf) {
        if (!recursoConfiguracionCreado) {
            configuracion = (ItfUsoConfiguracion) this.repositorio.get(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
            if (configuracion == null) {
                return null;
            } else {
                recursoConfiguracionCreado = true;
            }
        }
        if ((tipoItf == null ? NombresPredefinidos.ITF_USO != null : !tipoItf.equals(NombresPredefinidos.ITF_USO)) &&
            (tipoItf == null ? NombresPredefinidos.ITF_GESTION != null : !tipoItf.equals(NombresPredefinidos.ITF_GESTION))) {
            return null;
        }
        Object itfRemota = null;
        if (identEntity.startsWith(tipoItf)) {
            identEntity = identEntity.replaceFirst(tipoItf, "");
        }
        try {
            if (configuracion.esComponenteRemoto(identEntity)) {
                itfRemota = AdaptadorRegRMI.getItfComponenteRemoto(identEntity, tipoItf);
            }
        } catch (ExcepcionEnComponente ex) {
            Logger.getLogger(RepositorioInterfacesImpGen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(RepositorioInterfacesImpGen.class.getName()).log(Level.SEVERE, null, ex);
            this.trazas.trazar(this.getId(), "Error al intentar obtener una interfaz remota para la entidad:"
                    + identEntity, InfoTraza.NivelTraza.error);
        }
        return itfRemota;
    }
}
