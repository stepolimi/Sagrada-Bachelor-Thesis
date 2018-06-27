package it.polimi.ingsw.server.model.builders;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.cards.PrivateObjective;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PrivateObjectiveBuilder {
    private PrivateObjectiveBuilder(){}

    public static PrivateObjective buildPrivateObjective(int n){
        PrivateObjective privateObjective = new PrivateObjective();
        final String filePath = "/data/privCard/" + n + ".json";
        Gson g = new Gson();

        InputStream is = ToolCardBuilder.class.getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String tool;
            tool = reader.readLine();
            privateObjective = g.fromJson(tool,PrivateObjective.class);
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
        return privateObjective;
    }
}
