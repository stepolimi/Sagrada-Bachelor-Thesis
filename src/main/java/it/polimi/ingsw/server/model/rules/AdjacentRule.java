package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.ADJACENT_RESTRICTION;

public class AdjacentRule implements InsertionRule {
    private static String restriction = ADJACENT_RESTRICTION;

    /**
     * Checks if there is at least one dice in the schema near the specified box or if the schema is empty.
     * @param x is the row of the box of the schema.
     * @param y is the column of the box of the schema.
     * @param dice is the dice.
     * @param sch is the schema.
     * @return true if there is at least one dice near the box of if it is empty, false otherwise.
     */
    public boolean checkRule(int x, int y, Dice dice, Schema sch) {
        List<Dice> nearDices = sch.nearDice(x, y);

        if (sch.isEmpty()) {
            if (x == 0 || y == 0 || x == 3 || y == 4)
                return true;
            System.out.println("Adjacent rule error");
            return false;
        }

        for (Dice d : nearDices)
            if (d != null)
                return true;
        System.out.println("Adjacent rule error");
        return false;
    }

    public String getRestriction() {
        return restriction;
    }

}
