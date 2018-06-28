package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import static it.polimi.ingsw.server.costants.Constants.NUMBER_RESTRICTION;


public class NumberRule implements InsertionRule {
    private static String restriction = NUMBER_RESTRICTION;

    /**
     * Checks if the dice's value is compatible with the number restriction of the specified box of the schema.
     * @param x is the row of the box of the schema.
     * @param y is the column of the box of the schema.
     * @param dice is the dice.
     * @param sch is the schema.
     * @return true if the dice's value is compatible with the box's number restriction, false otherwise.
     */
    public boolean checkRule(int x, int y, Dice dice, Schema sch) {
        if (sch.getTable(x, y).getNumber() == 0)
            return true;
        if (sch.getTable(x, y).getNumber() == dice.getValue())
            return true;
        System.out.println("Number rule error");
        return false;
    }

    public String getRestriction() {
        return restriction;
    }
}
