package it.polimi.ingsw.serverTest.modelTest.cardsTest.objCardTest;

import it.polimi.ingsw.server.model.cards.objectiveCards.CoupleSetObj;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static org.junit.jupiter.api.Assertions.assertEquals;
//test verify the right calcolus of scoreCard funnction. test made adding dices in a schema WITHOUT restriction. to complete


public class CoupleCTest {

    private Schema s;

    public void insertDice(){
        try {
            s = buildSchema(1);
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




        s.silentInsertDice(0, 0, d1);
        s.silentInsertDice(0, 1, d2);
        s.silentInsertDice(0, 2, d3);
        s.silentInsertDice(0, 3, d4);
        s.silentInsertDice(0, 4, d5);
        s.silentInsertDice(2, 0, d6);
        s.silentInsertDice(2, 1, d7);
        s.silentInsertDice(2, 2, d8);
        s.silentInsertDice(2, 3, d9);
        s.silentInsertDice(2, 4, d10);

    }

    @Test
    void CorrectScore() {


        insertDice();
        CoupleSetObj card = new CoupleSetObj("name", "description", 1, 2 );
        card.dump();


        assertEquals(4, card.scoreCard(s), "correct calculus");
    }
    @Test
    public void null_score(){

        try {
            s = buildSchema(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CoupleSetObj card = new CoupleSetObj("name", "description", 1, 2 );
        card.dump();


        assertEquals(0, card.scoreCard(s), "result is 0");
    }


}