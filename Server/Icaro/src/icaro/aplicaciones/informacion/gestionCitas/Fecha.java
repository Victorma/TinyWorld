package icaro.aplicaciones.informacion.gestionCitas ;

import java.io.Serializable;
import java.util.*;
import java.text.*;

/**
 * <p>Title: Cita2  - Sistema Vocal de citas para directivos</p>
 * <p>Description: Clase que representa las fechas en el sistema</p>
 * <p>Copyright: Telef�nica I+D Copyright (c) 2002</p>
 * <p>Company: Telef�nica I+D </p>
 * @author Emilio Bobadilla �lvarez, Carlos Delgado Estremera
 * @version 3.0
 */
public class Fecha implements Comparable, Cloneable, java.io.Serializable
{


    /**
     *  Posibles valores del atributo mes
     */

    public final static int ENERO = 1;
    public final static int FEBRERO = 2;
    public final static int MARZO = 3;
    public final static int ABRIL = 4;
    public final static int MAYO = 5;
    public final static int JUNIO = 6;
    public final static int JULIO = 7;
    public final static int AGOSTO = 8;
    public final static int SEPTIEMBRE = 9;
    public final static int OCTUBRE = 10;
    public final static int NOVIEMBRE = 11;
    public final static int DICIEMBRE = 12;

    /**
     * Posibles atributos del atributo modoUsuario
     */
    public final static int MODO_ABSOLUTO = 1;
    public final static int MODO_HOY = 2;
    public final static int MODO_MANIANA = 3;
    public final static int MODO_MES_ACTUAL = 4;
    public final static int MODO_MES_SIGUIENTE = 5;
    public final static int MODO_ANIO_ACTUAL = 6;
    public final static int MODO_ANIO_SIGUIENTE = 7;

    /**
     * Posibles diagn�sticos para la fecha
     */
    public final static int FECHA_VALIDA = 0;        // Fecha valida
    public final static int ERROR_FEBRERO = 1;       // Fecha con mes igual a Febrero
                                                     // y con un valor para dia mayor
                                                     // que 28
    public final static int ERROR_NO_FEBRERO = 2;    // Fecha con mes distinto a
                                                     // Febrero pero de 30 dias y con
                                                     // un valor para dia mayor que 30
    public final static int FECHA_YA_HA_PASADO = 3;  // La fecha ya ha pasado
    public final static int FECHA_FIN_DE_SEMANA = 4; // La fecha pertenece al fin de semana

    /**
     *  Atributo d��
     */
    public int dia = Utilidades.int_INDEFINIDO;
    /**
     * Atributo mes seg�n el enumerado dado al comienzo de la clase
     */
    public int mes = Utilidades.int_INDEFINIDO;
    /**
     *  A�o del 2002 en adelante
     */
    public int anno = Utilidades.int_INDEFINIDO;
    /**
     * Modo en que dijo el usuario la fecha
     */
    public int modoUsuario = Utilidades.int_INDEFINIDO;




    /**
     *  Constructor, crea una fecha con todos los valores indefinidos
     */
    public Fecha() {

    }


    /**
     *  Constructor, crea una fecha con los valores indicados
     *
     *  @param  dia   valor de 1 a 31
     *  @param  mes   valor de ENERO a DICIEMBRE
     *  @param  anno  valor de 4 CIFRAS de 2001 en adelante
     */
    public Fecha(int dia, int mes, int anno) {
        this.dia = dia;
        this.mes = mes;
        this.anno = anno;
    }

    /**
     *  Constructor, crea una fecha con los valores indicados
     *
     *  @param  dia   valor de 1 a 31
     *  @param  mes   valor de ENERO a DICIEMBRE
     *  @param  anno  valor de 4 CIFRAS de 2001 en adelante
     *  @param  modo  modo en que dijo el usuario la fecha
     */
    public Fecha(int dia, int mes, int anno, int modo) {
        this.dia = dia;
        this.mes = mes;
        this.anno = anno;
        this.modoUsuario = modo;
    }

    /**
     *  Clona el objeto actual
     *
     *  @return    objeto clonado
     */
    public Object clone() {
        return new Fecha(this.dia, this.mes, this.anno, this.modoUsuario);
    }


