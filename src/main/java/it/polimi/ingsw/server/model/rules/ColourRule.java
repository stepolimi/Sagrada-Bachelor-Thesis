package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

//rule for checking the colour restrictions

public class ColourRule implements InsertionRule {
    private static final String restriction = "Colour";

    public boolean checkRule(int x, int y, Dice dice, Schema sch){
        if(sch.getTable(x,y).getC() == null)
            return true;
        if(sch.getTable(x,y).getC() == dice.getColour())
            return true;
        System.out.println("Colour rule error");
        return false;
    }

    public String getRestriction() { return restriction; }

}
