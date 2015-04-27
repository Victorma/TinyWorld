package icaro.aplicaciones.recursos.comunicacionUnity;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.recursos.comunicacionChat.imp.InterpreteMsgsUnity;
import icaro.aplicaciones.recursos.comunicacionChat.imp.util.ConexionUnity;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

import java.rmi.RemoteException;

public class ClaseGeneradoraComunicacionUnity extends ImplRecursoSimple implements ItfUsoComunicacionUnity {

    private String url;
    private String port;
    private String identExtractorSem;

    private ConexionUnity comunicChat;
    private InterpreteMsgsUnity interpreteMsgUnity;

    public ClaseGeneradoraComunicacionUnity(String idRecurso) throws RemoteException {
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

    @Override
    public void comenzar(String identAgteControlador) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean conectar(String url, String canal, String nick) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void enviarMensageCanal(String mensaje) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void enviarMensagePrivado(String mensaje) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void desconectar() throws Exception {
        // TODO Auto-generated method stub

    }

}
