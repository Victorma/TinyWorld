package icaro.aplicaciones.informacion.gestionCitas ;

/**
 * 
 * @version 1.0
 */

public class InfoUsuarioqPideCita {

  public InfoUsuarioqPideCita() {
  }
  private String nombre;
  private String ape1;
  private String ape2;
  private InfoConexionUsuario infoconex;
  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public void setApe1(String ape1) {
    this.ape1 = ape1;
  }
  public String getApe1() {
    return ape1;
  }
  public void setApe2(String ape2) {
    this.ape2 = ape2;
  }
  public String getApe2() {
    return ape2;
  }
  public void setInfoConexionUsuario(InfoConexionUsuario conexinfo) {
    this.infoconex = conexinfo;
  }
  public InfoConexionUsuario getInfoConexionUsuario() {
    return infoconex;
  }

  public String toString(){

    StringBuffer sb = new StringBuffer();
    sb.append("[ Nombre: "+ this.nombre);
    sb.append(", Ape1: "+this.ape1);
    sb.append(", Ape2: "+this.ape2+" ]");

    return super.toString() + ", " + sb.toString();



  }

  /**
   * Expresa el contenido en lenguaje natural
   * @return
   */
//  public String expresar(){
//    String cadena="";
//    cadena = this.nombre+" "+this.ape1;
//    return cadena;
//  }

//  public boolean equals(Object o)
//  {
//    try {
//      // esta clase hereda de UDC, comparamos padres primero
//      //buba
//      InfoUsuarioqPideCita udc = (InfoUsuarioqPideCita)o;
////      if(
////          Utilidades.compararStrings(this.getNombre(),udc.getNombre()) &&
////          Utilidades.compararStrings(this.getApe1(),udc.getApe1()) &&
////          Utilidades.compararStrings(this.getApe2(),udc.getApe2())
////          )
////        return true;
//      // else
//      return false;
//    }
//    catch (Exception ex) {
//      return false;
//    }
//  }
}