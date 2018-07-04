package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.cards.tool.cards.ToolCard;

import java.util.ArrayList;
import java.util.List;

public class RulesManager {
    private static RulesManager instance = null;
    private final List<InsertionRule> rules = new ArrayList<>();

    private RulesManager() {
        rules.add(new AdjacentRule());
        rules.add(new ColourRule());
        rules.add(new NumberRule());
        rules.add(new EmptyRule());
        rules.add(new DicesRule());
    }

    public static RulesManager getRulesManager() {
        if (instance == null)
            instance = new RulesManager();
        return instance;
    }

    /**
     * Checks all the rules for a dice's insertion in a schema.
     * @param toolCard is the eventual tool card that has been used.
     * @param x is the row of the schema where the dice will eventually be inserted.
     * @param y is the column of the schema where the dice will eventually be inserted.
     * @param dice is the dice that will eventually be inserted.
     * @param sch is the schema where the dice will eventually be inserted.
     * @return if it is possible to insert the dice in specified position.
     */
    public boolean checkRules(ToolCard toolCard, int x, int y, Dice dice, Schema sch) {
        boolean valid = true;
        for (InsertionRule r : rules) {
            if (toolCard != null) {
                if (!toolCard.getIgnoredRules().contains(r.getRestriction()))
                    valid = valid && r.checkRule(x, y, dice, sch);
            } else
                valid = valid && r.checkRule(x, y, dice, sch);
        }
        return valid;
    }
}
