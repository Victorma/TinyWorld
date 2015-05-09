package icaro.aplicaciones.informacion.gestionCitas ;

/**
 * 
 * @version 1.0
 */

public class InfoPersonaqDaCitas extends InfoUsuarioqPideCita{

  public InfoPersonaqDaCitas() {
  }


  protected int cargo = Utilidades.int_INDEFINIDO;

  /**
   * Posiblecas cargos
   */
  public final static int CARGO_PRESIDENTE          = 1;
  public final static int CARGO_CONSEJERO_DELEGADO  = 2;
  public final static int CARGO_DIRECTOR_GENERAL    = 3;
  public final static int CARGO_DIRECTOR            = 4;
  public final static int CARGO_GERENTE             = 5;
  public final static int CARGO_JEFE_DIVISION       = 6;
  public final static int CARGO_SUBDIRECTOR         = 7;

  public int getCargo() {
    return cargo;
  }
  public void setCargo(int cargo) {
    this.cargo = cargo;
  }

  public String toString(){
    return super.toString() + " [ Cargo: " + this.cargoToString(this.cargo)+" ]\n";
  }

  public String cargoToString(int cargo){

    switch(cargo){
      case CARGO_PRESIDENTE         : return "Presidente";
      case CARGO_CONSEJERO_DELEGADO : return "Consejero delegado";
      case CARGO_DIRECTOR_GENERAL   : return "Director general";
      case CARGO_DIRECTOR           : return "Director";
      case CARGO_GERENTE            : return "Gerente";
      case CARGO_JEFE_DIVISION      : return "Jefe de divisi�n";
      default                       : return "";
    }

  }

  /**
   * Metodo que transforma un String que contiene el cargo en un entero
   * @param cargo     String que contiene el cargo
   * @return int      Entero que es igual a una de las constantes del cargo
   */
  public int cargoToInt(String cargo){
    this.cargo = Utilidades.int_INDEFINIDO;
    if ( cargo.equals("consejero delegado") )
      this.cargo = this.CARGO_CONSEJERO_DELEGADO;
    else if ( cargo.equals("director general") )
      this.cargo = this.CARGO_DIRECTOR_GENERAL;
    else if ( cargo.equals("director") )
      this.cargo = this.CARGO_DIRECTOR;
    else if ( cargo.equals("subdirector") )
      this.cargo = this.CARGO_SUBDIRECTOR;
    else if ( cargo.equals("gerente") )
      this.cargo = this.CARGO_GERENTE;
    else if ( cargo.equals("jefe de division") )
      this.cargo = this.CARGO_JEFE_DIVISION;
    else if ( cargo.equals("presidente") )
      this.cargo = this.CARGO_PRESIDENTE;
    return this.cargo;
  }

  /**
   * Expresa el contenido en lenguaje natural
   * @return
   */
  public String expresar(){
    String cadena="";
    cadena = this.cargoToString(this.cargo)+" "+this.getNombre()+" "+this.getApe1();
    return cadena;
  }

  public boolean equals(Object o)
  {
    try {
      // esta clase hereda de UDC, comparamos padres primero
      //buba
      InfoPersonaqDaCitas upa = (InfoPersonaqDaCitas)o;
      if(
//          Utilidades.compararStrings(this.getNombre(),upa.getNombre()) &&
//          Utilidades.compararStrings(this.getApe1(),upa.getApe1()) &&
//          Utilidades.compararStrings(this.getApe2(),upa.getApe2()) &&
          (this.getCargo() == upa.getCargo())
          )
        return true;
      // else
      return false;
    }
    catch (Exception ex) {
      return false;
    }

  }

}
