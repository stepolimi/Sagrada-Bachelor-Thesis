package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

public class EmptyRule implements InsertionRule{

    public boolean checkRule(int toolCardNumber, int x, int y, Dice dice, Schema sch) {
        if(sch.getTable(x,y).getDice()== null)
            return true;
        return false;
    }
}
