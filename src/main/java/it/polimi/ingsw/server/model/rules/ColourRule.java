package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.COLOUR_RESTRICTION;


public class ColourRule implements InsertionRule {
    private static String restriction = COLOUR_RESTRICTION;

    /**
     * Checks if the dice's colour is compatible with the colour restriction of the specified box of the schema.
     * @param x is the row of the schema where the dice will eventually be inserted.
     * @param y is the column of the schema where the dice will eventually be inserted.
     * @param dice is the dice that will eventually be inserted.
     * @param sch is the schema where the dice will eventually be inserted.
     * @return true if the dice's colour is compatible with the box's colour restriction, false otherwise.
     */
    public boolean checkRule(int x, int y, Dice dice, Schema sch) {
        if (sch.getTable(x, y).getC() == null)
            return true;
        if (sch.getTable(x, y).getC() == dice.getColour())
            return true;
        Log.getLogger().addLog("Colour rule error", Level.INFO,this.getClass().getName(),"checkRule");
        return false;
    }

    public String getRestriction() {
        return restriction;
    }

}
