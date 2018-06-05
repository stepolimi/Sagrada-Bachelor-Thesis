package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

public class EmptyRule implements InsertionRule{
    private static final String restriction = "EmptyBox";

    public boolean checkRule(int x, int y, Dice dice, Schema sch) {
        if(sch.getTable(x,y).getDice()== null)
            return true;
        System.out.println("Empty rule error");
        return false;
    }

    public String getRestriction() { return restriction; }
}
