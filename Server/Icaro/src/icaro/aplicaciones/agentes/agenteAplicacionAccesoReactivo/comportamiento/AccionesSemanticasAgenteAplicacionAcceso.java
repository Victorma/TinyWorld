package icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivo.comportamiento;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoValidada;
import icaro.aplicaciones.recursos.persistenciaAccesoSimple.ItfUsoPersistenciaAccesoSimple;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

/**
 * Clase que modela las acciones que puede realizar el agente de acceso
 *
 * @author FGarijo
 */
public class AccionesSemanticasAgenteAplicacionAcceso extends AccionesSemanticasAgenteReactivo {

    /**
     * @uml.property name="visualizacion" Representa la interfaz de uso del
     * recurso de visualización
     * @uml.associationEnd
     */
    private ItfUsoVisualizadorAcceso visualizacion;
    /**
     * @uml.property name="persistencia1"Representa la interfaz de uso del
     * recurso de persistencia
     * @uml.associationEnd
     */
    private ItfUsoPersistenciaAccesoSimple persistencia1;
    /**
     * @uml.property name="agenteAcceso" Representa la interfaz de uso del
     * agente de acceso
     * @uml.associationEnd
     */
    private ItfUsoAgenteReactivo agenteAcceso;

    /**
     * Acción de inicialización de las actividades del agente. Se obtiene la
     * interfaz del recurso de visualizacion y del recurso de persistencia para
     * utilizarlos cuando haga falta se le ordena que muestre la ventana de
     * acceso
     */
    public void arranque() {

        try {
            visualizacion = (ItfUsoVisualizadorAcceso) itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + "VisualizacionAcceso1");
            persistencia1 = (ItfUsoPersistenciaAccesoSimple) itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + "Persistencia1");
            visualizacion.mostrarVisualizadorAcceso(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Se acaba de mostrar el visualizador", InfoTraza.NivelTraza.debug));
        } catch (Exception ex) {
            try {
                trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "Ha habido un problema al abrir el visualizador"
                        + " de Acceso en accion semantica 'arranque()'",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Comprueba la validez de la informacion introducida por el usuario por
     * medio del recurso de persistencia Se envía al automata un input
     * "usuarioValido" o "usuarioNoValido" según el resultado de la validación
     * para que se ejecute la transición correspondiente al estado en que se
     * encuentra el agente. "
     * infoUsuario.tomaUsuario(),infoUsuario.tomaPassword()" son los parametros
     * que se le pasan a la accion asociada a la transición
     *
     * @param infoUsuario contien la información introducida por el usuario y
     * enviada por el recurso de visualizacion
     */
    public void valida(InfoAccesoSinValidar infoUsuario) {
        boolean ok = false;
        try {
            ok = persistencia1.compruebaUsuario(infoUsuario.tomaUsuario(), infoUsuario.tomaPassword());
            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                    "Comprobando usuario...", InfoTraza.NivelTraza.debug));
        } catch (Exception ex) {
            try {
                this.trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "Ha habido un problema en la Persistencia1 al comprobar el usuario y el password",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if (ok) {
                this.informaraMiAutomata("usuarioValido", infoUsuario.tomaUsuario(), infoUsuario.tomaPassword());
            } else {
                this.informaraMiAutomata("usuarioNoValido", infoUsuario.tomaUsuario(), infoUsuario.tomaPassword());
            }
        } catch (Exception e) {
            try {
                trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "Ha habido un problema enviar el evento usuario Valido/NoValido al agente",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Esta accion es análoga a la anterior pero suponiendo que el agente esta
     * en otro nodo En la actualidad no se utiliza
     *
     * @param infoUsuario
     */
    public void validaConRecRemoto(InfoAccesoSinValidar infoUsuario) {
        boolean ok = false;

//		InfoAccesoSinValidar datos = new InfoAccesoSinValidar(username,password);
        try {
            ItfUsoConfiguracion config = (ItfUsoConfiguracion) itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_USO
                    + NombresPredefinidos.CONFIGURACION);
            String nombreRec = "Persistencia1";
            String identHostRecurso = config.getDescInstanciaRecursoAplicacion(nombreRec).getNodo().getNombreUso();
            ItfUsoPersistenciaAccesoSimple itfUsoRec = (ItfUsoPersistenciaAccesoSimple) AdaptadorRegRMI.getItfUsoRecursoRemoto(identHostRecurso, nombreRec);
            if (itfUsoRec == null)// la intf  es null El recruso no ha sido registrado
            {
                logger.debug("AgenteAcceso: No se puede dar la orden de arranque al recurso " + nombreRec + ". Porque su interfaz es null");
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                        " No se puede dar la orden de arranque al recurso " + nombreRec + ". Porque su interfaz es null",
                        InfoTraza.NivelTraza.debug));
//                                        errorEnArranque = true;
            } else {
                //
                ok = itfUsoRec.compruebaUsuario(infoUsuario.tomaUsuario(), infoUsuario.tomaPassword());
                try {
                    trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                            "Comprobando usuario...", InfoTraza.NivelTraza.debug));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception ex) {
            try {
                trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "Ha habido un problema en la Persistencia1 al comprobar el usuario y el password",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
//			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
//			(NombresPredefinidos.ITF_USO+this.nombreAgente);
//			Object[] datosEnvio = new Object[]{infoUsuario.tomaUsuario(), infoUsuario.tomaPassword()};
            if (ok) {
                this.informaraMiAutomata("usuarioValido", infoUsuario.tomaUsuario(), infoUsuario.tomaPassword());
            } else {
                this.informaraMiAutomata("usuarioNoValido", infoUsuario.tomaUsuario(), infoUsuario.tomaPassword());
            }
        } catch (Exception e) {
            try {
                trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, "Ha habido un problema enviar el evento usuario Valido/NoValido al agente",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Orden al recurso de visualización para que indique al usuario que ha
     * accedido al sistema Despues se le comunica al gestor de agente el fin de
     * actividades pidiendole la terminación
     *
     * @param username
     * @param password
     */
    public void mostrarUsuarioAccede(String username, String password) {
        InfoAccesoValidada dav = new InfoAccesoValidada(username, password);

        try {
            visualizacion.mostrarMensajeInformacion("AccesoCorrecto", "El usuario " +
                    dav.tomaUsuario() + " ha accedido al sistema. \n A partir de aqui deberia continuar la aplicacion.");
            visualizacion.cerrarVisualizadorAcceso();

        } catch (Exception ex) {
            try {
                trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "Ha habido un problema al abrir el visualizador de Acceso",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        pedirTerminacionGestorAgentes();
    }

    /**
     * se ordena al visualizador que le indique al usuario que no ha accedido al
     * sistema porque no esta registrado con esos dator
     *
     * @param username
     * @param password
     */
    public void mostrarUsuarioNoAccede(String username, String password) {
        InfoAccesoSinValidar dav = new InfoAccesoSinValidar(username, password);

        try {

            visualizacion.mostrarMensajeError("Acceso Incorrecto", "El usuario " +
                    dav.tomaUsuario() + " no ha accedido al sistema.");
        } catch (Exception ex) {
            try {
                trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "Ha habido un problema al abrir el visualizador de Acceso",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        // pedirTerminacionGestorAgentes();
    }

    /**
     * Acciones de terminación del agente: se cierran las ventana abiertas de
     * visualización Se obtiene la interfaz propia de gestión y se ejecuta el
     * métido terminar
     */
    public void terminacion() {
        try {
            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
            "Terminando agente: " + NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso1",
                    InfoTraza.NivelTraza.debug));
            try {
                visualizacion.cerrarVisualizadorAcceso();
                ((InterfazGestion) this.itfUsoRepositorio
                        .obtenerInterfaz(NombresPredefinidos.ITF_GESTION
                                + this.nombreAgente))
                        .termina();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        logger.debug("Terminando agente: " + this.nombreAgente);
    }

    @Override
    public void clasificaError() {
        /*
         *A partir de esta funcion se debe decidir si el sistema se puede recuperar del error o no.
         *En este caso la politica es que todos los errores son cri ticos.  
         */
        try {
            agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + this.nombreAgente);
            agenteAcceso.aceptaEvento(new EventoRecAgte("errorIrrecuperable",
                    this.nombreAgente, this.nombreAgente));
        } catch (Exception e) {
            try {
                trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Se le pide al recurso de visualización que cierran las ventanas abiertas
     * y se le envia al Gestor de agentes la petición de terminar todo que ha
     * sido creada por el usuario y enviada a través del recurso de
     * visualización. Se utiliza la clase comunicator que será la encargada de
     * buscar la interfaz del agente receptor, crear un mensaje con la
     * información a enviar y enviarla al agente.
     */
    public void pedirTerminacionGestorAgentes() {
        try {
            visualizacion.cerrarVisualizadorAcceso();
//            this.itfUsoGestorAReportar.aceptaMensaje(
//            new MensajeSimple (new InfoContEvtMsgAgteReactivo("peticion_terminar_todo"),
//            this.nombreAgente,NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
            this.comunicator.enviarInfoAotroAgente("peticion_terminar_todo",
                                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
        } catch (Exception e) {
            logger.error("Error al mandar el evento de terminar_todo", e);
            try {
                this.trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "Error al mandar el evento de terminar_todo",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                this.informaraMiAutomata("error");
            } catch (Exception exc) {
                try {
                    trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                            "Fallo al enviar un evento error.", InfoTraza.NivelTraza.error));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                logger.error("Fallo al enviar un evento error.", exc);
            }
        }
    }
}
