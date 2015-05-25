package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.aplicaciones.informacion.gestionCitas.Notificacion;

public class Explorar extends Subobjetivo {

    public String zone;

    public Explorar(Notificacion notif) {
        super.setgoalId("Explorar");
        this.zone = "norte";
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public boolean esAtomico() {
        return false;
    }

}
