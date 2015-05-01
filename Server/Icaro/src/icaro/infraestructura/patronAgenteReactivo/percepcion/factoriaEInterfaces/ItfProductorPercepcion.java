package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces;

public interface ItfProductorPercepcion {
    public void produce(Object evento);
    public void produceParaConsumirInmediatamente(Object evento);
}
