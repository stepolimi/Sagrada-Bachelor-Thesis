package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

public class AdjacentRule implements InsertionRule {
    private static final String restriction = "Adjacent";

    public boolean checkRule(int x, int y, Dice dice, Schema sch){
        List <Dice> nearDices = sch.nearDice(x, y);

        if(sch.isEmpty() == true) {
            if(x == 0 || y == 0 || x == 3 || y == 4)
                return true;
            System.out.println("Adjacent rule error");
            return false;
        }

        return adjacentDicesCheck(nearDices,dice) && nearDicesCheck(nearDices);
    }


    private boolean adjacentDicesCheck(List <Dice> nearDices, Dice dice){
        for(int i= 1; i < 4; i= i+2 )
            if(nearDices.get(i) != null)
                if(nearDices.get(i).getValue() == dice.getValue() || nearDices.get(i).getColour() == dice.getColour()) {
                    System.out.println("Adjacent rule error");
                    return false;
                }
        for(int i = 4; i < 7; i= i+2 )
            if(nearDices.get(i) != null)
                if(nearDices.get(i).getValue() == dice.getValue() || nearDices.get(i).getColour() == dice.getColour()) {
                    System.out.println("Adjacent rule error");
                    return false;
                }
        return true;
    }

    private boolean nearDicesCheck (List <Dice> nearDices){
        for(Dice d: nearDices)
            if(d != null)
                return true;
        System.out.println("Adjacent rule error");
        return false;
    }

    public String getRestriction() { return restriction; }

}
