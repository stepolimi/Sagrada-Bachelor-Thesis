package it.polimi.ingsw.client.setUp;

import it.polimi.ingsw.client.view.Message;
import it.polimi.ingsw.client.view.TypeMessage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static it.polimi.ingsw.client.constants.printCostants.CONFIGURATION_FILE_NOT_FOUND;

public class TakeDataFile {
        private FileReader reader;
        private BufferedReader input;
        private String file;
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
                Message.print(CONFIGURATION_FILE_NOT_FOUND, TypeMessage.ERROR_MESSAGE);
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

