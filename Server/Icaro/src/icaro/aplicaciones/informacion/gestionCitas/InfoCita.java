package icaro.aplicaciones.informacion.gestionCitas ;

import java.io.*;

/**
 * 
 */

public class InfoCita implements Serializable {

  /**
   * Posibles valores del atributo motivo
   */
  public final static int MOTIVO_PRIVADO                =1;
  public final static int MOTIVO_DISCUSION_PROYECTO     =2;
  public final static int MOTIVO_PRESENTACION_PROYECTO  =3;

  /**
   * Posibles valores del atributo tipo
   */
  public final static int TIPO_POSIBLE_CITA     =1;
  public final static int TIPO_PETICION_CITA    =2;
  public final static int TIPO_SUGERENCIA_CITA  =3;
  public final static int TIPO_INTENCION_UDC    =4;

  /**
   * Posibles valores del atributo operaci�n
   */
  public final static int OPERACION_INDEFINIDA       =-1;
  public final static int OPERACION_CREAR       =1;
  public final static int OPERACION_CANCELAR    =2;
  public final static int OPERACION_MODIFICAR   =3;

  /**
   * Tipo de la cita: posible, peticion, sugerencia, intecion
   */
  private int tipo ;

  /**
   * Motivo de la cita
   */
  private int motivo ;

  /**
   * Operaci�n que deseamos realizar
   */
  private int operacion ;

  /**
   * Atributos que caracterizan la fecha y hora de la cita
   */
  private Fecha fecha;
  private Hora hora;
  private Fecha fechaNueva;
  private Hora horaNueva;


  /**
   * Informacin del UDC Usuario del sistema de Citas que pide la cita
   */
  private InfoUsuarioqPideCita infoUDC;

  /**
   * Informacion del UPA la persona  con la que el UDC desea establecer la cita
   */
  private InfoPersonaqDaCitas infoUPA;


  public InfoCita() {
  }

  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
  public void setFecha(Fecha fecha) {
    this.fecha = fecha;
  }
  public Fecha getFecha() {
    return fecha;
  }
  public void setHora(Hora hora) {
    this.hora = hora;
  }
  public Hora getHora() {
    return hora;
  }
  public void setFechaNueva(Fecha fechaNueva) {
    this.fechaNueva = fechaNueva;
  }
  public Fecha getFechaNueva() {
    return fechaNueva;
  }
  public void setHoraNueva(Hora horaNueva) {
    this.horaNueva = horaNueva;
  }
  public Hora getHoraNueva() {
    return horaNueva;
  }
  public void setInfoUDC(InfoUsuarioqPideCita infoUDC) {
    this.infoUDC = infoUDC;
  }
  public InfoUsuarioqPideCita getInfoUDC() {
    return infoUDC;
  }
  public void setInfoUPA(InfoPersonaqDaCitas infoUPA) {
    this.infoUPA = infoUPA;
  }
  public InfoPersonaqDaCitas getInfoUPA() {
    return infoUPA;
  }
  public void setTipo(int tipo) {
    this.tipo = tipo;
  }
  public int getTipo() {
    return tipo;
  }
  public void setMotivo(int motivo) {
    this.motivo = motivo;
  }
  public int getMotivo() {
    return motivo;
  }
  public void setOperacion(int operacion) {
    this.operacion = operacion;
  }
  public int getOperacion() {
    return operacion;
  }

  /**
   * Clasifica el UPA
   * @return true si upa tiene alg�n atributo definido
   */
  public boolean estaUPADefinido()
  {
    return (this.infoUPA != null);
  }

  /**
   * Clasifica el UDC
   * @return true si upa tiene alg�n atributo definido
   */
  public boolean estaUDCDefinido()
  {
    return (this.infoUDC != null);
  }

  /**
   * Clasifica el OP
   * @return true si upa tiene algun atributo definido
   */
  public boolean estaOPDefinido()
  {
    return (this.operacion != this.OPERACION_INDEFINIDA);
  }

  public boolean estaFechaDefinida(){
    return (this.fecha != null);
  }

