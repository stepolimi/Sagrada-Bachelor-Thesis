package it.polimi.ingsw.Server.Model.rules;

import it.polimi.ingsw.Server.Model.board.Dice;
import it.polimi.ingsw.Server.Model.board.Schema;

public interface InsertionRule {

    public boolean checkRule(int toolCardNumber, int x, int y, Dice dice, Schema sch);


}
