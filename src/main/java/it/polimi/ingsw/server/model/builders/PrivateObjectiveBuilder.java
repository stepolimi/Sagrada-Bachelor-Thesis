package it.polimi.ingsw.server.model.builders;

import com.google.gson.Gson;
import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.model.cards.PrivateObjective;
import it.polimi.ingsw.server.set.up.TakeDataFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.JSON_EXTENSION;
import static it.polimi.ingsw.server.costants.LogConstants.BUILD_PRIVATE_OBJECTIVE;
import static it.polimi.ingsw.server.costants.NameConstants.PRIVATE_OBJECTIVE_PATH;
import static it.polimi.ingsw.server.costants.SetupConstants.CONFIGURATION_FILE;


public class PrivateObjectiveBuilder {
    private PrivateObjectiveBuilder(){}

    /**
     * Creates the specified private objective from file and returns it.
     * @param n is the number of the private objective that is going to be created.
     * @return the created private objectives.
     */
    public static PrivateObjective buildPrivateObjective(int n){
        TakeDataFile config = new TakeDataFile();
        String pathPrivateObjective = config.getParameter(PRIVATE_OBJECTIVE_PATH);

        PrivateObjective privateObjective = new PrivateObjective();
        final String filePath = pathPrivateObjective + n + JSON_EXTENSION;
        Gson g = new Gson();

        InputStream is = ToolCardBuilder.class.getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String tool;
            tool = reader.readLine();
            privateObjective = g.fromJson(tool,PrivateObjective.class);
        }
        catch(IOException e){
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE,PrivateObjectiveBuilder.class.getName(),BUILD_PRIVATE_OBJECTIVE);
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                Log.getLogger().addLog(e.getMessage(),Level.SEVERE,PrivateObjectiveBuilder.class.getName(),BUILD_PRIVATE_OBJECTIVE);
            }
        }
        return privateObjective;
    }
}
