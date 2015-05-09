package icaro.aplicaciones.informacion.gestionCitas ;


import java.util.*;
import java.io.Serializable;


/**
 * 
 */
public class Hora implements Comparable, Cloneable, java.io.Serializable
{

  /**
   * Constantes para el atributo momentoDia
   */
  public final static int MOMENTO_DIA_AM = 0;
  public final static int MOMENTO_DIA_PM = 1;

    /**
     *  Hora expresada mediante un n�mero entero de 0 a 23
     */
    public int hora = Utilidades.int_INDEFINIDO;
    /**
     *  Minutos expresadons mediante un n�mero entero de 0 a 59
     */
    public int minutos = Utilidades.int_INDEFINIDO;
    /**
     *  Minutos expresadons mediante un n�mero entero de 0 a 59
     */
    public int segundos = Utilidades.int_INDEFINIDO;
    /**
     *  Momento del d�a. Puede ser: MOMENTO_DIA_AM, MOMENTO_DIA_PM, o indefinido
     */
    public int momentoDia = Utilidades.int_INDEFINIDO;

    /**
     *  Constructor sin par�metros, inicializa todos los campos a indefinido
     */
    public Hora() {

    }


    /**
     *  Constructor que inicializa la Hora seg�n los par�metros indicados
     *
     *@param  hora      del d�a de 0-23 horas
     *@param  minutos   de 0-59
     *@param  segundos  de 0-59;
     */
    public Hora(int hora, int minutos, int segundos) {
        this.hora = hora;
        this.minutos = minutos;
        this.segundos = segundos;
    }


    /**
     *  Constructor que inicializa la Hora seg�n los par�metros indicados
     *
     *@param  hora      del d�a de 0-23 horas
     *@param  minutos   de 0-59
     *@param  segundos  de 0-59;
     */
    public Hora(int hora, int minutos, int segundos, int momentoDia) {
        this.hora = hora;
        this.minutos = minutos;
        this.segundos = segundos;
        this.momentoDia = momentoDia;
    }

    /**
     *  Constructor que inicializa la Hora seg�n los par�metros indicados
     *
     *@param  hora     del d�a de 0-23 horas
     *@param  minutos  de 0-59
     */
    public Hora(int hora, int minutos) {
        this.hora = hora;
        this.minutos = minutos;
        this.segundos = Utilidades.int_INDEFINIDO;

    }


    /**
     *  Clona el objeto actual
     *
     *  @return    objeto clonado
     */
    public Object clone() {
        return new Hora(this.hora, this.minutos, this.segundos, this.momentoDia);
    }


    /**
     *  Implementaci�n del interfaz java.lang.Comparable
     *
     *@param  obj  fecha con la que comparar
     *@return      -1 si la fecha es menor, 0 si son iguales y +1 si es mayor
     */
    public int compareTo(Object obj) {

        Hora h = (Hora)obj;
        int res = 0;

        if(this.hora < h.hora)
            res = -1;
        if(this.hora > h.hora)
            res = +1;
        if(this.hora == h.hora && this.minutos < h.minutos)
            res = -1;
        if(this.hora == h.hora && this.minutos > h.minutos)
            res = +1;
        if(this.hora == h.hora && this.minutos == h.minutos && this.segundos < h.segundos)
            res = -1;
        if(this.hora == h.hora && this.minutos == h.minutos && this.segundos > h.segundos)
            res = +1;
        if(this.hora == h.hora && this.minutos == h.minutos && this.segundos == h.segundos)
            res = 0;

        return res;
    }


    /**
     *  Devuelve la hora actual del sistema
     *
     *  @return    hora actual del sistema
     */
    public static Hora getHoraActual() {

        Calendar calendar = Calendar.getInstance(Hora.getSimpleTimeZoneMadrid());
        Hora res = new Hora();

        // Obtenemos la franja horaria: AM o PM
        int AM_PM = calendar.get(Calendar.AM_PM);

        if(AM_PM == Calendar.AM){
            // Es AM
            res.hora = calendar.get(Calendar.HOUR);
            res.momentoDia = Hora.MOMENTO_DIA_AM;
        }
        else{
            // Es PM
            res.hora = calendar.get(Calendar.HOUR) + 12;
            res.momentoDia = Hora.MOMENTO_DIA_PM;
        }
        res.minutos = calendar.get(Calendar.MINUTE);
        res.segundos = calendar.get(Calendar.SECOND);

        return res;
    }


