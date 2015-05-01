package icaro.infraestructura.patronRecursoSimple;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ItfUsoRecursoSimple extends Remote {
    public void setIdentAgenteAReportar(String identAgenteAReportar) throws RemoteException;
}
