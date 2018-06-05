package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;

import java.util.ArrayList;
import java.util.List;

//manager for checking all the rules for a dice insertion in a schema

public class RulesManager {
    public static RulesManager instance = null;
    private List<InsertionRule> rules = new ArrayList<InsertionRule>();

    private RulesManager(){
        rules.add(new AdjacentRule());
        rules.add(new ColourRule());
        rules.add(new NumberRule());
        rules.add(new EmptyRule());
    }

    public static RulesManager getRulesManager(){
        if(instance == null)
            instance = new RulesManager();
        return instance;
    }

    // toolCardNumber will be the index of the eventual tool card used by the user; it will be =0 if no tool card was used

    public boolean checkRules(ToolCard toolCard, int x, int y, Dice dice, Schema sch){
        boolean valid = true;
        for(InsertionRule r: rules){
            if (toolCard != null) {
                if (!toolCard.getIgnoredRules().contains(r.getRestriction()))
                    valid = valid && r.checkRule(x, y, dice, sch);
            }
            else
                valid = valid && r.checkRule(x, y, dice, sch);
        }
        return valid;
    }
}