    /**
     *  Devuelve el 'time stamp' asociado a la fecha y hora dadas
     *
     *  @param  f      Fecha
     *  @param  h      Hora
     *  @return        n�mero de ms transcurridos desde 1-Enero-1970, 00:00:00 GMT
     *                 + 1 (Europa/Madrid)
     */
    public static long getTimeStampUnix(Fecha f, Hora h) {

        // Creamos un GregorianCalendar con la zona horaria de Madrid
        Calendar cal = new GregorianCalendar(Hora.getSimpleTimeZoneMadrid());

        //fijamos la fecha y hora del calendar (no consideramos ms)
        if(h.segundos == Utilidades.int_INDEFINIDO)
            cal.set(f.anno, f.mes - 1, f.dia, h.hora, h.minutos, 0);
        else
            cal.set(f.anno, f.mes - 1, f.dia, h.hora, h.minutos, h.segundos);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }


    /**
     *  Devuelve el 'time stamp' asociado a la fecha y hora como una cadena
     *  AAAAMMDDHHMM (a�o-mes-dia-hora-minuto)
     *
     *@param  f      Fecha
     *@param  h      Hora
     *@return        n�mero de ms transcurridos desde 1-Enero-1970, 00:00:00 GMT
     *               + 1 (Europa/Madrid)
     */
    public static String getTimeStampAnioMesDiaHora(Fecha f, Hora h) {

        // Creamos un GregorianCalendar con la zona horaria de Madrid
        Calendar cal = new GregorianCalendar(Hora.getSimpleTimeZoneMadrid());

        //fijamos la fecha y hora del calendar (no consideramos segundos ni ms)
        if(h.segundos == Utilidades.int_INDEFINIDO)
            cal.set(f.anno, f.mes - 1, f.dia, h.hora, h.minutos, 0);
        else
            cal.set(f.anno, f.mes - 1, f.dia, h.hora, h.minutos, h.segundos);
        cal.set(Calendar.MILLISECOND, 0);

        //formateamos 'a�o mes dia hora formato 24h minutos'
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmm");
        return sdf.format(cal.getTime());

    }


