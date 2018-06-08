package it.polimi.ingsw.server.model.rules;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

public class AdjacentRule implements InsertionRule {
    private static final String restriction = "Adjacent";

    public boolean checkRule(int x, int y, Dice dice, Schema sch){
        List <Dice> nearDices = sch.nearDice(x, y);

        if(sch.isEmpty()) {
            if(x == 0 || y == 0 || x == 3 || y == 4)
                return true;
            System.out.println("Adjacent rule error");
            return false;
        }

        for(Dice d: nearDices)
            if(d != null)
                return true;
        System.out.println("Adjacent rule error");
        return false;
    }

    public String getRestriction() { return restriction; }

}
