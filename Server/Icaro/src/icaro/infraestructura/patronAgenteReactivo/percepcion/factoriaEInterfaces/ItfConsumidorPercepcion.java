package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces;

public interface ItfConsumidorPercepcion {
    public Object consumeConTimeout(int tiempoEnMilisegundos) throws ExcepcionSuperadoTiempoLimite;
}
