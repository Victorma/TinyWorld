package icaro.infraestructura.entidadesBasicas;

import java.io.IOException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public final class ConfiguracionTrazas {

    public static void configura() {
        configura(Logger.getRootLogger(), "log/organizacion.log", "debug");
    }
    
    public static void configura(Logger logger) {
        configura(logger, "log/organizacion.log", "debug");
    }

    public static void configura(Logger logger, String archivoLog, String nivelLog) {
        System.out.println("TRAZAS: Configurando logger: " + logger.getName());
        PatternLayout layout = new PatternLayout("%-4r [%t] %-5p %c %x - %m%n");
        ConsoleAppender appenderConsola = new ConsoleAppender(layout);
        logger.removeAllAppenders();
        logger.addAppender(appenderConsola);
        try {
            FileAppender appenderArchivo = new FileAppender(layout, archivoLog, true);
            logger.addAppender(appenderArchivo);
        } catch (IOException e) {
            logger.error("Error incializando logging auxiliar sobre archivo.");
            logger.error(e);
        }
        logger.setLevel(Level.toLevel(nivelLog));
    }
}
