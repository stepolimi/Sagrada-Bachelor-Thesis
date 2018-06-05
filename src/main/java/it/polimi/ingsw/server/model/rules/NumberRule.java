package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

//rule for checking the number restrictions

public class NumberRule implements InsertionRule {
    private static final String restriction = "Number";

    public boolean checkRule(int x, int y, Dice dice, Schema sch){
        if(sch.getTable(x,y).getNumber() == 0)
            return true;
        if(sch.getTable(x,y).getNumber() == dice.getValue())
            return true;
        System.out.println("Number rule error");
        return false;
    }

    public String getRestriction() { return restriction; }
}
