package it.polimi.ingsw.serverTest.modelTest.cardsTest;
import com.google.gson.Gson;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.server.model.cards.PrivateObjective;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

//testing ONLY the correct calculus of score  IGNORING restriction of the current schema.

public class PrivateCardTest {

    Schema s = new Schema();

    public void insertDice(){
        try {
            s = s.schemaInit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dice d1 = new Dice(Colour.ANSI_GREEN, 1);
        Dice d2 = new Dice(Colour.ANSI_RED, 2);
        Dice d3 = new Dice(Colour.ANSI_YELLOW, 3);
        Dice d4 = new Dice(Colour.ANSI_BLUE, 4);
        Dice d5 = new Dice(Colour.ANSI_PURPLE, 5);
        Dice d6 = new Dice(Colour.ANSI_RED, 6);
        Dice d7 = new Dice(Colour.ANSI_PURPLE, 1);
        Dice d8 = new Dice(Colour.ANSI_YELLOW, 2);
        Dice d9 = new Dice(Colour.ANSI_GREEN, 3);
        Dice d10 = new Dice(Colour.ANSI_BLUE, 4);



        s.insertDice(0, 0, d1);
        s.insertDice(1, 0, d2);
        s.insertDice(2, 0, d3);
        s.insertDice(3, 0, d4);
        s.insertDice(0, 2, d5);
        s.insertDice(1, 2, d6);
        s.insertDice(2, 2, d7);
        s.insertDice(3, 2, d8);
        s.insertDice(3, 4, d9);
        s.insertDice(3, 3, d10);
    }
    @Test
    public void scoreColour() throws IOException {

        insertDice();

        PrivateObjective card = new PrivateObjective().PrivateInit(4);
        card.dump();
        assertEquals(6, card.scoreCard(s));
    }

    @Test
    public void null_score() throws IOException {
        try {
            s = s.schemaInit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrivateObjective card = new PrivateObjective().PrivateInit(4);
        card.dump();
        assertEquals(0, card.scoreCard(s), "result is 0");

    }
}
