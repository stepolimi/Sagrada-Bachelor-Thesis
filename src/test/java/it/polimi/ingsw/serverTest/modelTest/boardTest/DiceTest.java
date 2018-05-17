package it.polimi.ingsw.serverTest.modelTest.boardTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;



public class DiceTest {

    public boolean CorrectDice(int n)
    {
        if((n > 0) && (n < 7))
            return true;
        else return false;
    }


    @Test
    public void  CorrectDiceFormat(){

        Dice d =new Dice(Colour.ANSI_GREEN, 6);
        assertTrue("Dice's number is a right value", CorrectDice(d.getValue()));
    }

    @Test
    public void WrongDiceFormat(){
        Dice d =new Dice(Colour.ANSI_GREEN, 10);
        assertFalse("Number is wrong. " + d.getValue() + "is not a dice's value", CorrectDice(d.getValue()));

    }

}
