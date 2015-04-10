package icaro.aplicaciones.informacion.gestionCitas ;

/**
 * 
 * @version 1.0
 */

public class InfoConexionUsuario {

  public InfoConexionUsuario() {
  }
  private String userName;
  private String login;
  private String host;
  public String getuserName() {
    return userName;
  }
  public void setuserName(String usName) {
    this.userName = usName;
  }
  public void setlogin(String loginU) {
    this.login = loginU;
  }
  public String getlogin() {
    return login;
  }
  public void sethost(String hostU) {
    this.host = hostU;
  }
  public String gethost() {
    return host;
  }

  public String toString(){

    StringBuffer sb = new StringBuffer();
    sb.append("[ Usurio: "+ this.userName);
    sb.append(", pasw: "+this.login);
    sb.append(", host: "+this.host+" ]");

    return super.toString() + ", " + sb.toString();



  }

  /**
   * Expresa el contenido en lenguaje natural
   * @return
   */
//  public String expresar(){
//    String cadena="";
//    cadena = this.userName+" "+this.pasw;
//    return cadena;
//  }

//  public boolean equals(Object o)
//  {
//    try {
//      // esta clase hereda de UDC, comparamos padres primero
//      //buba
//      InfoConexionUsuario udc = (InfoConexionUsuario)o;
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