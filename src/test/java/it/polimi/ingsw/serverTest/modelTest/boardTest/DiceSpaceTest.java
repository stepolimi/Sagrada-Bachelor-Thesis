package it.polimi.ingsw.serverTest.modelTest.boardTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.DiceSpace;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DiceSpaceTest {


    Dice d1 = new Dice(Colour.ANSI_PURPLE,3);

    ArrayList<Dice> lista = new ArrayList<Dice>();

    DiceSpace diceSpace = new DiceSpace(lista);



    public void insertion(){
        diceSpace.insertDice(d1);

    }

    public Dice removing(){

        return diceSpace.removeDice(1);
    }

    @Test
    public void correct_insert() {
        insertion();
        assertEquals(1, diceSpace.getListDice().size());
    }

    @Test
    public void correct_remove(){
        insertion();
        assertTrue(removing() == d1);
        assertEquals(0, diceSpace.getListDice().size());

        assertTrue(removing() == null);

    }



}
