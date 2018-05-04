package it.polimi.ingsw.Model.rules;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;

public class EmptyRule implements InsertionRule{

    public boolean checkRule(Player currentPlayer, int toolCardNumber, int x, int y, Dice dice) {
        if(currentPlayer.getSchema().getTable(x,y).getDice()== null)
            return true;
        return false;
    }
}
