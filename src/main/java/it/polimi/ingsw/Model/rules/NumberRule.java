package it.polimi.ingsw.Model.rules;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;

//rule for checking the number restrictions

public class NumberRule implements InsertionRule {

    public boolean checkRule(Player currentPlayer, int toolCardNumber, int x, int y, Dice dice){
        if(currentPlayer.getSchema().getTable(x,y).getNumber() == 0)
            return true;
        if(toolCardNumber == 3)
            return true;
        if(currentPlayer.getSchema().getTable(x,y).getNumber() == dice.getValue())
            return true;
        return false;
    }
}
