package it.polimi.ingsw.Server.Model.rules;

import it.polimi.ingsw.Server.Model.board.Dice;
import it.polimi.ingsw.Server.Model.board.Schema;

public class EmptyRule implements InsertionRule{

    public boolean checkRule(int toolCardNumber, int x, int y, Dice dice, Schema sch) {
        if(sch.getTable(x,y).getDice()== null)
            return true;
        return false;
    }
}
