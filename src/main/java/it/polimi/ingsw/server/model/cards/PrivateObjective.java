package it.polimi.ingsw.server.model.cards;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Schema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrivateObjective {
    private String name;
    private String description;
    private Colour c;

    public PrivateObjective PrivateInit (int n) throws IOException {   //constructs the private objective from file

        PrivateObjective sch = new PrivateObjective();
        final String filePath = new String("src/main/data/PrivCard/" + n + ".json");  //import every private objective from
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
                    if(sch.getTable(i,j).getDice().getColour().equals(this.c))
                        score+=sch.getTable(i,j).getDice().getValue();
            }
        }
        return score;
    }

    public String getColour(){
        if(c == Colour.ANSI_GREEN){ return "verde"; }
        else if(c == Colour.ANSI_BLUE) { return "blu"; }
        else if(c == Colour.ANSI_RED){ return "rosso"; }
        else if(c == Colour.ANSI_PURPLE) { return "viola"; }
        else if(c == Colour.ANSI_YELLOW) { return "giallo"; }
        return "";
    }

    @Override
    public String toString(){
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src= src + "|" +  this.name.toString() + "\n" + "|" + this.description + "\n" + "|" + "points: " + this.c + "\n";
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

    }

    public void dump(){
        System.out.println(this);
    }


}
