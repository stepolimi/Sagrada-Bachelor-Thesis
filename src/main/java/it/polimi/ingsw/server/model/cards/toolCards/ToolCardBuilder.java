package it.polimi.ingsw.server.model.cards.toolCards;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ToolCardBuilder {
    private static ToolCardBuilder instance = null;

    private ToolCardBuilder(){}

    public static ToolCardBuilder getToolCardBuilder(){
        if(instance == null)
            instance = new ToolCardBuilder();
        return instance;
    }

    public ToolCard buildToolCard(int n){
        ToolCard toolCard = new ToolCard();
        final String filePath = "src/main/data/toolCard/ToolCard" + 1;
        Gson g = new Gson();

        FileReader f = null;
        try {
            f = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader b;
        b = new BufferedReader(f);
        try {
            String tool;
            tool = b.readLine();
            toolCard = g.fromJson(tool,ToolCard.class);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally {
            try {
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toolCard;
    }
}
