package icaro.infraestructura.patronAgenteReactivo.control;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.acciones.Accion;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionAsincrona;
import icaro.infraestructura.entidadesBasicas.acciones.AccionProxy;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionSincrona;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.gestorAcciones.GestorAccionesAbstr;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.gestorAcciones.ItfGestorAcciones;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class GestorAccionesAgteReactivoImp extends GestorAccionesAbstr implements ItfGestorAcciones {

    private String identPropietario;
    private ItfProductorPercepcion envioInputs;
    private ItfUsoRecursoTrazas trazas;
    private String identAccion;
    private ComunicacionAgentes comunicator;
    private AccionAsincrona accionAsinc;
    private String accionAsincSimpleName;
    private AccionSincrona accionSinc;
    private String accionSincSimpleName;
    private AccionesSemanticasAgenteReactivo accionesSemAgteReactivo;
    private String accionesSemAgteReactivoSimpleName;
    private Map<String, Object> accionesCreadas;
    private Logger log = Logger.getLogger(GestorAccionesAgteReactivoImp.class);
    private ItfControlAgteReactivo itfControlAgte;

    public GestorAccionesAgteReactivoImp() {
        accionAsincSimpleName = AccionAsincrona.class.getSimpleName();
        accionSincSimpleName = AccionSincrona.class.getSimpleName();
        accionesSemAgteReactivoSimpleName = AccionesSemanticasAgenteReactivo.class.getSimpleName();
        this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        accionesCreadas = new HashMap<String, Object>();
    }

    @Override
    public void setPropietario(String identAgte) {
        identPropietario = identAgte;
        comunicator = new ComunicacionAgentes(identAgte);
    }

    public void inicializarInfoAccionesAgteReactivo(String identAgte, ItfProductorPercepcion itfEvtosInternos, ItfControlAgteReactivo itfControl) {
        try {
            identPropietario = identAgte;
            envioInputs = itfEvtosInternos;
            itfControlAgte = itfControl;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(GestorAccionesAgteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
            this.trazas.trazar("GestorAccionesAgteReactivoImp", "Operacion de inicializar Accion. Error en los objetos de inicializacion: Revisar ", InfoTraza.NivelTraza.error);
        }
    }

    @Override
    public void inicializarInfoAcciones(Class claseAccionAinicializar, Object... paramsInicializacion) {
        String identClase = claseAccionAinicializar.getSimpleName();
        try {
            if (identClase.equals(accionesSemAgteReactivoSimpleName)) {
                envioInputs = (ItfProductorPercepcion) paramsInicializacion[0];
                itfControlAgte = (ItfControlAgteReactivo) paramsInicializacion[1];
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(GestorAccionesAgteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
            this.trazas.trazar("GestorAccionesAgteReactivoImp", "Operacion de inicializar Accion. Error en los objetos de inicializacion: Revisar ", InfoTraza.NivelTraza.error);
        }
    }

    @Override
    public Accion crearAccion(Class clase) throws Exception {
        Accion accion = (Accion) clase.newInstance();
        accion.setComunicator(comunicator);
        String identAccion = accion.getClass().getSimpleName();
        accion.setIdentAccion(identAccion);
        log.debug("Accion creada:" + clase.getName());
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        return new AccionProxy(accion);
    }

    @Override
    public AccionSincrona crearAccionSincrona(Class clase) throws Exception {
        identAccion = clase.getSimpleName();
        if (accionesCreadas.containsKey(identAccion)) {
            return (AccionSincrona) accionesCreadas.get(identAccion);
        }
        AccionSincrona accion = (AccionSincrona) clase.newInstance();
        accion.inicializar(identPropietario, envioInputs);
        accion.setComunicator(comunicator);
        accionesCreadas.put(identAccion, accion);
        accion.setIdentAccion(identAccion);
        log.debug("Accion creada:" + clase.getName());
        return accion;
    }

    @Override
    public AccionSincrona crearAccionAsincrona(Class clase) throws Exception {
        identAccion = clase.getSimpleName();
        if (accionesCreadas.containsKey(identAccion)) {
            return (AccionSincrona) accionesCreadas.get(identAccion);
        }
        AccionSincrona accion = (AccionAsincrona) clase.newInstance();
        accion.inicializar(identPropietario, envioInputs);
        accion.setComunicator(comunicator);
        accion.setIdentAccion(accion.getClass().getSimpleName());
        accionesCreadas.put(identAccion, accion);
        accion.setIdentAccion(identAccion);
        log.debug("Accion creada:" + clase.getName());
        return accion;
    }

    public AccionesSemanticasAgenteReactivo getInstanceASagteReactivo(Class accionClass) {
        if (accionesSemAgteReactivo == null) {
            identAccion = accionClass.getSimpleName();
        }
        try {
            accionesSemAgteReactivo = (AccionesSemanticasAgenteReactivo) accionClass.newInstance();
            accionesSemAgteReactivo.inicializarAcciones(this.identPropietario, itfControlAgte, envioInputs);
            accionesCreadas.put(identAccion, accionesSemAgteReactivo);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestorAccionesAgteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestorAccionesAgteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accionesSemAgteReactivo;
    }

    @Override
    public void ejecutar(Object... paramsEjecucion) throws Exception {
        if (paramsEjecucion == null) {
            this.trazas.trazar(this.getClass().getSimpleName(), "Error en  la ejecucion de la accion: "
                    + " No se han especificado parametros de ejecucion", InfoTraza.NivelTraza.error);
        }
        Class claseAccionEjecutar = (Class) paramsEjecucion[0];
        if (claseAccionEjecutar == null) {
            this.trazas.trazar(this.getClass().getSimpleName(), "Error en  la ejecucion de la accion: "
                    + " No se ha especificado la clase de las acciones a ejecutar", InfoTraza.NivelTraza.error);
        }
        String superclase = claseAccionEjecutar.getSuperclass().getSimpleName();
        int numparam = paramsEjecucion.length - 1;
        for (int i = 0; i < numparam; i++) {
            paramsEjecucion[i] = paramsEjecucion[i + 1];
        }
        paramsEjecucion[numparam] = null;
        if (superclase.equals(accionSincSimpleName) || superclase.equals(accionesSemAgteReactivoSimpleName)) {
            accionSinc = crearAccionSincrona(claseAccionEjecutar);
            accionSinc.ejecutar(paramsEjecucion);
        } else if (superclase.equals(accionAsincSimpleName)) {
            accionAsinc = (AccionAsincrona) claseAccionEjecutar.newInstance();
            accionAsinc.inicializar(identPropietario, envioInputs);
            accionAsinc.setParams(paramsEjecucion);
            accionAsinc.comenzar();
        } else {
            this.trazas.trazar(this.getClass().getSimpleName(), "Error en  la ejecucion de la accion: " + claseAccionEjecutar.getSimpleName()
                    + " debe extender a AccionSincrona o a AccionAsincrona ", InfoTraza.NivelTraza.error);
        }
    }

    @Override
    public void ejecutarAccion(Class claseAccionEjecutar, Object... paramsEjecucion) throws Exception {
        String identAccion = claseAccionEjecutar.getSimpleName();
        Object accionCreada = accionesCreadas.get(identAccion);
        String superclase = claseAccionEjecutar.getSuperclass().getSimpleName();
        if (superclase.equals(accionSincSimpleName)) {
            if (accionCreada != null) {
                accionSinc = (AccionAsincrona) accionCreada;
            } else {
                accionSinc = crearAccionSincrona(claseAccionEjecutar);
            }
            accionSinc.ejecutar(paramsEjecucion);
        } else if (superclase.equals(accionAsincSimpleName)) {
            accionAsinc = (AccionAsincrona) claseAccionEjecutar.newInstance();
            accionAsinc.inicializar(identPropietario, envioInputs);
            accionAsinc.setParams(paramsEjecucion);
            accionAsinc.comenzar();
        } else {
            this.trazas.trazar(this.getClass().getSimpleName(), "Error en  la ejecucion de la accion: " + claseAccionEjecutar.getSimpleName()
                    + " debe extender a AccionSincrona o a AccionAsincrona ", InfoTraza.NivelTraza.error);
        }
    }

    @Override
    public synchronized void ejecutarMetodo(Class claseAccionEjecutar, String identMetodo, Object[] paramsEjecucion) throws Exception {
        String superclase = claseAccionEjecutar.getSuperclass().getSimpleName();
        if (superclase.equals(accionesSemAgteReactivoSimpleName)) {
            if (accionesSemAgteReactivo == null) {
                accionesSemAgteReactivo = getInstanceASagteReactivo(claseAccionEjecutar);
            }
        }
        Class params[] = {};
        Object paramsObj[] = {};
        if (paramsEjecucion == null || (paramsEjecucion.length == 1 && paramsEjecucion[0] == null)) {
            params = new Class[0];
            paramsObj = new Object[0];
        } else {
            params = new Class[paramsEjecucion.length];
            paramsObj = new Object[paramsEjecucion.length];
            for (int i = 0; (i < paramsEjecucion.length); i++) {
                params[i] = paramsEjecucion[i].getClass();
                paramsObj[i] = paramsEjecucion[i];
            }
        }

        try {
            Method thisMethod = claseAccionEjecutar.getMethod(identMetodo, params);
            thisMethod.invoke(accionesSemAgteReactivo, paramsObj);
        } catch (IllegalAccessException iae) {
            System.out.println("ERROR en los privilegios de acceso (no es publico) para en metodo: " + identMetodo + " de la clase: " + accionesSemAgteReactivo.getClass().getName());
            iae.printStackTrace();
            throw new ExcepcionEjecucionAcciones("AccionesSemanticasImp", "error al ejecutar un metodo" + identMetodo + " de la clase: " + accionesSemAgteReactivo.getClass().getName(),
                    "se ha producido un IlegalAccessIAE");
        } catch (NoSuchMethodError nsme) {
            System.out.println("ERROR (NO EXISTE) al invocar el metodo: " + identMetodo + " en la clase: " + accionesSemAgteReactivo.getClass().getName());
            nsme.printStackTrace();
            throw new ExcepcionEjecucionAcciones("AccionesSemanticasImp", "error al ejecutar un metodo" + identMetodo + " de la clase: " + accionesSemAgteReactivo.getClass().getName(),
                    "El metodo a invocar no existe. Se ha producido una excepcion NoSuchMethodError");
        } catch (NoSuchMethodException nsmee) {
            System.out.println("ERROR (NO EXISTE) al invocar el metodo: " + identMetodo + " en la clase: " + accionesSemAgteReactivo.getClass().getName());
            nsmee.printStackTrace();
            System.out.println("Invocando metodo con parametros de sus superclases correspondientes");
            throw new ExcepcionEjecucionAcciones("AccionesSemanticasImp", "error al ejecutar un metodo" + identMetodo + " de la clase: " + accionesSemAgteReactivo.getClass().getName(),
                    "El metodo a invocar no existe. Se ha producido una excepcion NoSuchMethodError");
            //ejecutarAccionBloqueantePolimorfica(nombre, parametros, 1);
        } catch (InvocationTargetException ite) {
            System.out.println("ERROR en la ejecucion del metodo: " + identMetodo + " en la clase: " + accionesSemAgteReactivo.getClass().getName());
            ite.printStackTrace();
            System.out.println("Excepcion producida en el metodo: ");
            ite.getTargetException().printStackTrace();
            throw new ExcepcionEjecucionAcciones("AccionesSemanticasImp", "error al ejecutar un metodo" + identMetodo + " de la clase: " + accionesSemAgteReactivo.getClass().getName(),
                    "El metodo a invocar no existe. Se ha producido una excepcion InvocationTargetException");
        }
    }

    @Override
    public synchronized void ejecutarMetodoThread(Class claseAccionEjecutar, String identMetodo, Object... paramsEjecucion) throws Exception {
        throw new ExcepcionEjecucionAcciones("GestorAccionesImp", "error al ejecutar un metodo" + identMetodo + " de la clase: " + accionesSemAgteReactivo.getClass().getName(),
                "El metodo no esta iplementado. Se ha producido una excepcion InvocationTargetException");
    }
}
