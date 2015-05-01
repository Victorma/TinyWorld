package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import java.rmi.RemoteException;

public abstract class ClaseGeneradoraRecursoTrazas extends ImplRecursoSimple implements ItfUsoRecursoTrazas {

    public ClaseGeneradoraRecursoTrazas(String idRecurso) throws RemoteException {
        super(idRecurso);
    }

    @Override
    public abstract void visualizacionDeTrazas(Boolean opcionTraza);

    private static ClaseGeneradoraRecursoTrazas instance;

    public static ClaseGeneradoraRecursoTrazas instance() throws RemoteException {
        if (instance == null) {
            instance = new RecursoTrazasImp(NombresPredefinidos.RECURSO_TRAZAS);
        }
        return instance;
    }
}
