package icaro.infraestructura.entidadesBasicas;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class ConfiguracionTrazas {

    public static void configura() {
        configura(Logger.getRootLogger(), "log/organizacion.log", "debug");
    }

    public static void configura(Logger logger) {
        configura(logger, "log/organizacion.log", "debug");
    }
    
    public static void configura(Logger logger, String archivoLog, String nivelLog) {
        // Como es un Logger nuevo, no tiene appenders, se los colocamos
        System.out.println("TRAZAS: Configurando logger: " + logger.getName());
        // WARNING: Comprobar que no tiene appenders antes de ponerle mas
        logger.removeAllAppenders();
        // Primero el de consola
        // El layout esta hard-wired
        PatternLayout layout = new PatternLayout("%-4r [%t] %-5p %c %x - %m%n");

        ConsoleAppender appenderConsola = new ConsoleAppender(layout);
        logger.addAppender(appenderConsola);

        // Ahora el patron de archivo
        // Primero obtenemos el nombre de archivo de la configuracion
        try {
            FileAppender appenderArchivo = new FileAppender(layout, archivoLog, true);
            logger.addAppender(appenderArchivo);
        } catch (IOException e) {
            logger.error("Error incializando logging auxiliar sobre archivo");
            logger.error(e);
        }

        logger.setLevel(Level.toLevel(nivelLog));
    }
}
