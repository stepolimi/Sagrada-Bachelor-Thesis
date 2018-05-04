package it.polimi.ingsw.Model.rules;

import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;

public interface InsertionRule {

    public boolean checkRule(Player currentPlayer, int toolCardNumber, int x, int y, Dice dice);


}
