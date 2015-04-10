package icaro.aplicaciones.recursos.comunicacionUnity;

import icaro.aplicaciones.recursos.extractorSemantico.*;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;


public interface ItfUsoComunicacionUnity extends ItfUsoRecursoSimple {
    public void comenzar ( String identAgteControlador)throws Exception;;
	public Boolean conectar( String url, String canal, String nick)throws Exception;
//        public Boolean unirseAlcanal(String idCanal)throws Exception;
//        public Boolean cabiarNick(String nickname);
//        public Boolean enviarMensage(String idReceptor, String mensaje);
        public void enviarMensageCanal( String mensaje)throws Exception;
        public void enviarMensagePrivado( String mensaje)throws Exception;
        public void desconectar( )throws Exception;
        
}