    /**
     *  Implementaci�n del interfaz java.lang.Comparable
     *
     *@param  obj  fecha con la que comparar
     *@return      -1 si la fecha es menor, 0 si son iguales y +1 si es mayor
     */
    public int compareTo(Object obj) {

        Fecha f = (Fecha)obj;
        int res = 0;

        if(this.anno < f.anno)
            res = -1;
        if(this.anno > f.anno)
            res = +1;
        if(this.anno == f.anno) {

            if(this.mes < f.mes)
                res = -1;
            if(this.mes > f.mes)
                res = +1;

            if(this.mes == f.mes) {
                if(this.dia < f.dia)
                    res = -1;
                if(this.dia > f.dia)
                    res = +1;
                if(this.dia == f.dia)
                    res = 0;

            }
        }
        return res;
    }


    /**
     *  Devuelve el sucesor del objeto fecha actual. El sucesor se calcula como
     *  el d�a siguiente a la fecha actual
     *
     *@return    fecha siguiente a fechaActual
     */
    public Fecha sucesor() {

        Fecha sucesor = new Fecha();

        Calendar calendar = Calendar.getInstance(Hora.getSimpleTimeZoneMadrid());
        calendar.clear();
        calendar.set(this.anno, this.mes - 1, this.dia);
        calendar.add(Calendar.DATE, 1);

        sucesor.dia =  calendar.get(Calendar.DAY_OF_MONTH);
        sucesor.mes =  calendar.get(Calendar.MONTH) + 1;
        sucesor.anno = calendar.get(Calendar.YEAR);

        return sucesor;
    }


    /**
     *  Determina si la fecha est� completa, es decir, ninguno de sus
     *  campos est� indefinido
     *
     *  @return    true si la fecha tiene todos sus campos completos
     */
    public boolean estaCompleta() {
        if(this.dia != Utilidades.int_INDEFINIDO &
           this.mes != Utilidades.int_INDEFINIDO &
           this.anno != Utilidades.int_INDEFINIDO)
          return true;

        // else
        return false;
    }

    /**
     * M�todo que comprueba si la fecha est� indefinida, es decir, todos sus campos
     * est�n indefinidos
     * return true si la Fecha tiene todos los atributos a indefinido y false e.o.c.
     *
     */
     public boolean estaIndefinida(){
        if( this.dia == Utilidades.int_INDEFINIDO &&
            this.mes == Utilidades.int_INDEFINIDO &&
            this.anno == Utilidades.int_INDEFINIDO  )
          return true;
        else
          return false;
    }
    /**
     *  Devuelve la fecha actual del sistema como un objeto de la clase fecha
     *
     *@return    fecha actual
     */
    public static Fecha getFechaActual() {
        Calendar cal = Calendar.getInstance(Hora.getSimpleTimeZoneMadrid());
        Fecha res = new Fecha();

        res.mes = cal.get(Calendar.MONTH) + 1;
        res.dia = cal.get(Calendar.DAY_OF_MONTH);
        res.anno = cal.get(Calendar.YEAR);

        return res;
    }

    /**
     * Metodo que comprueba si una fecha es valida
     *
     * @param fecha Fecha que se desea validar
     * @return int Entero que se correponde con:
     *              0 -> fecha valida
     *              1 -> fecha con mes igual a Febrero y dia mayor que 28
     *              2 -> fecha con mes distinto a Febrero y con 30 dias y valor
     *                   para dia mayor que 30
     */
    public int esFechaValida(){

      int fechaCorrecta = this.FECHA_VALIDA;

      // Si el mes es Febrero, el dia debe ser menor o igual que 28
      if( ( this.mes == Fecha.FEBRERO ) && ( this.dia > 28 ) ){
        fechaCorrecta = this.ERROR_FEBRERO;
      }
      else{
      // Mes distinto de Febrero. Hay que tener en cuenta que:
        // Caso 1: Meses pares excepto Febrero tiene 30 dias hasta Junio
        // Caso 2: Meses pares tienen 31 dias a partir de Junio
        // Caso 3: Meses impares tienen 31 dias hasta Julio
        // Caso 4: Meses impares tienen 30 dias a partir de Julio
        // Como dia puede tomar un valor entre 1 y 31, solo me ocupar� de los meses
        // que tienen 30 dias ( Caso 1 y Caso 4 )

        // Caso 1
        if ( ( this.mes % 2 ) == 0 && ( this.mes <= 6 ) && ( this.dia > 30 ) )
          fechaCorrecta = this.ERROR_NO_FEBRERO;
        // Caso 4
        if ( ( this.mes % 2 ) == 1 && ( this.mes > 7 ) && ( this.dia > 30 ) )
          fechaCorrecta = this.ERROR_NO_FEBRERO;
      }

      return fechaCorrecta;
   }

