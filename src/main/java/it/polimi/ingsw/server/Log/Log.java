package it.polimi.ingsw.server.Log;

import it.polimi.ingsw.server.setUp.TakeDataFile;

import java.io.IOException;
import java.util.logging.*;

import static it.polimi.ingsw.server.costants.NameCostants.PATH_LOG_FILE;
import static it.polimi.ingsw.server.costants.SetupCostants.CONFIGURATION_FILE;

public class Log {
    private static Log log;
    private Logger LOGGER;
    private ConsoleHandler consoleHandler;
    private Log()
    {
        LOGGER = Logger.getLogger(Log.class.toString());
        LOGGER.setUseParentHandlers(false);
        TakeDataFile config = new TakeDataFile(CONFIGURATION_FILE);
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(config.getParameter(PATH_LOG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        consoleHandler = new ConsoleHandler();
        LOGGER.addHandler(fileHandler);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.ALL);
        Formatter f = new SimpleFormatter();
        fileHandler.setFormatter(f);
    }

    public static Log getLogger(){
        if(log==null)
             log = new Log();
            return log;
    }
    public void setLevelLog(Level level){
        LOGGER.setLevel(level);
    }

    public Level getLevelLog()
    {
      return LOGGER.getLevel();
    }

    public void serVisibleLog(Level level)
    {
        consoleHandler.setLevel(level);
    }

    public void addLog(String message,Level levelLoggin,String sourceClass,String sourceMethod)
    {
        LOGGER.logp(levelLoggin,sourceClass,sourceMethod,message);
    }

}
