package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import java.rmi.RemoteException;

public interface ItfGestionAgenteReactivo extends InterfazGestion {
    public void setGestorAReportar(String nombreGestor) throws RemoteException;
}
