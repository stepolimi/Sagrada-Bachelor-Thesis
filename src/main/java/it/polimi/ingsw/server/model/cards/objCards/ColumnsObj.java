package it.polimi.ingsw.server.model.cards.objCards;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.ArrayList;
import java.util.List;

public class ColumnsObj extends ObjectiveCard {

    private String name;
    private String description;
    private int points;

    public ColumnsObj(String name, String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }

    @Override
    public int ScoreCard(Schema sch) {

        int score = 0;

        List<Dice> container = new ArrayList<Dice>();
        for(int j=0; j < 5; j++){
            for(int i=0; i < 4; i++){
                if(sch.getTable(i,j).getDice() != null)
                    container.add(sch.getTable(i,j).getDice());
            }

            if(this.areDifferent(container, this.points) && container.size() == 4) {
                score = score + this.points;
                container.clear();;
            }
        }

        return score;
    }


    public boolean areDifferent(List<Dice> container, int points) {
        if (points == 5) {
            for (int i = 0, j= 1; i < container.size()-1; i++, j++) {
                    if (container.get(i).getcolour().equals(container.get(j).getcolour()))
                        return false;
            }
            return true;
        }
        else if (points == 4){
            for (int i = 0, j= 1; i < container.size()-1; i++, j++) {
                    if (container.get(i).getValue() == (container.get(j).getValue())) {
                        return false;
                    }
            }
            return true;
        }

        else return false;
    }

    @Override
    public String getName(){ return this.name; }

    @Override
    public String toString(){
        String src = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        src= src + "|" +  this.name.toString() + "\n" + "|" + this.description + "\n" + "|" + "points: " + this.points + "\n";
        src = src + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        return src;

    }

    public void dump(){
        System.out.println(this);
    }


}
