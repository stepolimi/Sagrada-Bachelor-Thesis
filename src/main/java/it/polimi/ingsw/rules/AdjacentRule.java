package it.polimi.ingsw.rules;

import it.polimi.ingsw.Dice;
import it.polimi.ingsw.Player;

import java.util.List;

public class AdjacentRule implements InsertionRule {

    public boolean checkRule(Player currentPlayer, int toolCardNumber, int x, int y, Dice dice){
        List <Dice> nearDices = currentPlayer.getSchema().nearDice(x, y);

        if(currentPlayer.getSchema().isEmpty() == true) {
            if(x == 0 || y == 0 || x == 3 || y == 4)
                return true;
            return false;
        }

        if(toolCardNumber == 9){
            if(x == 0 || y == 0 || x == 3 || y == 4) {
                for (Dice d : nearDices)
                    if (d != null)
                        return false;
                return true;
            }
            return false;
        }

        return adjacentDicesCheck(nearDices,dice) && nearDicesCheck(nearDices);
    }


    public boolean adjacentDicesCheck(List <Dice> nearDices, Dice dice){
        for(int i= 1; i < 4; i= i+2 )
            if(nearDices.get(i) != null)
                if(nearDices.get(i).getValue() == dice.getValue() || nearDices.get(i).getcolour() == dice.getcolour())
                    return false;
        for(int i = 4; i < 7; i= i+2 )
            if(nearDices.get(i) != null)
                if(nearDices.get(i).getValue() == dice.getValue() || nearDices.get(i).getcolour() == dice.getcolour())
                    return false;
        return true;
    }

    public boolean nearDicesCheck (List <Dice> nearDices){
        for(Dice d: nearDices)
            if(d != null)
                return true;
        return false;
    }
}
