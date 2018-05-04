package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Colour;
import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.DiceSpace;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;


public class DiceSpaceTest {


    Dice d1 = new Dice(Colour.ANSI_PURPLE,3);

    ArrayList<Dice> lista = new ArrayList<Dice>();

    DiceSpace diceSpace = new DiceSpace(lista);



    public void insertion(){
        diceSpace.insertDice(d1);

    }

    public void removing(){

        if(diceSpace.getListDice() != null)
            diceSpace.removeDice(0);
    }

    @Test
    public void correct_insert() {
        insertion();
        assertEquals(1, diceSpace.getListDice().size());
    }

    @Test
    public void correct_remove(){
        removing();
        assertEquals(0, diceSpace.getListDice().size());
    }


}