   /**
    * Metodo que comprueba que la fecha es igual o posterior a la fecha
    * actual.
    *
    * @param fechaInicio fecha de inicio
    * @return boolean Devuelve true si la fecha de inicio es mayor o igual que la
    *                 fecha actual y false en caso contrario
    */
    public boolean fechaEnCalendario(){
      boolean fechaEnCalendario = true;

      if ( this != null ){
        // Obtengo la fecha actual
        Fecha fechaActual = Fecha.getFechaActual();
        int comp = this.compareTo( fechaActual );
        // La fecha es mayor o igual que la actual si el resultado de la
        // comparacion es 0 � 1
        fechaEnCalendario = comp >= 0;
      }
      return fechaEnCalendario;
    }

    /**
     * Comprueba si la fecha se corresponde con un d�a de fin de semana, esto es,
     * s�bado o domingo
     * @param fecha
     * @return boolean   Ser� igual a true si es un dia de fin de semana y false e.o.c.
     */
    public boolean esFinDeSemana(){
      Calendar calendar = Calendar.getInstance(Hora.getSimpleTimeZoneMadrid());
      calendar.clear();
      calendar.set(this.anno, this.mes - 1, this.dia);
      if ( (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) ||
           (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ) )
        return true;
      else
        return false;
    }


    /**
     *  Comleta la fecha con los campos que no est�n indefinidos de una fecha
     *  nueva
     *
     *@param  fechaNueva  fecha con la que completar la fecha actual
     */
    public void completar(Fecha fechaNueva) {

        if(fechaNueva != null) {

            if(fechaNueva.dia != Utilidades.int_INDEFINIDO)
                this.dia = fechaNueva.dia;

            if(fechaNueva.mes != Utilidades.int_INDEFINIDO)
                this.mes = fechaNueva.mes;

            if(fechaNueva.anno != Utilidades.int_INDEFINIDO)
                this.anno = fechaNueva.anno;
        }
    }

    /**
     * Completa la fecha con sentido com�n
     * @return
     */
    public void completar(){
      Fecha fechaActual = getFechaActual();
      if ( !estaIndefinida() ){
        if ( this.dia != Utilidades.int_INDEFINIDO ){
          if ( this.mes == Utilidades.int_INDEFINIDO ){
            // Si el mes est� indefinido y el dia es posterior o igual al de la
            // fecha actual, suponemos el mes actual. Si no, suponemos el mes
            // siguiente
            if ( this.dia >= fechaActual.dia )
              this.mes = fechaActual.mes;
            else
              this.mes = fechaActual.mes + 1;
          }
          if ( this.anno == Utilidades.int_INDEFINIDO ){
          // A�o indefinido
            // Si el dia y mes no ha pasado, suponemos el a�o actual. En caso
            // contrario, suponemos el a�o siguiente
            if ( this.mes > fechaActual.mes )
              this.anno = fechaActual.anno;
            else if ( this.mes == fechaActual.mes )
              if ( this.dia >= fechaActual.dia )
                this.anno = fechaActual.anno;
              else
                this.anno = fechaActual.anno + 1;
            else
              this.anno = fechaActual.anno + 1;
          }
        }
      }
    }


    /**
     *  Devuelve la fecha actual como cadena de texto
     *
     *  @return    String cadena con la fecha
     */
    public String toString() {

        String Cdia = String.valueOf(this.dia);
        String Cmes = String.valueOf(this.mes);
        String Canno = String.valueOf(this.anno);

        if(this.dia == Utilidades.int_INDEFINIDO)
            Cdia = "INDEF";
        if(this.mes == Utilidades.int_INDEFINIDO)
            Cmes = "INDEF";
        if(this.anno == Utilidades.int_INDEFINIDO)
            Canno = "INDEF";

        return "(FECHA: " + Cdia + "-" + Cmes + "-" + Canno + ")";

    }

    /**
     *  Expresa una instancia de Fecha en lenguaje natural
     *
     *@return    Description of the Return Value
     */
    public String expresarFecha() {
      StringBuffer sb = new StringBuffer();

      if ( this.dia != Utilidades.int_INDEFINIDO )
        sb.append(this.dia);
      if ( this.mes != Utilidades.int_INDEFINIDO )
        sb.append(" de ").append(mesToString());
      if ( this.anno != Utilidades.int_INDEFINIDO ){
        int anioNuevo = this.anno;
        String anio = Integer.toString(this.anno);
        if ( anio.length() == 2 ){
        // Lo convierto en un n�mero de 4 d�gitos
          if ( anioNuevo >= 90 )
            anioNuevo = anioNuevo + 1900;
          else
            anioNuevo = anioNuevo + 2000;
        }
        sb.append(" de ").append(anioNuevo);
      }
      return sb.toString();
    }