  public boolean estaHoraDefinida(){
    return (this.hora != null);
  }

  /**
   * Clasifica el PARAM
   * @return true si upa tiene alg�n atributo definido
   */
  public boolean estaPARAMDefinido()
  {
    return ( (this.fecha != null) ||
             (this.fechaNueva != null)||
             (this.hora != null)||
             (this.horaNueva != null) ||
             (this.motivo != Utilidades.int_INDEFINIDO));
  }

  /**
   * Comprueba si est�n todos los atributos PARAM indefinidos
   * @return true si todos los PARAM est�n indefinidos
   */
  public boolean estaPARAMIndefinido()
  {
    if (this.operacion == this.OPERACION_CREAR )
      return ( (this.fecha == null) &&
               (this.hora == null) &&
               (this.motivo == Utilidades.int_INDEFINIDO));
    else if ( this.operacion == this.OPERACION_MODIFICAR )
      return ( (this.fecha == null) &&
               (this.fechaNueva == null) &&
               (this.hora == null) &&
               (this.horaNueva == null) &&
               (this.motivo == Utilidades.int_INDEFINIDO));
    else
      return false;
  }

  /**
   * Comprueba si estan todos los atributos PARAM definidos
   * @return true si todos los PARAM est�n definidos
   */
  public boolean estaPARAMTotalmenteDefinido()
  {
    if ( this.operacion == this.OPERACION_CREAR )
      return ( (this.fecha != null) &&
               (this.hora != null) &&
               (this.motivo != Utilidades.int_INDEFINIDO));
    else
      return false;
  }

  /**
   * Intenta completar la fecha y la hora
   */
  public void completarFechaHora(){
    if ( this.hora != null )
      if ( !this.hora.estaIndefinida() )
        this.hora.completar();
    if ( this.horaNueva != null )
      if ( !this.horaNueva.estaIndefinida() )
        this.horaNueva.completar();
    if ( this.fecha != null )
      if ( !this.fecha.estaIndefinida() )
        this.fecha.completar();
    if ( this.fechaNueva != null )
      if ( !this.fechaNueva.estaIndefinida() )
        this.fechaNueva.completar();
  }

  /**
   *
   */
  public String toString(){
    StringBuffer sb = new StringBuffer("CITA:\n");
    sb.append("Operacion -> ");
    switch (this.operacion){
      case OPERACION_INDEFINIDA:
        sb.append("indefinida\n");
        break;
      case OPERACION_CREAR:
        sb.append("crear\n");
        break;
      case OPERACION_MODIFICAR:
        sb.append("modificar\n");
        break;
      case OPERACION_CANCELAR:
        sb.append("cancelar\n");
        break;
    }
    sb.append("UDC -> ");
    if ( this.infoUDC != null )
      sb.append(this.infoUDC.toString()).append("\n");
    else
      sb.append("indefinido\n");
    sb.append("UPA -> ");
    if ( this.infoUPA != null )
      sb.append(this.infoUPA.toString()).append("\n");
    else
      sb.append("indefinido\n");
    sb.append("Fecha -> ");
    if ( this.fecha != null )
      sb.append(this.fecha.toString()).append("\n");
    else
      sb.append("indefinido\n");
    sb.append("Fecha Nueva -> ");
    if ( this.fechaNueva != null )
      sb.append(this.fechaNueva.toString()).append("\n");
    else
      sb.append("indefinido\n");
    sb.append("Hora -> ");
    if ( this.hora != null )
      sb.append(this.hora.toString()).append("\n");
    else
      sb.append("indefinido\n");
    sb.append("Hora Nueva -> ");
    if ( this.horaNueva != null )
      sb.append(this.horaNueva.toString()).append("\n");
    else
      sb.append("indefinido\n");
    sb.append("Motivo -> ");
    switch (this.motivo){
      case MOTIVO_DISCUSION_PROYECTO:
        sb.append("discusion proyecto\n");
        break;
      case MOTIVO_PRESENTACION_PROYECTO:
        sb.append("presentacion proyecto\n");
        break;
      case MOTIVO_PRIVADO:
        sb.append("privado\n");
        break;
      default:
        sb.append("desconocido\n");
        break;
    }
    sb.append("Tipo -> ");
    switch (this.tipo){
      case TIPO_INTENCION_UDC:
        sb.append("intencion UDC\n");
        break;
      case TIPO_PETICION_CITA:
        sb.append("peticion cita\n");
        break;
      case TIPO_POSIBLE_CITA:
        sb.append("posible cita\n");
        break;
      case TIPO_SUGERENCIA_CITA:
        sb.append("sugerencia cita\n");
        break;
      default:
        sb.append("indefinido\n");
        break;
    }
    return sb.toString();
  }

