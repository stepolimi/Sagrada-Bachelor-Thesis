package it.polimi.ingsw.server.set.up;


import java.io.*;
import java.util.Optional;

import static it.polimi.ingsw.server.costants.LogConstants.*;
import static it.polimi.ingsw.server.costants.SetupConstants.CONFIGURATION_FILE;

public class TakeDataFile {
    public TakeDataFile() {
    }

    public String getParameter(String parameter)   {
        String result = null;
        InputStreamReader streamReader = new InputStreamReader(getClass().getResourceAsStream(CONFIGURATION_FILE));
        try ( BufferedReader input = new BufferedReader(streamReader)){
            Optional<String> optional =  input.lines()
                    .filter(line-> line.contains(parameter))
                    .map(line -> line.substring(parameter.length()+1))
                    .findFirst();
            if(optional.isPresent())
                result = optional.get();
            streamReader.close();
        } catch (IOException e) {
            System.out.println(CONFIGURATION_FILE_ERROR);
        }
        return result;
    }

}