    /**
     *  Expresa una instancia de Fecha en lenguaje natural de acuerdo al modo
     *  dicho por el usuario
     *
     *@return    Description of the Return Value
     */
    public String expresarFechaModoUsuario() {
      StringBuffer sb = new StringBuffer();
      if ( this.modoUsuario == Utilidades.int_INDEFINIDO ){
      // No se ha fijado un modo de usuario a la hora de expresarla
        if ( equals(Fecha.getFechaActual()) )
          // Expresamos como 'hoy'
          sb.append("hoy");
        else if ( equals(Fecha.getFechaActual().sucesor()) )
          // Expresamos como ma�ana
          sb.append("ma�ana");
      }
      if ( this.modoUsuario == this.MODO_HOY )
      // El usuario dijo 'hoy'
        sb.append("hoy");
      else if ( this.modoUsuario == this.MODO_MANIANA )
      // El usuario dijo 'ma�ana'
        sb.append("ma�ana");
      else if ( this.modoUsuario == this.MODO_ABSOLUTO ||
                (this.modoUsuario == Utilidades.int_INDEFINIDO &&
                 sb.length() == 0) ){
      // Debemos expresar la fecha en la forma dd/mm/aaaa si la fecha ha sido
      // dada en modo absoluto, o en caso de no tener ning�n modo asociado, la
      // cadena que contiene la fecha expresada no tiene a�n ning�n valor almacenado
        if ( this.dia != Utilidades.int_INDEFINIDO )
          sb.append("el dia ").append(this.dia);
        if ( this.modoUsuario == this.MODO_MES_ACTUAL )
          sb.append("de este mes");
        else if ( this.modoUsuario == this.MODO_MES_SIGUIENTE )
          sb.append("del mes que viene");
        else
          if ( this.mes != Utilidades.int_INDEFINIDO )
            sb.append(" de ").append(mesToString());
        if ( this.modoUsuario == this.MODO_ANIO_ACTUAL )
          sb.append("de este a�o");
        else if ( this.modoUsuario == this.MODO_ANIO_SIGUIENTE )
          sb.append("del a�o que viene");
        else{
          if ( this.anno != Utilidades.int_INDEFINIDO ){
            int anioNuevo = this.anno;
            String anio = Integer.toString(this.anno);
            if ( anio.length() == 2 ){
              // Lo convierto en un n�mero de 4 d�gitos
              if ( anioNuevo >= 90 )
                anioNuevo = anioNuevo + 1900;
              else
                anioNuevo = anioNuevo + 2000;
            }
            sb.append(" de ").append(anioNuevo);
          }
        }
      }
      return sb.toString();
    }


    /**
     *  Devuelve el mes actual como cadena de texto
     *
     *@return    String cadena con el mes
     */
    public String mesToString() {

        String Cmes = new String();
        switch (this.mes) {
            case ENERO:
                Cmes = "enero";
                break;
            case FEBRERO:
                Cmes = "febrero";
                break;
            case MARZO:
                Cmes = "marzo";
                break;
            case ABRIL:
                Cmes = "abril";
                break;
            case MAYO:
                Cmes = "mayo";
                break;
            case JUNIO:
                Cmes = "junio";
                break;
            case JULIO:
                Cmes = "julio";
                break;
            case AGOSTO:
                Cmes = "agosto";
                break;
            case SEPTIEMBRE:
                Cmes = "septiembre";
                break;
            case OCTUBRE:
                Cmes = "octubre";
                break;
            case NOVIEMBRE:
                Cmes = "noviembre";
                break;
            case DICIEMBRE:
                Cmes = "diciembre";
                break;
        }
        return Cmes;
    }


