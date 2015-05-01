package icaro.infraestructura.patronAgenteReactivo.control.acciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;

public abstract class AccionAsincrona extends AccionSincrona implements Runnable {

    Thread tareaActiva;

    public AccionAsincrona() {
        this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;

    }

    public boolean terminada() {
        return terminada;
    }

    public void comenzar() {
        tareaActiva = new Thread(this);
        tareaActiva.setDaemon(true);
        tareaActiva.setName(identAccion);
        tareaActiva.start();
    }

    @Override
    public void run() {
        this.ejecutar(params);
        terminada = true;
    }
}
