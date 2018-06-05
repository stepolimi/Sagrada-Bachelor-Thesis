package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

public interface InsertionRule {

    boolean checkRule(int x, int y, Dice dice, Schema sch);

    String getRestriction();
}
