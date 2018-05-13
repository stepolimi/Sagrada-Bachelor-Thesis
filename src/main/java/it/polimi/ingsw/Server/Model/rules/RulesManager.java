package it.polimi.ingsw.Server.Model.rules;

import it.polimi.ingsw.Server.Model.board.Dice;
import it.polimi.ingsw.Server.Model.board.Schema;

import java.util.ArrayList;
import java.util.List;

//manager for checking all the rules for a dice insertion in a schema

public class RulesManager {
    private List<InsertionRule> rules = new ArrayList<InsertionRule>();

    public RulesManager(){
        rules.add(new AdjacentRule());
        rules.add(new ColourRule());
        rules.add(new NumberRule());
        rules.add(new EmptyRule());
    }

    // toolCardNumber will be the index of the eventual tool card used by the user; it will be =0 if no tool card was used

    public boolean checkRules(int toolCardNumber, int x, int y, Dice dice, Schema sch){
        boolean valid = true;
        for(InsertionRule r: rules){
            valid = valid && r.checkRule(toolCardNumber, x, y, dice, sch);
        }
        return valid;
    }
}