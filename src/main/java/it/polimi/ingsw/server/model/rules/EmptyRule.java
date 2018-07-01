package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.EMPTY_RESTRICTION;

public class EmptyRule implements InsertionRule {
    private static String restriction = EMPTY_RESTRICTION;

    /**
     * Checks if the specified box of the specified schema is empty.
     * @param x is the row of the box of the schema.
     * @param y is the column of the box of the schema.
     * @param dice is the dice.
     * @param sch is the schema.
     * @return true if the box is empty, false otherwise.
     */
    public boolean checkRule(int x, int y, Dice dice, Schema sch) {
        if (sch.getTable(x, y).getDice() == null)
            return true;
        Log.getLogger().addLog("Empty rule error", Level.INFO,this.getClass().getName(),"checkRule");
        return false;
    }

    public String getRestriction() {
        return restriction;
    }
}