    /**
     *  Compara seg�n igualdad campo a campo
     *
     *@param  o  objeto que vamos a comparar
     *@return    true si los objetos son iguales, false e.o.c.
     */
    public boolean equals(Object o) {
        try {
            if ( o != null ){
              Fecha aux = (Fecha)o;
              return (this.anno == aux.anno && this.mes == aux.mes && this.dia == aux.dia);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     *  Devuelve una fecha a partir de un String
     *
     *@param  cadena  String con la fecha
     *@return Sera igual a null si no ha sido posible parsear la
     *        fecha para convertirla en un tipo Date
     */
    public static Fecha stringToFecha(String cadena) {
        Fecha fecha = new Fecha();
        Date d;
        try {
            // Transformo el String en un objeto de la clase Date
            d = formatear(cadena);
        } catch(ParseException pe) {
            System.out.println(pe);
            return null;
        }
        // Creamos un GregorianCalendar con la zona horaria de Madrid
        Calendar cal = new GregorianCalendar(Hora.getSimpleTimeZoneMadrid());
        cal.setTime(d);
        fecha.dia = cal.get(Calendar.DAY_OF_MONTH);
        // Sumo 1 al mes porque los meses comienzan en el 0
        fecha.mes = cal.get(Calendar.MONTH) + 1;
        if(cal.get(Calendar.YEAR) == 1970)
            // Este es el valor por defecto para el a�o cuando no se ha proporcionado
            // el a�o
            fecha.anno = Utilidades.int_INDEFINIDO;
        else
            fecha.anno = cal.get(Calendar.YEAR);
        return fecha;
    }


    /**
     *  Transforma una cadena que representa una fecha en un tipo Date El
     *  formato de la fecha puede ser "dd/MM/yy" o "dd/MM"
     *
     *@param  cadena           String con la fecha
     *@return                  Date Date con la cadena transformada
     *@throws  ParseException  Si la cadena no tiene alguno de los formatos
     *      antes indicados
     */
    private static Date formatear(String cadena) throws ParseException {
        Date d = null;
        // Definimos los formatos
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMM");
        try {
            // Parseamos la fecha con el primer formato
            d = sdf1.parse(cadena);
        } catch(ParseException pe1) {
            // No ha sido posible devolver una fecha segun el primer formato asi que
            // probamos con el segundo
            try {
                d = sdf2.parse(cadena);
            } catch(ParseException pe2) {
                System.out.println("Imposible transformar " + cadena + " en un tipo Date");
                throw pe2;
            }
            return d;
        }
        return d;
    }

    /**
     * M�todo que devuelve el n�mero de campos no indefinidos
     * @return int     N�mero de campos no indefinidos
     */
    public int numCamposDef(){
      int num = 0;
      if ( this.dia != Utilidades.int_INDEFINIDO )
        num++;
      if ( this.mes != Utilidades.int_INDEFINIDO )
        num++;
      if ( this.anno != Utilidades.int_INDEFINIDO )
        num++;
      return num;
    }

    /**
     * M�todo que nos dice si esta fecha es m�s completa que la fecha pasada como
     * par�metro
     * @param fecha     Fecha con la que queremos comparar
     * @return boolean  Devolver� true si esta fecha es m�s completa, esto es, tiene
     *                  un n�mero de campos definidos igual o mayor que la fecha
     *                  pasada como par�metro y false e.o.c.
     */
    public boolean esMasCompleta( Fecha fecha ){
      boolean b = false;
      int numFechaActual = numCamposDef();
      int numFechaParam = fecha.numCamposDef();
      if ( numFechaActual >= numFechaParam )
        b = true;
      return b;
    }

    /**
     *  Test de la clase
     *
     *
     */
    public static void main(String args[]) {
/*        Fecha d;
        d = Fecha.stringToFecha("9/11/01");
        if(d != null)
            System.out.println("9/11/01 -> " + d.toString());
        d = Fecha.stringToFecha("9/6/91");
        if(d != null)
            System.out.println("9/6/91 -> " + d.toString());
        d = Fecha.stringToFecha("19/10/01");
        if(d != null)
            System.out.println("19/10/01 -> " + d.toString());
        d = Fecha.stringToFecha("22/2/2001");
        if(d != null)
            System.out.println("22/2/2001 -> " + d.toString());
        d = Fecha.stringToFecha("19/11/1997");
        if(d != null)
            System.out.println("19/11/1997 -> " + d.toString());
        d = Fecha.stringToFecha("22/11");
        if(d != null)
            System.out.println("22/11 -> " + d.toString());
        d = Fecha.stringToFecha("01/10");
        if(d != null)
            System.out.println("01/10 -> " + d.toString());*/
      Fecha fecha = Fecha.stringToFecha("24022003");
      System.out.println(fecha);
    }

    /**
     * M�todo que intenta completar una fecha primero con la lista de Fecha
     * y luego mediante sentido com�n
     * @param listaSugerencias
     */
    public void completar( Vector listaSugerencias ){
      Fecha fechaCompletada = new Fecha();
      if ( this.dia == Utilidades.int_INDEFINIDO ){
      // Intentamos completar el dia
        int dia = factorComunDia( listaSugerencias );
        fechaCompletada.dia = dia;
      }
      else
        fechaCompletada.dia = this.dia;
      if ( this.mes == Utilidades.int_INDEFINIDO ){
      // Intentamos completar el mes
        int mes = factorComunMes( listaSugerencias );
        fechaCompletada.mes = mes;
      }
      else
        fechaCompletada.mes = this.mes;
      if ( this.anno == Utilidades.int_INDEFINIDO ){
        int anno = factorComunAnio( listaSugerencias );
        fechaCompletada.anno = anno;
      }
      else
        fechaCompletada.anno = this.anno;
      // Comprobamos si la fecha obtenida completando con la lista de sugerencias
      // es una fecha correcta
      if ( fechaCompletada.estaCompleta() ){
        int validacionFecha = fechaCompletada.esFechaValida();
        if ( validacionFecha == this.FECHA_VALIDA ){
        // La fecha es correcta
          if ( !fechaCompletada.fechaEnCalendario() )
          // La fecha es anterior a la fecha actual. Completamos la fecha con sentido com�n
            this.completar();
          else
          // Completamos la fecha con la fecha obtenida a partir de la lista de
          // sugerencias
            this.completar(fechaCompletada);
        }
        else
        // La fecha es incorrecta. Completamos la fecha con sentido com�n
          this.completar();
      }
      else{
      // Por si quedan atributos sin completar, completamos con sentido com�n
        this.completar(fechaCompletada);
        this.completar();
      }

    }

    /**
     * M�todo que comprueba si todas las Fecha de la lista de entrada tienen
     * en su fecha el mismo campo dia
     * Si todas tienen el mismo campo dia devuelve dicho dia y Utilidades.int_INDEFINIDO
     * en caso contrario
     * @param listaSugerencias
     * @return int
     */
    private int factorComunDia( Vector listaSugerencias ){
      int dia = Utilidades.int_INDEFINIDO;
      int diaAux = ((Fecha) listaSugerencias.elementAt(0)).dia;
      boolean todosIguales = true;
      int i = 0;
      while ( i < listaSugerencias.size() && todosIguales ){
        if ( diaAux != ((Fecha) listaSugerencias.elementAt(i)).dia )
        // Hemos encontrado uno que difiere del resto
          todosIguales = false;
        i++;
      }
      if ( todosIguales )
        dia = diaAux;
      return dia;
    }

    /**
     * M�todo que comprueba si todas las Fecha de la lista de entrada tienen
     * en su fecha el mismo campo mes
     * Si todas tienen el mismo campo mes devuelve dicho mes y Utilidades.int_INDEFINIDO
     * en caso contrario
     * @param listaSugerencias
     * @return int
     */
    private int factorComunMes( Vector listaSugerencias ){
      int mes = Utilidades.int_INDEFINIDO;
      int mesAux = ((Fecha) listaSugerencias.elementAt(0)).mes;
      boolean todosIguales = true;
      int i = 0;
      while ( i < listaSugerencias.size() && todosIguales ){
        if ( mesAux != ((Fecha) listaSugerencias.elementAt(i)).mes )
        // Hemos encontrado uno que difiere del resto
          todosIguales = false;
        i++;
      }
      if ( todosIguales )
        mes = mesAux;
      return mes;
    }

    /**
     * M�todo que comprueba si todas las Fecha de la lista de entrada tienen
     * en su fecha el mismo campo a�o
     * Si todas tienen el mismo campo a�o devuelve dicho a�o y Utilidades.int_INDEFINIDO
     * en caso contrario
     * @param listaSugerencias
     * @return int
     */
    private int factorComunAnio( Vector listaSugerencias ){
      int anio = Utilidades.int_INDEFINIDO;
      int anioAux = ((Fecha) listaSugerencias.elementAt(0)).anno;
      boolean todosIguales = true;
      int i = 0;
      while ( i < listaSugerencias.size() && todosIguales ){
        if ( anioAux != ((Fecha) listaSugerencias.elementAt(i)).anno )
        // Hemos encontrado uno que difiere del resto
          todosIguales = false;
        i++;
      }
      if ( todosIguales )
        anio = anioAux;
      return anio;
    }
}
