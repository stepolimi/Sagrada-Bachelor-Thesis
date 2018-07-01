package it.polimi.ingsw.client.setUp;

import it.polimi.ingsw.client.view.Message;
import it.polimi.ingsw.client.view.TypeMessage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TakeDataFile {
        FileReader reader;
        BufferedReader input;
        String file;
        public TakeDataFile(String file)
        {
            this.file = file;
        }

        public String getParameter(String parameter){
            String result = null;
            try {
                reader = new FileReader(file);
                input = new BufferedReader(reader);
                result =  input.lines().filter(line-> line.contains(parameter)).map(line -> line.substring(parameter.length()+1)).findFirst().get();
            } catch (FileNotFoundException e) {
                Message.print("File di configurazione non trovato", TypeMessage.ERROR_MESSAGE);
            }finally {
                closeBuffer();
            }

            return result;
        }

        public void printContent()
        {
            try {
                reader = new FileReader(file);
                input = new BufferedReader(reader);
                input.lines().forEach(line-> System.out.println(line));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                closeBuffer();
            }

        }

    private void closeBuffer(){
        try {
            reader.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

        public void setFileReader(String file)
        {
            this.file = file;
        }

}

