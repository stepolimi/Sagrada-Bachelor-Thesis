package it.polimi.ingsw.server.model.cards.objCards;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.Arrays;

public class SetObj extends ObjectiveCard {

    private String name;
    private String description;
    private int points;

    public SetObj(String name, String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }


    @Override
    public int ScoreCard(Schema sch) {

        int count[];
        count = new int[6];


        if(points == 4){


            for(int i=0; i < 4; i++){
                for(int j=0; j<5; j++){
                    if (sch.getTable(i, j).getDice() != null) {
                        if(sch.getTable(i,j).getDice().getcolour() ==  Colour.ANSI_RED)
                            count[0]++;
                        if(sch.getTable(i,j).getDice().getcolour() == Colour.ANSI_BLUE)
                            count[1]++;
                        if(sch.getTable(i,j).getDice().getcolour() == Colour.ANSI_GREEN)
                            count[2]++;
                        if(sch.getTable(i,j).getDice().getcolour() == Colour.ANSI_PURPLE)
                            count[3]++;
                        if(sch.getTable(i,j).getDice().getcolour() == Colour.ANSI_YELLOW)
                            count[4]++;
                }

                    }

            }
            System.out.println(Arrays.toString(count));

            return 4*Math.min(count[0], Math.min(count[1], Math.min(count[2], Math.min(count[3],
                    Math.min(count[4], count[4])))));


        }
        else if (points == 5){
                for(int i=0; i < 4; i++){
                    for(int j=0; j<5; j++) {
                        if (sch.getTable(i, j).getDice() != null) {
                            if (sch.getTable(i, j).getDice().getValue() == 1)
                                count[0]++;
                            if (sch.getTable(i, j).getDice().getValue() == 2)
                                count[1]++;
                            if (sch.getTable(i, j).getDice().getValue() == 3)
                                count[2]++;
                            if (sch.getTable(i, j).getDice().getValue() == 4)
                                count[3]++;
                            if (sch.getTable(i, j).getDice().getValue() == 5)
                                count[4]++;
                            if (sch.getTable(i, j).getDice().getValue() == 6)
                                count[5]++;
                        }
                    }
            }
            System.out.println(Arrays.toString(count));


            return 5*Math.min(count[0], Math.min(count[1], Math.min(count[2], Math.min(count[3],
                    Math.min(count[4], count[5])))));
        }
        else return 0;
    }


    @Override
    public String toString(){
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src= src + "|" + this.name.toString() + "\n"  + "|" + this.description + "\n" + "|" + "points: " + this.points + "\n";
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

    }

    public void dump(){
        System.out.println(this);
    }

}
