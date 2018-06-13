package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import static it.polimi.ingsw.server.serverCostants.Constants.EMPTY_RESTRICTION;

public class EmptyRule implements InsertionRule {
    private static String restriction = EMPTY_RESTRICTION;

    public boolean checkRule(int x, int y, Dice dice, Schema sch) {
        if (sch.getTable(x, y).getDice() == null)
            return true;
        System.out.println("Empty rule error");
        return false;
    }

    public String getRestriction() {
        return restriction;
    }
}
