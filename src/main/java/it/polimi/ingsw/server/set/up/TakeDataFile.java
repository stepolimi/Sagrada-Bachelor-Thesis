package it.polimi.ingsw.server.set.up;


import it.polimi.ingsw.server.log.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.LogConstants.*;

public class TakeDataFile {
    private FileReader reader;
    private String file;
    public TakeDataFile(String file)
    {
        this.file = file;
    }

    public String getParameter(String parameter)   {
        String result = null;
        try (BufferedReader input = new BufferedReader(reader)){
            reader = new FileReader(file);

            Optional<String> optional =  input.lines()
                    .filter(line-> line.contains(parameter))
                    .map(line -> line.substring(parameter.length()+1))
                    .findFirst();
            if(optional.isPresent())
                result = optional.get();
        } catch (IOException e) {
            Log.getLogger().addLog(CONFIGURATION_FILE_ERROR, Level.SEVERE,this.getClass().getName(),TAKE_DATA_FILE_GET_PARAMETER);
        } finally {
            closeBuffer();
        }

        return result;
    }

    private void closeBuffer(){
        try {
            reader.close();
        } catch (IOException e) {
            Log.getLogger().addLog(e.getMessage(),Level.SEVERE,this.getClass().getName(),TAKE_DATA_FILE_CLOSE_BUFFER);
        }

    }
}

