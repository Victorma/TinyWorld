package icaro.aplicaciones.recursos.comunicacionChat;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.recursos.comunicacionChat.imp.InterpreteMsgsUnity;
import icaro.aplicaciones.recursos.comunicacionChat.imp.util.ConexionUnity;
import icaro.aplicaciones.recursos.comunicacionUnity.ConfigInfoComunicacionUnity;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClaseGeneradoraComunicacionChat extends ImplRecursoSimple implements ItfUsoComunicacionChat {

    private String url;
    private String port;
    private String identExtractorSem;

    private boolean conectado = false;

    private ConexionUnity comunicChat;
    private InterpreteMsgsUnity interpreteMsgUnity;

    public ClaseGeneradoraComunicacionChat(String idRecurso) throws RemoteException {
        super(idRecurso);
        url = ConfigInfoComunicacionUnity.urlFeeNode;
        port = ConfigInfoComunicacionUnity.portFeeNode;
        identExtractorSem = VocabularioGestionCitas.IdentRecursoExtractorSemantico;
        try {
            comunicChat = new ConexionUnity();
            interpreteMsgUnity = new InterpreteMsgsUnity(comunicChat);
            comunicChat.setInterpreteMsgs(interpreteMsgUnity);
            trazas.aceptaNuevaTraza(new InfoTraza(this.getId(), "Creando el recurso " + idRecurso, InfoTraza.NivelTraza.debug));

        } catch (Exception e) {
            e.printStackTrace();
            this.trazas.aceptaNuevaTraza(new InfoTraza(id, "Se ha producido un error al crear el extractor semantico  " + e.getMessage()
                    + ": Verificar los parametros de creacion " + "rutas y otros", InfoTraza.NivelTraza.error));
            this.itfAutomata.transita("error");
            throw e;
        }
    }

    private void generarErrorCreacionComponente(String textoMensaje) {
        this.trazas.aceptaNuevaTraza(new InfoTraza(id, "Se ha producido un error al crear el extractor semantico  " + textoMensaje
                + ": Verificar los parametros de creacion ", InfoTraza.NivelTraza.error));
        this.itfAutomata.transita("error");

    }

    @Override
    public void comenzar(String identAgteControlador) throws Exception {
        InterfazUsoAgente itfAgteControlador;
        try {
            itfAgteControlador = (InterfazUsoAgente) this.repoIntfaces.obtenerInterfazUso(identAgteControlador);
            if (itfAgteControlador == null) {
                this.generarErrorCreacionComponente("itfAgteControlador es null");
            } else {
                interpreteMsgUnity.setItfusoAgenteGestorDialogo(itfAgteControlador);
            }
            ItfUsoExtractorSemantico itfExtractorSem = (ItfUsoExtractorSemantico) this.repoIntfaces.obtenerInterfazUso(identExtractorSem);
            if (itfExtractorSem == null) {
                this.generarErrorCreacionComponente("itfExtractorSemantico es null");
            } else {
                interpreteMsgUnity.setItfusoRecExtractorSemantico(itfExtractorSem);
            }
            if (itfExtractorSem == null || itfAgteControlador == null) {
                throw new Exception();
            } else {
                interpreteMsgUnity.setIdentAgenteGestorDialogo(VocabularioGestionCitas.IdentAgenteAplicacionDialogoCitas);
                interpreteMsgUnity.setIdentConexion(VocabularioGestionCitas.IdentConexionAgte);
                conectar("", "", "");
            }
        } catch (Exception ex) {
            Logger.getLogger(ClaseGeneradoraComunicacionChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Boolean conectar(String urlaConectar, String canal, String nick) throws Exception {
        if (conectado) {
            return true;
        } else {
            conectado = false;
        }

        while (!conectado) {
            comunicChat.connect();
            conectado = true;
        }
        return conectado;
    }

    @Override
    public void enviarMensageCanal(String mensaje) throws Exception {
        comunicChat.sendMessage(mensaje);
    }

    @Override
    public void enviarMensagePrivado(String mensaje) throws Exception {
        comunicChat.sendMessage(mensaje);
    }

    @Override
    public void desconectar() throws Exception {
        comunicChat.disconnect();
    }

    @Override
    public void setIdentAgenteAReportar(String identAgte) {
        identAgenteAReportar = identAgte;
        InterfazUsoAgente itfAgteControlador = null;
        try {
            itfAgteControlador = (InterfazUsoAgente) this.repoIntfaces.obtenerInterfazUso(identAgenteAReportar);
        } catch (Exception ex) {
            Logger.getLogger(ClaseGeneradoraComunicacionChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (itfAgteControlador == null) {
            this.generarErrorCreacionComponente("itfAgteAreportar es null");
        } else {
            interpreteMsgUnity.setItfusoAgenteGestorDialogo(itfAgteControlador);
        }
    }

    @Override
    public void termina() {
        try {
            super.termina();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
