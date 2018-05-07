package it.polimi.ingsw.Model.rules;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Schema;

//rule for checking the number restrictions

public class NumberRule implements InsertionRule {

    public boolean checkRule(int toolCardNumber, int x, int y, Dice dice, Schema sch){
        if(sch.getTable(x,y).getNumber() == 0)
            return true;
        if(toolCardNumber == 3)
            return true;
        if(sch.getTable(x,y).getNumber() == dice.getValue())
            return true;
        return false;
    }
}
