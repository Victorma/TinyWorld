package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp;

import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.EjecutorDeAccionesAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import java.util.logging.Level;

public class EjecutorDeAccionesImp extends EjecutorDeAccionesAbstracto {

    protected AccionesSemanticasAgenteReactivo accionesSemanticas;
    protected String accionSemanticaAEjecutar;
    protected Object[] parametros;

    public EjecutorDeAccionesImp(AccionesSemanticasAgenteReactivo accionesSemanticas) {
        super("Acciones semanticas");
        this.accionesSemanticas = accionesSemanticas;
    }

    public synchronized void setAccion(String accion) {
        accionSemanticaAEjecutar = accion;
    }

    public synchronized void setParametros(Object[] parametros) {
        this.parametros = parametros;
    }

    public synchronized AccionesSemanticasAgenteReactivo getAccionesSemanticas() {
        return accionesSemanticas;
    }

    public synchronized void setAccionesSemanticas(AccionesSemanticasAgenteReactivo accionesSemanticas) {
        this.accionesSemanticas = accionesSemanticas;
    }

    @Override
    public synchronized void ejecutarAccion(String accion, Object[] parametros, boolean modoBloqueante) throws ExcepcionEjecucionAcciones {
        if (accion != null) {
            if (modoBloqueante) {
                ejecutarAccionBloqueante(accion, parametros);
            } else {
                this.ejecutarAccionEnNuevaHebra(accion, parametros);
            }
        } else {
            System.out.println("AVISO: Accion Ignorada.La accion a ejecutar era null");
        }
    }

    @Override
    public void run() {
        try {
            ejecutarAccionBloqueante(accionSemanticaAEjecutar, parametros);
        } catch (ExcepcionEjecucionAcciones ex) {
            Logger.getLogger(EjecutorDeAccionesImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected synchronized void ejecutarAccionBloqueante(String nombre, Object[] parametros) throws ExcepcionEjecucionAcciones {
        Class params[] = {};
        Object paramsObj[] = {};

        if (parametros != null && parametros.length > 0) {
            params = new Class[parametros.length];
            paramsObj = new Object[parametros.length];
            for (int i = 0; i < parametros.length; i++) {
                params[i] = parametros[i].getClass();
                paramsObj[i] = parametros[i];
            }
        } else {
            params = new Class[0];
            paramsObj = new Object[0];
        }

        try {
            Class thisClass = accionesSemanticas.getClass();
            Object iClass = accionesSemanticas;
            Method thisMethod = thisClass.getMethod(nombre, params);
            thisMethod.invoke(iClass, paramsObj);
        } catch (IllegalAccessException iae) {
            System.out.println("ERROR en los privilegios de acceso (no es publico) para en metodo: " + nombre + " de la clase: " + accionesSemanticas.getClass().getName());
            iae.printStackTrace();
            throw new ExcepcionEjecucionAcciones("AccionesSemanticasImp", "error al ejecutar un metodo" + nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                    "se ha producido un IlegalAccessIAE");
        } catch (NoSuchMethodError nsme) {
            System.out.println("ERROR (NO EXISTE) al invocar el metodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
            nsme.printStackTrace();
            throw new ExcepcionEjecucionAcciones("AccionesSemanticasImp", "error al ejecutar un metodo" + nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                    "El metodo a invocar no existe. Se ha producido una excepcion NoSuchMethodError");
        } catch (NoSuchMethodException nsmee) {
            System.out.println("ERROR (NO EXISTE) al invocar el metodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
            nsmee.printStackTrace();
            System.out.println("Invocando metodo con parametros de sus superclases correspondientes");
            throw new ExcepcionEjecucionAcciones("AccionesSemanticasImp", "error al ejecutar un metodo" + nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                    "El metodo a invocar no existe. Se ha producido una excepcion NoSuchMethodError");
            //ejecutarAccionBloqueantePolimorfica(nombre, parametros, 1);
        } catch (InvocationTargetException ite) {
            System.out.println("ERROR en la ejecucion del metodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
            ite.printStackTrace();
            System.out.println("Excepcion producida en el metodo: ");
            ite.getTargetException().printStackTrace();
            throw new ExcepcionEjecucionAcciones("AccionesSemanticasImp", "error al ejecutar un metodo" + nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                    "El metodo a invocar no existe. Se ha producido una excepcion InvocationTargetException");
        }
    }

    protected void ejecutarAccionBloqueantePolimorfica(String nombre, Object[] parametros, int nivel) {
        Class params[] = {};
        Object paramsObj[] = {};
        boolean salir = false;

        if (parametros != null && parametros.length > 0) {
            params = new Class[parametros.length];
            paramsObj = new Object[parametros.length];
            for (int i = 0; i < parametros.length; i++) {
                params[i] = parametros[i].getClass();
                for (int j = 0; j < nivel; j++) {
                    if (params[i].getSuperclass() != null) {
                        params[i] = params[i].getSuperclass();
                        System.out.println("\n\nCLASS: " + params[i].toString() + "\n\n");
                        salir = false;
                    } else {
                        salir = true;
                    }
                }
                paramsObj[i] = parametros[i];
            }
        } else {
            params = new Class[0];
            paramsObj = new Object[0];
        }

        try {
            Class thisClass = accionesSemanticas.getClass();
            Object iClass = accionesSemanticas;
            Method thisMethod = thisClass.getMethod(nombre, params);
            thisMethod.invoke(iClass, paramsObj);
        } catch (IllegalAccessException iae) {
            System.out.println("ERROR en los privilegios de acceso (no es pblico) para en mtodo: " + nombre + " de la clase: " + accionesSemanticas.getClass().getName());
            iae.printStackTrace();
        } catch (NoSuchMethodError nsme) {
            System.out.println("ERROR (NO EXISTE) al invocar el mtodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
            nsme.printStackTrace();
        } catch (NoSuchMethodException nsmee) {
            System.out.println("ERROR (NO EXISTE) al invocar el mtodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
            nsmee.printStackTrace();
            System.out.println("Invocando mtodo con parmetros de sus superclases correspondientes");
            if (!salir) {
                ejecutarAccionBloqueantePolimorfica(nombre, parametros, ++nivel);
            }
        } catch (InvocationTargetException ite) {
            System.out.println("ERROR en la ejecucin del mtodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
            ite.printStackTrace();
            System.out.println("Excepcin producida en el mtodo: ");
            ite.getTargetException().printStackTrace();
        }
    }

    protected void ejecutarAccionEnNuevaHebra(String nombre, Object[] parametros) {
        this.setAccion(nombre);
        this.setParametros(parametros);
        this.setDaemon(true);
        this.start();
    }
}
