package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Colour;
import it.polimi.ingsw.Model.Schema;

public class PrivateObjective {
    private String name;
    private String description;
    private Colour c;

    public PrivateObjective(String name, String description, Colour c) {
        this.name = name;
        this.description = description;
        this.c = c;
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
