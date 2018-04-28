package it.polimi.ingsw.rules;

import it.polimi.ingsw.Dice;
import it.polimi.ingsw.Player;

public class EmptyRule implements InsertionRule{

    public boolean checkRule(Player currentPlayer, int toolCardNumber, int x, int y, Dice dice) {
        if(currentPlayer.getSchema().getTable(x,y).getDice()== null)
            return true;
        return false;
    }
}
