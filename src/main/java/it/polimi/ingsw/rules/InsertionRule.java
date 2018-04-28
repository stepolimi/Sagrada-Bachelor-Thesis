package it.polimi.ingsw.rules;

import it.polimi.ingsw.Dice;
import it.polimi.ingsw.Player;

public interface InsertionRule {

    public boolean checkRule(Player currentPlayer, int toolCardNumber, int x, int y, Dice dice);


}
