package it.polimi.ingsw.rules;

import it.polimi.ingsw.Dice;
import it.polimi.ingsw.Player;

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
