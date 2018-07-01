package it.polimi.ingsw.serverTest.modelTest.cardsTest.objCardTest;

import it.polimi.ingsw.server.model.cards.objectiveCards.DiagonalObj;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DiagonalCTest {
    private Schema s;

    private void insertDice1() {
        try {
            s = buildSchema(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dice d1 = new Dice(Colour.ANSI_GREEN, 1);
        Dice d2 = new Dice(Colour.ANSI_GREEN, 2);
        Dice d3 = new Dice(Colour.ANSI_GREEN, 3);
        Dice d4 = new Dice(Colour.ANSI_GREEN, 4);
        Dice d5 = new Dice(Colour.ANSI_YELLOW, 5);
        Dice d6 = new Dice(Colour.ANSI_YELLOW, 6);
        Dice d7 = new Dice(Colour.ANSI_YELLOW, 1);
        Dice d8 = new Dice(Colour.ANSI_YELLOW, 2);
        Dice d9 = new Dice(Colour.ANSI_BLUE, 3);
        Dice d10 = new Dice(Colour.ANSI_BLUE, 4);

        s.silentInsertDice(0, 0, d1);
        s.silentInsertDice(0, 2, d2);
        s.silentInsertDice(0, 4, d3);
        s.silentInsertDice(1, 1, d4);
        s.silentInsertDice(1, 3, d5);
        s.silentInsertDice(2, 0, d6);
        s.silentInsertDice(2, 2, d7);
        s.silentInsertDice(2, 4, d8);
        s.silentInsertDice(3, 1, d9);
        s.silentInsertDice(3, 3, d10);
    }

    private void insertDice2() {
        try {
            s = buildSchema(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dice d1 = new Dice(Colour.ANSI_GREEN, 1);
        Dice d2 = new Dice(Colour.ANSI_GREEN, 2);
        Dice d3 = new Dice(Colour.ANSI_GREEN, 3);
        Dice d4 = new Dice(Colour.ANSI_GREEN, 4);

        s.silentInsertDice(1, 1, d1);
        s.silentInsertDice(2, 0, d2);
        s.silentInsertDice(2, 2, d3);
        s.silentInsertDice(3, 1, d4);
    }

    @Test
    void CorrectScore1() {
        insertDice1();

        DiagonalObj card = new DiagonalObj("Diagonali Colorate", "Numero di dadi dello stesso colore adiacenti");
        card.dump();

        assertEquals(6, card.scoreCard(s), "Result Correct");
    }

    @Test
    void nullScore() {
        try {
            s = buildSchema(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiagonalObj card = new DiagonalObj("Diagonali Colorate", "Numero di dadi dello stesso colore adiacenti");
        card.dump();

        assertEquals(0, card.scoreCard(s), "result is 0");
    }

    @Test
    void CorrectScore2() {
        insertDice2();

        DiagonalObj card = new DiagonalObj("Diagonali Colorate", "Numero di dadi dello stesso colore adiacenti");
        card.dump();

        assertEquals(4, card.scoreCard(s), "Result Correct");
    }
}
