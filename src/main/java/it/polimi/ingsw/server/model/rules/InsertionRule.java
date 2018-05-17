package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

public interface InsertionRule {

    public boolean checkRule(int toolCardNumber, int x, int y, Dice dice, Schema sch);


}
