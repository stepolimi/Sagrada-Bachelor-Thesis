package it.polimi.ingsw.client.setUp;

import it.polimi.ingsw.client.view.Message;
import it.polimi.ingsw.client.view.TypeMessage;

import java.io.*;
import java.util.Optional;

import static it.polimi.ingsw.client.constants.NameConstants.CONFIG_FILE;
import static it.polimi.ingsw.client.constants.printCostants.CONFIGURATION_FILE_NOT_FOUND;

public class TakeDataFile {
        public String getParameter(String parameter){
            String result = null;
            String configFile  = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            configFile = configFile.substring(0, configFile.lastIndexOf("/"));

            try(InputStream inputStream = new FileInputStream(configFile+CONFIG_FILE);InputStreamReader streamReader = new InputStreamReader(inputStream);BufferedReader input = new BufferedReader(streamReader)) {
                Optional<String> optional =  input.lines()
                        .filter(line-> line.contains(parameter))
                        .map(line -> line.substring(parameter.length()+1))
                        .findFirst();
                if(optional.isPresent())
                    result = optional.get();

            } catch (IOException e) {
                Message.print(CONFIGURATION_FILE_NOT_FOUND, TypeMessage.ERROR_MESSAGE);
            }

            return result;
        }


}

