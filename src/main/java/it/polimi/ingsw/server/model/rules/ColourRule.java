package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

//rule for checking the colour restrictions

public class ColourRule implements InsertionRule {

    public boolean checkRule(int toolCardNumber, int x, int y, Dice dice, Schema sch){
        if(sch.getTable(x,y).getC() == null)
            return true;
        if(toolCardNumber == 2)
            return true;
        if(sch.getTable(x,y).getC() == dice.getColour())
            return true;
        return false;
    }

}
