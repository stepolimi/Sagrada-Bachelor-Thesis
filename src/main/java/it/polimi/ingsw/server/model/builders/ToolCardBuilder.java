package it.polimi.ingsw.server.model.builders;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ToolCardBuilder {
    private ToolCardBuilder(){}

    public static ToolCard buildToolCard(int n){
        ToolCard toolCard = new ToolCard();
        final String filePath = "/data/toolCard/ToolCard" + n;
        Gson g = new Gson();

        InputStream is = ToolCardBuilder.class.getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String tool;
            tool = reader.readLine();
            toolCard = g.fromJson(tool,ToolCard.class);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toolCard;
    }
}