  /**
   * Metodo que hace una union machacante entre la cita actual y la cita pasada
   * como parametro pero solo con los parametros
   * @param cita
   * @return Cita  Cita resultado de la uni�n machacante
   */
  public InfoCita unionMachacantePARAM( InfoCita cita ){
    InfoCita nuevaCita = new InfoCita();
    nuevaCita.setOperacion(this.operacion);
    nuevaCita.setInfoUDC(this.infoUDC);
    nuevaCita.setInfoUPA(this.infoUPA);
    nuevaCita.setTipo(this.tipo);
    if ( cita.getFecha() != null )
      nuevaCita.setFecha(cita.getFecha());
    else
      nuevaCita.setFecha(this.fecha);
    if ( cita.getFechaNueva() != null )
      nuevaCita.setFechaNueva(cita.getFechaNueva());
    else
      nuevaCita.setFechaNueva(this.fechaNueva);
    if ( cita.getHora() != null )
      nuevaCita.setHora(cita.getHora());
    else
      nuevaCita.setHora(this.hora);
    if ( cita.getHoraNueva() != null )
      nuevaCita.setHoraNueva(cita.getHoraNueva());
    else
      nuevaCita.setHoraNueva(this.horaNueva);
    if ( cita.getMotivo() != Utilidades.int_INDEFINIDO )
      nuevaCita.setMotivo(cita.getMotivo());
    else
      nuevaCita.setMotivo(this.motivo);
    return nuevaCita;
  }

  /**
   * M�todo que devuelve true si los par�metros de la cita pasada como par�metro
   * son iguales a los de la cita actual y false e.o.c.
   * @param motivo
   * @return boolean
   */
  public boolean paramIguales( InfoCita cita ){
    boolean paramIguales = false;
    boolean fechasIguales;
    boolean horasIguales;
    boolean motivosIguales;
    // Comprobamos si las fechas son iguales
    if ( this.fecha != null && cita.getFecha() != null )
      fechasIguales = this.fecha.equals(cita.getFecha());
    else if ( this.fecha == null && cita.getFecha() == null )
      fechasIguales = true;
    else
      fechasIguales = false;
    // Comprobamos si las horas son iguales
    if ( this.hora != null && cita.getHora() != null )
      horasIguales = this.hora.equals(cita.getHora());
    else if ( this.hora == null && cita.getHora() == null )
      horasIguales = true;
    else
      horasIguales = false;
    // Comprobamos si los motivos son iguales
    motivosIguales = (this.motivo == cita.getMotivo());
    if ( this.operacion == this.OPERACION_CREAR ){
      paramIguales = fechasIguales && horasIguales && motivosIguales;
    }
    return paramIguales;
  }

  public static String expresarMotivo( int motivo ){
    StringBuffer sb = new StringBuffer();
    switch (motivo){
      case MOTIVO_DISCUSION_PROYECTO:
        sb.append("para discutir acerca de un proyecto");
        break;
      case MOTIVO_PRESENTACION_PROYECTO:
        sb.append("para hablar sobre la presentaci�n de un proyecto");
        break;
      case MOTIVO_PRIVADO:
        sb.append("por motivos personales");
        break;
      default:
        sb.append("por motivos desconocidos");
        break;
    }
    return sb.toString();
  }

}