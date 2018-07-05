package it.polimi.ingsw.server.set.up;


import it.polimi.ingsw.server.log.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.LogConstants.*;

public class TakeDataFile {
    private String file;
    public TakeDataFile(String file)
    {
        this.file = file;
    }

    public String getParameter(String parameter)   {
        String result = null;
        try ( FileReader reader = new FileReader(file); BufferedReader input = new BufferedReader(reader)){
            Optional<String> optional =  input.lines()
                    .filter(line-> line.contains(parameter))
                    .map(line -> line.substring(parameter.length()+1))
                    .findFirst();
            if(optional.isPresent())
                result = optional.get();
        } catch (IOException e) {
            Log.getLogger().addLog(CONFIGURATION_FILE_ERROR, Level.SEVERE,this.getClass().getName(),TAKE_DATA_FILE_GET_PARAMETER);
        }
        return result;
    }

}

