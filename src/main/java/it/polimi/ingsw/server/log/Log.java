package it.polimi.ingsw.server.log;

import it.polimi.ingsw.server.set.up.TakeDataFile;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.*;

import static it.polimi.ingsw.server.costants.NameConstants.PATH_LOG_FILE;
import static it.polimi.ingsw.server.costants.SetupConstants.CONFIGURATION_FILE;

public class Log {
    private static Log log;
    private final Logger logger;
    private final ConsoleHandler consoleHandler;
    private Log() {
        logger = Logger.getLogger(Log.class.toString());
        logger.setUseParentHandlers(false);
        TakeDataFile config = new TakeDataFile(CONFIGURATION_FILE);
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(config.getParameter(PATH_LOG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        consoleHandler = new ConsoleHandler();
        logger.addHandler(Objects.requireNonNull(fileHandler));
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);
        Formatter f = new SimpleFormatter();
        fileHandler.setFormatter(f);
    }

    public static Log getLogger(){
        if(log==null)
             log = new Log();
        return log;
    }
    public void setLevelLog(Level level){
        logger.setLevel(level);
    }

    public Level getLevelLog() { return logger.getLevel(); }

    public void serVisibleLog(Level level)
    {
        consoleHandler.setLevel(level);
    }

    public void addLog(String message,Level levelLogin,String sourceClass,String sourceMethod)
    {
        logger.logp(levelLogin,sourceClass,sourceMethod,message);
    }

}
