package icaro.aplicaciones.informacion.gestionCitas ;

import java.util.Vector;

/**
 * <p>Title: Cita2</p>
 * <p>Description: Clase que contiene una Fecha y una Hora</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Telef�nica I+D</p>
 * @author Sonia Bravo Silva
 * @version 1.0
 */

public class FechaHora implements Comparable, Cloneable, java.io.Serializable{

  public Fecha fecha;
  public Hora hora;

  public FechaHora(Fecha fecha, Hora hora) {
    this.fecha = fecha;
    this.hora = hora;
  }

  public Fecha getFecha() {
    return fecha;
  }
  public void setFecha(Fecha fecha) {
    this.fecha = fecha;
  }
  public void setHora(Hora hora) {
    this.hora = hora;
  }
  public Hora getHora() {
    return hora;
  }

  /**
   *  Implementaci�n del interfaz java.lang.Comparable
   *
   */
  public boolean equals(Object objeto){
    boolean compFechas = false;
    boolean compHoras = false;
    try {
      FechaHora fH = (FechaHora) objeto;
      Fecha f = fH.getFecha();
      Hora h = fH.getHora();
      if ( f != null && this.fecha != null )
        compFechas = this.fecha.equals(f);
      else if ( f == null && this.fecha == null ){
        compFechas = true;
      }
      if ( h != null && this.hora != null )
        compHoras = this.hora.equals(h);
      else if ( h == null && this.hora == null ){
        compHoras = true;
      }
      return (compFechas && compHoras);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  /**
   *  Implementaci�n del interfaz java.lang.Comparable
   *
   *@param  obj  fecha con la que comparar
   *@return      -1 si la fechaHora es menor, 0 si son iguales y +1 si es mayor
   */
  public int compareTo(Object objeto){
    FechaHora fH = (FechaHora) objeto;
    int compararFechas = this.fecha.compareTo(fH.getFecha());
    if ( compararFechas == 0 )
    // Las fechas son iguales
      return this.hora.compareTo(fH.getHora());
    else
      return compararFechas;
  }

  /**
   *  Clona el objeto actual
   *
   *  @return    objeto clonado
   */
  public Object clone() {
      return new FechaHora((Fecha)this.fecha.clone(), (Hora)this.hora.clone());
  }

  public String expresar(){
    StringBuffer sb = new StringBuffer();
    String fechaExpresar = this.fecha.expresarFechaModoUsuario();
    sb.append(fechaExpresar);

    // Expresamos la hora
    sb.append(" a ").append(this.hora.expresar());
    return sb.toString();
  }

  /**
   * Intenta completar la fecha y la hora
   */
  public void completar(){
    if ( this.hora != null )
      if ( !this.hora.estaIndefinida() )
        this.hora.completar();
    if ( this.fecha != null )
      if ( !this.fecha.estaIndefinida() )
        this.fecha.completar();
  }

  public String toString(){
    StringBuffer sb = new StringBuffer("Fecha Hora\n");
    sb.append("Fecha -> ");
    if ( this.fecha != null )
      sb.append(this.fecha.toString()).append("\n");
    else
      sb.append("indefinido\n");
    sb.append("Hora -> ");
    if ( this.hora != null )
      sb.append(this.hora.toString()).append("\n");
    else
      sb.append("indefinido\n");
    return sb.toString();
  }

  /**
   * M�todo que intenta completar una FechaHora primero con la lista de FechaHora
   * y luego mediante sentido com�n
   * @param listaSugerencias
   */
  public void completar( Vector listaSugerencias ){
    Vector listaFechas = extraerListaFechas(listaSugerencias);
    Vector listaHoras = extraerListaHoras(listaSugerencias);
    if ( this.fecha == null )
    // Si la fecha es indefinida creamos una fecha con todos los campos indefinidos
    // para intentar completar con la lista
      this.fecha = new Fecha();
    this.fecha.completar(listaFechas);
    if ( this.hora == null )
    // Si la hora es indefinida creamos una hora con todos los campos indefinidos
    // para intentar completar con la lista
      this.hora = new Hora();
    this.hora.completar(listaHoras);
    if ( this.hora.hora == Utilidades.int_INDEFINIDO )
    // Las horas son indefinidas y por tanto, debemos dar la hora como indefinida
      this.hora = null;
    if ( this.fecha.dia == Utilidades.int_INDEFINIDO )
    // El dia es indefinido y por tanto, debemos dar la fecha como indefinida
      this.fecha = null;
  }

  /**
   * M�todo que devuelve una lista de Fecha extrayendo las fechas de la lista
   * de FechaHora pasada como par�metro
   * @param listaFechaHora
   * @return Lista de Fecha
   */
  private Vector extraerListaFechas( Vector listaFechaHora ){
    Vector resultado = new Vector();
    for ( int i = 0; i < listaFechaHora.size(); i++ ){
      resultado.add(((FechaHora) listaFechaHora.elementAt(i)).getFecha());
    }
    return resultado;
  }

  /**
   * M�todo que devuelve una lista de Hora extrayendo las horas de la lista
   * de FechaHora pasada como par�metro
   * @param listaFechaHora
   * @return Lista de Fecha
   */
  private Vector extraerListaHoras( Vector listaFechaHora ){
    Vector resultado = new Vector();
    for ( int i = 0; i < listaFechaHora.size(); i++ ){
      resultado.add(((FechaHora) listaFechaHora.elementAt(i)).getHora());
    }
    return resultado;
  }
}