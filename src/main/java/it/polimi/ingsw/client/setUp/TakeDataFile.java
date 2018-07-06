package it.polimi.ingsw.client.setUp;

import it.polimi.ingsw.client.view.Message;
import it.polimi.ingsw.client.view.TypeMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import static it.polimi.ingsw.client.constants.SetupConstants.CONFIGURATION_FILE;
import static it.polimi.ingsw.client.constants.printCostants.CONFIGURATION_FILE_NOT_FOUND;

public class TakeDataFile {
        public String getParameter(String parameter){
            String result = null;
            InputStreamReader streamReader = new InputStreamReader(getClass().getResourceAsStream(CONFIGURATION_FILE));
            try(BufferedReader input = new BufferedReader(streamReader)) {
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

