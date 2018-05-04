package it.polimi.ingsw.Model.rules;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;

//rule for checking the colour restrictions

public class ColourRule implements InsertionRule {

    public boolean checkRule(Player currentPlayer, int toolCardNumber, int x, int y, Dice dice){
        if(currentPlayer.getSchema().getTable(x,y).getC() == null)
            return true;
        if(toolCardNumber == 2)
            return true;
        if(currentPlayer.getSchema().getTable(x,y).getC() == dice.getcolour())
            return true;
        return false;
    }

}