    /**
     *  Devuelve la hora correspondiente al 'time stamp' Unix dado en ms
     *
     * @param  ms  n�mero de ms desde las 0 horas del 1 de enero de 1970
     * @return     Hora correspondiente al time stamp
     */
    public static Hora getHoraFromMs(long ms) {

        // Creamos un GregorianCalendar con la zona horaria de Madrid
        Calendar cal = new GregorianCalendar(Hora.getSimpleTimeZoneMadrid());

        cal.setTime(new Date(ms));

        return new Hora(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    }


    /**
     *  Devuelve un objeto SimpleTimeZone configurado con la hora local de
     *  Europe/Madrid (GMT+1) , incluyendo las reglas de cambio de hora
     *  "Daylight saving time-adjustment" (DST)
     *
     *@return    hora local configurada para Madrid/Europe GMT+1
     */
    public static SimpleTimeZone getSimpleTimeZoneMadrid() {

        // creamos zona horaria para Madrid (GMT +1)
        SimpleTimeZone stz = new SimpleTimeZone(+1 * 60 * 60 * 1000, "Europe/Madrid");

        //Creamos reglas de cambio de hora "Daylight saving time-adjustment" (DST)

        //DST started on Sunday March 31, 2002 at 2:00:00 AM local standard time
        stz.setStartRule(Calendar.MARCH, 31, 2 * 60 * 60 * 1000);

        //DST ends on Sunday October 27, 2002 at 3:00:00 AM local daylight time
        stz.setEndRule(Calendar.OCTOBER, 27, 3 * 60 * 60 * 1000);

        return stz;
    }


    /**
     *  Devuelve la fecha correspondiente al 'time stamp' Unix dado en ms
     *
     *@param  ms  n�mero de ms desde las 0 horas del 1 de enero de 1970
     *@return     Fecha correspondiente al 'time stamp'
     */
    public static Fecha getFechaFromMs(long ms) {

        // Creamos un GregorianCalendar con la zona horaria de Madrid
        Calendar cal = new GregorianCalendar(Hora.getSimpleTimeZoneMadrid());

        cal.setTime(new Date(ms));

        return new Fecha(cal.get(Calendar.DATE), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
    }


    /**
     *  Devuelve la (Fecha,Hora) correspondiente al Time Stamp Unix dado en ms
     *
     *@param  ms  n�mero de ms desde las 0 horas del 1 de enero de 1970
     *@return     Array de 2 componentes,
     *             componente [0] = Fecha
     *             componente [1] = Hora
     */
    public static Object[] getFechaHoraFromMs(long ms) {

        Object[] res = new Object[2];

        res[0] = getFechaFromMs(ms);
        res[1] = getHoraFromMs(ms);

        return res;
    }


    /**
     *  Haya la diferencia de tiempos entre la hora actual y la hora dada
     *  y la devuelve en un nuevo objeto  hora
     *
     *@param  hora  sustraendo de la operaci�n de resta
     *@return       hora resultante de la resta
     */

    public Hora resta(Hora hora) {

        int hora_sustraendo = hora.hora;
        int min_sustraendo = hora.minutos;
        int seg_sustraendo = hora.segundos;

        int resSegundos = this.segundos - seg_sustraendo;
        if(resSegundos < 0) {
            resSegundos = 60 - java.lang.Math.abs(resSegundos);
            min_sustraendo++;
        }
        int resMinutos = this.minutos - min_sustraendo;
        if(resMinutos < 0) {
            resMinutos = 60 - java.lang.Math.abs(resMinutos);
            hora_sustraendo++;
        }
        int resHora = this.hora - hora_sustraendo;
        if(resHora < 0)
            resHora = 24 - java.lang.Math.abs(resHora);

        return new Hora(resHora, resMinutos, resSegundos);
    }


    /**
     * Comprueba si la hora est� definida, es decir, si todos sus campos
     * est�n definidos
     *
     * @return   true si la Hora tiene completos todos sus campos, false e.o.c.
     */
    public boolean estaCompleta() {
        if(this.minutos != Utilidades.int_INDEFINIDO &
           this.hora != Utilidades.int_INDEFINIDO &
           this.segundos != Utilidades.int_INDEFINIDO &
           this.momentoDia != Utilidades.int_INDEFINIDO)
          return true;

        // else
        return false;
    }

    /**
     * Comprueba si la hora est� indefinida, es decir, si ninguno de sus campos
     * est� indefinido
     *
     * @return   true si la Hora tiene completos todos sus campos, false e.o.c.
     */
    public boolean estaIndefinida() {
        if(this.minutos == Utilidades.int_INDEFINIDO &
           this.hora == Utilidades.int_INDEFINIDO &
           this.segundos == Utilidades.int_INDEFINIDO &
           this.momentoDia == Utilidades.int_INDEFINIDO)
          return true;

        // else
        return false;
    }


    /**
     *  Suma una hora a la que ten�amos
     *
     *  @param  horaSum  hora a sumar
     */
    public void suma(Hora horaSum) {

        int hora = horaSum.getHora();
        int min = horaSum.minutos;
        int seg = horaSum.segundos;

        int restoSeg = (this.segundos + seg) / 60;
        this.segundos = (this.segundos + seg) % 60;
        int restoMin = (this.minutos + min + restoSeg) / 60;
        this.minutos = (this.minutos + min + restoSeg) % 60;
        this.hora = (this.hora + hora + restoMin) % 24;
        if ( this.hora >= 12 )
          this.momentoDia = this.MOMENTO_DIA_PM;
        else
          this.momentoDia = this.MOMENTO_DIA_AM;

    }


    /**
     *  Convierte un objeto de la clase Date en otro de la clase Hora
     *
     *@param  date  objeto Date a convertir
     *@return       objeto Date convertido a Hora
     */
    public static Hora date2Hora(Date date) {

        // Devuelve la hora de la fecha
        Calendar calendar = Calendar.getInstance(Hora.getSimpleTimeZoneMadrid());
        calendar.setTime(date);

        // Obtenemos la franja horaria: AM o PM
        int AM_PM = calendar.get(Calendar.AM_PM);
        int hora = 0;
        int minutos = 0;
        int segundos = 0;
        int momentoDia = 0;
        if(AM_PM == Calendar.AM){
            // Es AM
            hora = calendar.get(Calendar.HOUR);
            momentoDia = Hora.MOMENTO_DIA_AM;
        }
        else{
            // Es PM
            hora = calendar.get(Calendar.HOUR) + 12;
            momentoDia = Hora.MOMENTO_DIA_PM;
        }
        minutos = calendar.get(Calendar.MINUTE);
        segundos = calendar.get(Calendar.SECOND);


        return new Hora(hora, minutos, segundos);
    }


    /**
     *  Devuelve el atributo hora como un entero de 0-23
     *
     *  @return  valor del atributo hora
     */
    public int getHora() {
        return hora;
    }


    /**
     *  Convierte una hora con el siguiente formato:
     *   "hh:mm:ss" o "hh:mm" o "h:mm" o "h:mm:ss"
     *  en un objeto de la clase hora
     *
     *@param  horaS  cadena que contiene la hora
     *@return        objeto de la clase Hora equivalente a la hora dada en forma de cadena
     */
    public static Hora string2Hora(String horaS) {
        // La hora en cadena tiene que tener el siguiente formato: "hh:mm:ss" o "hh:mm" o "h:mm" o "h:mm:ss"
        Hora hora = new Hora();
        try {
            // Recuperamos la hora, minutos y segundos
            int primerSeparador = horaS.indexOf(":");
            String hora2S = horaS.substring(0, primerSeparador);
            int segundoSeparador = horaS.indexOf(":", primerSeparador + 1);
            String minutos = "00";
            if(segundoSeparador > -1)
                // Hay segundo separador
                minutos = horaS.substring(primerSeparador + 1, segundoSeparador);
            else
                // No hay segundo separador
                minutos = horaS.substring(primerSeparador + 1, horaS.length());

            String segundos = "00";
            if(segundoSeparador > -1)
                // Hay segundo separador
                segundos = horaS.substring(segundoSeparador + 1, horaS.length());

            hora = new Hora(Integer.parseInt(hora2S), Integer.parseInt(minutos), Integer.parseInt(segundos));

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return hora;
    }


    /**
     *  Devuelve un objeto de la clase Date con la fecha del sistema y la
     *  hora dada por el objeto actual
     *
     *  @return    objeto Date con la fecha del sistema y la hora del objeto actual
     */
    public Date toDate() {
        // Devuelve la fecha actual pero actualizada a la nueva hora

        Calendar calendar = Calendar.getInstance(Hora.getSimpleTimeZoneMadrid());

        // Recuperamos el a�o, mes y d�a
        int anyo = calendar.get(Calendar.YEAR);
        int mes  = calendar.get(Calendar.MONTH);
        int dia  = calendar.get(Calendar.DATE);
        // Actualizamos los nuevos campos
        calendar.set(anyo, mes, dia, this.hora, this.minutos, this.segundos);

        // Devolvemos la fecha
        return calendar.getTime();
    }


    /**
     *  Devuelve un objeto de la clase Date con la fecha dada por el parametro
     *  fecha y la hora dada por el objeto actual
     *
     *  @param  fecha  Description of the Parameter
     *  @return        java.util.Date
     */
    public Date toDate(Date fecha) {
        // Devuelve la fecha pasada por par�metro pero actualizada a la nueva hora

        Calendar calendar = Calendar.getInstance(Hora.getSimpleTimeZoneMadrid());
        calendar.setTime(fecha);

        // Recuperamos el a�o, mes y d�a
        int anyo = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DATE);
        // Actualizamos los nuevos campos
        calendar.set(anyo, mes, dia, this.getHora(), this.minutos, this.segundos);

        // Devolvemos la fecha
        return calendar.getTime();
    }


    /**
     *  Devuelve la hora en el siguiente formato: "hh:mm"
     *
     *@return    cadena de texto con la hora en formato hh:mm
     */
    public String toString() {

        String cad = "";
        String hora = "";
        String minutos = "";
        String segundos = "";
        String momento = "";
        if(this.hora == Utilidades.int_INDEFINIDO)
            hora = "INDEF";
        else
            if(this.getHora() < 10)
                hora = "0" + Integer.toString(this.getHora());
            else
                hora = Integer.toString(this.getHora());

        if(this.minutos == Utilidades.int_INDEFINIDO)
            minutos = "INDEF";

        else
            if(this.minutos < 10)
                minutos = "0" + Integer.toString(this.minutos);
            else
                minutos = Integer.toString(this.minutos);

        if(this.segundos == Utilidades.int_INDEFINIDO)
            segundos = "INDEF";
        else
            if(this.segundos < 10)
                segundos = "0" + Integer.toString(this.segundos);
            else
                segundos = Integer.toString(this.segundos);

        if ( this.momentoDia == Utilidades.int_INDEFINIDO )
          momento = "INDEF";
        else
          if (this.momentoDia == this.MOMENTO_DIA_AM)
            momento = "AM";
          else
            momento = "PM";

        cad = "(HORA: " + hora + "h " + minutos + "m " + segundos + "s ,AM/PM-> "+momento+")";

        return cad;
    }

    /**
     * Devuelve la hora en formato "las 3 de la tarde", "las 3 de la madrugada"
     *
     * @return String con el formato de la hora
     */
    public String expresar()
    {
      String mensaje= "";

     if ( (this.hora == 1) || (this.hora == 13) )
       mensaje+= "la ";
     else
       mensaje+= "las ";

     if (this.hora > 12)
       mensaje+= this.hora - 12;
     else if (this.hora == 0)
       mensaje+= 12;
     else
       mensaje+= this.hora;

     if (this.minutos > 0)
       mensaje+= " y " + this.minutos;

     if ( (this.hora >= 1) && (this.hora <= 6) )
       mensaje+= " de la madrugada";
     else if ( (this.hora >= 7) && (this.hora <= 12) )
       mensaje+= " de la ma�ana";
     else if ( (this.hora >= 13) && (this.hora <= 20) )
       mensaje+= " de la tarde";
     else
       mensaje+= " de la noche";

     return mensaje;

    }

    /**
     *  Compara seg�n igualdad
     *
     *@param  obj  objeto a comparar
     *@return      true si los objetos son iguales false e.o.c.
     */

    public boolean equals(Object obj) {
      try {
          if ( obj != null ){
            Hora h = (Hora)obj;
            return (this.hora == h.hora && this.minutos == h.minutos && this.segundos == h.segundos && this.momentoDia == h.momentoDia);
          }
      } catch(Exception e) {
          e.printStackTrace();
      }
      return false;
    }


    /**
     * M�todo que devuelve el n�mero de campos no indefinidos
     * @return int     N�mero de campos no indefinidos
     */
    public int numCamposDef(){
      int num = 0;
      if ( this.hora != Utilidades.int_INDEFINIDO )
        num++;
      if ( this.minutos != Utilidades.int_INDEFINIDO )
        num++;
      if ( this.segundos != Utilidades.int_INDEFINIDO )
        num++;
      if ( this.momentoDia != Utilidades.int_INDEFINIDO )
        num++;
      return num;
    }

    /**
     * M�todo que nos dice si esta hora es m�s completa que la hora pasada como
     * par�metro
     * @param hora     Hora con la que queremos comparar
     * @return boolean  Devolver� true si esta hora es m�s completa, esto es, tiene
     *                  un n�mero de campos definidos igual o mayor que la hora
     *                  pasada como par�metro y false e.o.c.
     */
    public boolean esMasCompleta( Hora hora ){
      boolean b = false;
      int numHoraActual = numCamposDef();
      int numHoraParam = hora.numCamposDef();
      if ( numHoraActual >= numHoraParam )
        b = true;
      return b;
    }

    /**
     * Metodo que intenta completar una hora mediante sentido comun y mediante
     * el horario laboral
     * @param listaSugerencias
     */
    public void completar(){
      // Hora en la que comienzan a recibir los UPA's
//      int primeraHoraCita = Integer.parseInt(Configuracion.obtenerParametro("HORA_COMIENZO_CITAS"));
      int primeraHoraCita = 830;
      // �ltima hora a la que reciben los UPA's
//      int ultimaHoraCita = Integer.parseInt(Configuracion.obtenerParametro("HORA_FIN_CITAS"));;
      int ultimaHoraCita = 2030;
      if ( this.segundos == Utilidades.int_INDEFINIDO )
        this.segundos = 0;
      if ( this.momentoDia == Utilidades.int_INDEFINIDO ){
        if ( this.hora >= 12 )
        // PM
          this.momentoDia = this.MOMENTO_DIA_PM;
        else{
        // Completamos mediante el horario laboral que va de 9 a 18
          if ( this.hora > 0 && this.hora < 7 ){
          // Se refiere a por la tarde
            this.momentoDia = this.MOMENTO_DIA_PM;
            // Actualizamos las horas de acuerdo al momento
            this.hora = this.hora + 12;
          }
          else if ( this.hora > 8 && this.hora < 12 )
          // Se refiere a por la ma�ana
            this.momentoDia = this.MOMENTO_DIA_AM;
        }
      }
    }

    /**
     * M�todo que intenta completar una hora primero con la lista de sugerencias
     * y luego mediante sentido com�n
     * @param listaSugerencias
     */
    public void completar( Vector listaSugerencias ){
      // Los minutos siempre son 0
      Hora horaCompletada = new Hora();
      if ( this.hora == Utilidades.int_INDEFINIDO ){
      // Intentamos completar las horas
        int horas = factorComunHoras( listaSugerencias );
        horaCompletada.hora = horas;
      }
      else
        horaCompletada.hora = this.hora;
      if ( this.momentoDia == Utilidades.int_INDEFINIDO ){
      // Intentamos completar el momento del d�a
        int momento = factorComunMomentoDia( listaSugerencias );
        horaCompletada.momentoDia = momento;
      }
      else
        horaCompletada.momentoDia = this.momentoDia;
      if ( this.minutos == Utilidades.int_INDEFINIDO )
        horaCompletada.minutos = 0;
      else
        horaCompletada.minutos = this.minutos;
      horaCompletada.segundos = 0;
      // Comprobamos si la hora obtenida completando con la lista de sugerencias
      // es una hora correcta
      if ( horaCompletada.estaCompleta() ){
        if ( horaCompletada.horaEnHorarioLaboral() ){
        // La hora est� dentro del horario laboral
          // Completamos la hora con la hora obtenida a partir de la lista de
          // sugerencias
          this.completar(horaCompletada);
        }
        else
        // La fecha no est� dentro del horario laboral. Completamos la hora con sentido com�n
          this.completar();
      }
      else{
      // Por si quedan atributos sin completar, completamos con sentido com�n
        this.completar(horaCompletada);
        this.completar();
      }
    }

    /**
     *  Comleta la fecha con los campos que no est�n indefinidos de una hora
     *  nueva
     *
     *@param  horaNueva  hora con la que completar la hora actual
     */
    public void completar(Hora horaNueva) {

        if(horaNueva != null) {

            if(horaNueva.hora != Utilidades.int_INDEFINIDO)
                this.hora = horaNueva.hora;

            if(horaNueva.minutos != Utilidades.int_INDEFINIDO)
                this.minutos = horaNueva.minutos;

            if(horaNueva.segundos != Utilidades.int_INDEFINIDO)
                this.segundos = horaNueva.segundos;

            if(horaNueva.momentoDia != Utilidades.int_INDEFINIDO )
                this.momentoDia = horaNueva.momentoDia;
        }
    }

    /**
     * M�todo que comprueba si todas las Fecha de la lista de entrada tienen
     * en su hora el mismo campo hora
     * Si todas tienen el mismo campo hora devuelve dichas horas y Utilidades.int_INDEFINIDO
     * en caso contrario
     * @param listaSugerencias
     * @return int
     */
    private int factorComunHoras( Vector listaSugerencias ){
      int horas = Utilidades.int_INDEFINIDO;
      int horasAux = ((Hora) listaSugerencias.elementAt(0)).hora;
      boolean todosIguales = true;
      int i = 0;
      while ( i < listaSugerencias.size() && todosIguales ){
        if ( horasAux != ((Hora) listaSugerencias.elementAt(i)).hora )
        // Hemos encontrado uno que difiere del resto
          todosIguales = false;
        i++;
      }
      if ( todosIguales )
        horas = horasAux;
      return horas;
    }

    /**
     * M�todo que comprueba si todas las Fecha de la lista de entrada tienen
     * como momento del d�a el mismo momento
     * Si todas tienen el mismo momento del d�a devuelve dicho momento y Utilidades.int_INDEFINIDO
     * en caso contrario
     * @param listaSugerencias
     * @return int
     */
    private int factorComunMomentoDia( Vector listaSugerencias ){
      int momento = Utilidades.int_INDEFINIDO;
      int momentoAux = ((Hora) listaSugerencias.elementAt(0)).momentoDia;
      boolean todosIguales = true;
      int i = 0;
      while ( i < listaSugerencias.size() && todosIguales ){
        if ( momentoAux != ((Hora) listaSugerencias.elementAt(i)).momentoDia )
        // Hemos encontrado uno que difiere del resto
          todosIguales = false;
        i++;
      }
      if ( todosIguales )
        momento = momentoAux;
      return momento;
    }

    /**
     * Metodo que comprueba que si la fecha es igual a la fecha actual y la hora
     * del aviso no ha pasado.
     *
     * @param fecha    fecha
     * @return boolean Devuelve true si la fecha de inicio es igual a la fecha
     *                 actual y la hora es menor que la hora actual y false en
     *                 caso contrario
     */
    public boolean horaCorrecta ( Fecha fecha ){
      boolean horaCorrecta = true;

      if ( fecha != null && this != null ){
        // Obtengo la fecha actual
        Fecha fechaActual = Fecha.getFechaActual();
        Hora horaActual = Hora.getHoraActual();
        int compHoraActual = this.compareTo( horaActual );
        // La hora del aviso es mayor o igual que la hora actual si el resultado de la
        // comparacion es igual a 0 � 1
        boolean horaMayor = compHoraActual >= 0;
        // La hora es incorrecta si la fecha es igual a la fecha actual y
        // la hora del aviso es menor que la hora actual
        if ( fecha.equals(fechaActual) && !horaMayor ){
          horaCorrecta = false;
        }
      }
      return horaCorrecta;
    }

    /**
     * Metodo que indica si la hora esta dentro del horario laboral
     * @return
     */
    public boolean horaEnHorarioLaboral(){
      boolean enHorarioLaboral = true;
//      int primeraHoraCita = Integer.parseInt(Configuracion.obtenerParametro("HORA_COMIENZO_CITAS"));
//      int ultimaHoraCita = Integer.parseInt(Configuracion.obtenerParametro("HORA_FIN_CITAS"));
      int primeraHoraCita = 830;
      int ultimaHoraCita = 2030;
      if ( (this.hora < primeraHoraCita) ||
           (this.hora > ultimaHoraCita) )
        enHorarioLaboral = false;
      return enHorarioLaboral;
    }

    /**
     *  Test de la clase
     *
     *
     */
    public static void main(String args[]) {

        Hora h1 = new Hora(0, 0);
        Hora h2 = null;
        System.out.println("La comparacion es "+h1.equals(h2));
/*        Hora h2 = new Hora(9, 54);
        Fecha f = new Fecha(1, 1, 1970);

        System.out.println(Hora.getTimeStampUnix(f, h1));
        System.out.println(Hora.getTimeStampAnioMesDiaHora(f, h1));

        if(h1.compareTo(h2)<0)
	System.out.println("menor");
       else
       if(h1.compareTo(h2)>0)
	System.out.println("mayor");
       else
       if(h1.compareTo(h2)==0)
	System.out.println("igual");
	System.out.println(Hora.getHoraActual());*/

    }

}
