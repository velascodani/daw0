
// 21/05/14 - Añadido  al proyecto LoggerManager



package managers;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Rebag-Ware Logger class
 *
 */
public class LoggerManager {

    private static Logger log;
    public static String prefix;
    public final static boolean DEBUG = false;

    /**
     * get a reference to log file
     *
     * @return
     */
    public static Logger getLog() {

        if (log == null) {
          
            PropertyConfigurator.configure(prefix+"/WEB-INF/log4j.properties");
            log = Logger.getLogger("ecotienda");
        }
        return log;
    }
}
