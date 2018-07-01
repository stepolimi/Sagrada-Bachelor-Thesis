package it.polimi.ingsw.server.setUp;


import it.polimi.ingsw.server.Log.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

public class TakeDataFile {
    FileReader reader;
    BufferedReader input;
    String file;
    public TakeDataFile(String file)
    {
        this.file = file;
    }

    public String getParameter(String parameter)   {
        String result = null;
        try {
            reader = new FileReader(file);
            input = new BufferedReader(reader);
            result =  input.lines().filter(line-> line.contains(parameter)).map(line -> line.substring(parameter.length()+1)).findFirst().get();
        } catch (FileNotFoundException e) {
            Log.getLogger().addLog("File di configurazione non trovato", Level.SEVERE,this.getClass().getName(),"getParameter");
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
            e.printStackTrace();
        }

    }

    public void printContent()
    {
        try {
            reader = new FileReader(file);
            input = new BufferedReader(reader);
            input.lines().forEach(line-> System.out.println(line));
        } catch (FileNotFoundException e) {
            Log.getLogger().addLog(e.getMessage(),Level.SEVERE,this.getClass().getName(),"printContent");
        }finally {
            closeBuffer();
        }

    }

    public void setFileReader(String file)
    {
        this.file = file;
    }

}

