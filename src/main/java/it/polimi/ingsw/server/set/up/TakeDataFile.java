package it.polimi.ingsw.server.set.up;


import it.polimi.ingsw.server.log.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.LogConstants.*;

public class TakeDataFile {
    private FileReader reader;
    private BufferedReader input;
    private String file;
    public TakeDataFile(String file)
    {
        this.file = file;
    }

    public String getParameter(String parameter)   {
        String result = null;
        try {
            reader = new FileReader(file);
            input = new BufferedReader(reader);
            Optional<String> optional =  input.lines()
                    .filter(line-> line.contains(parameter))
                    .map(line -> line.substring(parameter.length()+1))
                    .findFirst();
            if(optional.isPresent())
                result = optional.get();
        } catch (FileNotFoundException e) {
            Log.getLogger().addLog(CONFIGURATION_FILE_ERROR, Level.SEVERE,this.getClass().getName(),TAKE_DATA_FILE_GET_PARAMETER);
        }finally {
            closeBuffer();
        }

        return result;
    }

    private void closeBuffer(){
        try {
            reader.close();
            input.close();
        } catch (IOException e) {
            Log.getLogger().addLog(e.getMessage(),Level.SEVERE,this.getClass().getName(),TAKE_DATA_FILE_CLOSE_BUFFER);
        }

    }

    public void printContent() {
        try {
            reader = new FileReader(file);
            input = new BufferedReader(reader);
            input.lines().forEach(System.out::println);
        } catch (FileNotFoundException e) {
            Log.getLogger().addLog(e.getMessage(),Level.SEVERE,this.getClass().getName(),TAKE_DATA_FILE_PRINT_CONTENT);
        }finally {
            closeBuffer();
        }

    }

    public void setFileReader(String file) {
        this.file = file;
    }

}

