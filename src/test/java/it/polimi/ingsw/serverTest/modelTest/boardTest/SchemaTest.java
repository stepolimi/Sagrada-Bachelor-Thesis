package it.polimi.ingsw.serverTest.modelTest.boardTest;

import com.google.gson.Gson;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;
import org.junit.jupiter.api.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SchemaTest {
    Gson g = new Gson();
    String sch = "{\"name\":\"Kaleidoscopic Dream\",\"difficult\":4,\"table\":[[{\"c\":\"ANSI_YELLOW\",\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":1,\"full\":false}],[{\"c\":\"ANSI_GREEN\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":5,\"full\":false},{\"number\":0,\"full\":false},{\"number\":4,\"full\":false}],[{\"number\":3,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_GREEN\",\"number\":0,\"full\":false}],[{\"number\":2,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"c\":\"ANSI_YELLOW\",\"number\":0,\"full\":false}]]}\n" ;
    Schema s;

    @Test
    void DicePlacement()
    {
        s = g.fromJson(sch,Schema.class);
        Dice d = new Dice(Colour.ANSI_YELLOW,6);
        s.insertDice(0,0,d);
        assertTrue(s.getTable(0,0).getDice()!=null,"The box is not empty");
        try {
            s.testRemoveDice(0,0);
            s.removeDice(0,0);
            assertTrue(s.getTable(0,0).getDice()==null,"The box is empty");
            assertTrue(s.removeDice(0,0)==null,"The box is empty");
        } catch (RemoveDiceException e) {
            e.printStackTrace();
        }

    }



    @Test
    void NearDicesAngle()
    {
        s = g.fromJson(sch,Schema.class);

        Dice d1 = new Dice(Colour.ANSI_RED,1);
        Dice d2 = new Dice(Colour.ANSI_BLUE,6);
        Dice d3 = new Dice(Colour.ANSI_PURPLE,3);

        s.insertDice(0,1,d1);
        s.insertDice(1,1,d2);
        s.insertDice(1,0,d3);

        assertTrue(s.nearDice(0,0).contains(d1),"The die is near");
        assertTrue(s.nearDice(0,0).contains(d2),"The die is near");
        assertTrue(s.nearDice(0,0).contains(d3),"The die is near");
        try {
            s.testRemoveDice(1,0);
            s.removeDice(1,0);
        } catch (RemoveDiceException e) {
            e.printStackTrace();
        }
        assertFalse("The die is not near",s.nearDice(0,0).contains(d3));

    }

    // the list returned by nearDice must respect the order
    @Test
    void OrderDice()
    {
        List <Dice> nearDice;
        s = g.fromJson(sch,Schema.class);

        Dice d1 = new Dice(Colour.ANSI_PURPLE,3);
        Dice d2 = new Dice(Colour.ANSI_BLUE,4);
        Dice d3 = new Dice(Colour.ANSI_RED,5);
        Dice d4 = new Dice(Colour.ANSI_YELLOW,2);
        Dice d5 = new Dice(Colour.ANSI_RED,5);
        Dice d6 = new Dice(Colour.ANSI_RED,5);
        Dice d7 = new Dice(Colour.ANSI_BLUE,2);
        Dice d8 = new Dice(Colour.ANSI_RED,1);

        s.insertDice(0,0,d1);
        s.insertDice(0,1,d2);
        s.insertDice(0,2,d3);
        s.insertDice(1,0,d4);
        s.insertDice(1,2,d5);
        s.insertDice(2,0,d6);
        s.insertDice(2,1,d7);
        s.insertDice(2,2,d8);

        nearDice = s.nearDice(1,1);

        assertTrue(nearDice.get(0).equals(d1));
        assertTrue(nearDice.get(1).equals(d2));
        assertTrue(nearDice.get(2).equals(d3));
        assertTrue(nearDice.get(3).equals(d4));
        assertTrue(nearDice.get(4).equals(d5));
        assertTrue(nearDice.get(5).equals(d6));
        assertTrue(nearDice.get(6).equals(d7));
        assertTrue(nearDice.get(7).equals(d8));



    }

}
