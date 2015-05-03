package icaro.aplicaciones.recursos.comunicacionChat;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;


public interface ItfUsoComunicacionChat extends ItfUsoRecursoSimple {
    public void comenzar ( String identAgteControlador) throws Exception;
	public Boolean conectar( String url, String canal, String nick) throws Exception;
    public void enviarMensaje( String identAgenteOrigen, String mensaje ) throws Exception;
    public void desconectar( ) throws Exception;
        
}