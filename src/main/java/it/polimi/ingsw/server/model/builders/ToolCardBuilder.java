package it.polimi.ingsw.server.model.builders;

import com.google.gson.Gson;
import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.model.cards.tool.cards.ToolCard;
import it.polimi.ingsw.server.set.up.TakeDataFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.LogConstants.BUILD_TOOL_CARD;
import static it.polimi.ingsw.server.costants.NameConstants.TOOL_CARD_PATH;
import static it.polimi.ingsw.server.costants.SetupConstants.CONFIGURATION_FILE;


public class ToolCardBuilder {
    private ToolCardBuilder(){}

    /**
     * Creates the specified tool card from file and returns it.
     * @param n is the number of the tool card that is going to be created.
     * @return the created tool card.
     */
    public static ToolCard buildToolCard(int n){
        TakeDataFile config = new TakeDataFile(CONFIGURATION_FILE);
        String pathToolCard = config.getParameter(TOOL_CARD_PATH);
        ToolCard toolCard = new ToolCard();
        final String filePath = pathToolCard + 4;
        Gson g = new Gson();

        InputStream is = ToolCardBuilder.class.getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String tool;
            tool = reader.readLine();
            toolCard = g.fromJson(tool,ToolCard.class);
        }
        catch(IOException e){
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE,ToolCardBuilder.class.getName(),BUILD_TOOL_CARD);
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                Log.getLogger().addLog(e.getMessage(),Level.SEVERE,ToolCardBuilder.class.getName(),BUILD_TOOL_CARD);
            }
        }
        return toolCard;
    }
}
