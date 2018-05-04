package it.polimi.ingsw.Model.Cards;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Colour;
import it.polimi.ingsw.Model.Schema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrivateObjective {
    private String name;
    private String description;
    private Colour c;

    public PrivateObjective PrivateInit (int n) throws IOException {   //costructs the Schema obj from file

        PrivateObjective sch = new PrivateObjective();
        final String filePath = new String("src/main/data/PrivCard/" + n + ".json");  //import every schema from
        //json file form /src/main/data/Schema/i.json
        Gson g = new Gson();

        FileReader f;
        f = new FileReader(filePath);

        BufferedReader b;
        b = new BufferedReader(f);
        try {
            String sc;
            sc = b.readLine();

            sch = g.fromJson(sc, PrivateObjective.class);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally {
            b.close();
        }
        return sch;
    }

    public int ScoreCard(Schema sch){
        int score = 0;
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                if(sch.getTable(i,j).getDice() != null)
                    if(sch.getTable(i,j).getDice().getcolour().equals(this.c))
                        score++;
            }
        }
        return score;
    }
}
