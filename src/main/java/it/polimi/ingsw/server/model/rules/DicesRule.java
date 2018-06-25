package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.DICES_RESTRICTION;

public class DicesRule implements InsertionRule {
    private static String restriction = DICES_RESTRICTION;

    public boolean checkRule(int x, int y, Dice dice, Schema sch) {
        List<Dice> nearDices = sch.nearDice(x, y);

        for (int i = 1; i < 4; i = i + 2)
            if (nearDices.get(i) != null)
                if (nearDices.get(i).getValue() == dice.getValue() || nearDices.get(i).getColour() == dice.getColour()) {
                    System.out.println("Adjacent rule error");
                    return false;
                }
        for (int i = 4; i < 7; i = i + 2)
            if (nearDices.get(i) != null)
                if (nearDices.get(i).getValue() == dice.getValue() || nearDices.get(i).getColour() == dice.getColour()) {
                    System.out.println("Adjacent rule error");
                    return false;
                }
        return true;
    }

    public String getRestriction() {
        return restriction;
    }
}
