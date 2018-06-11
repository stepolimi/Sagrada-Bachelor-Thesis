package it.polimi.ingsw.serverTest.modelTest.cardsTest.objCardTest;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.cards.objCards.DiagonalObj;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

//testing ONLY the correct calculus of score  IGNORING restriction of the current schema. restriction we'll be tested in
//other tests

public class DiagonalCTest {


    Schema s = new Schema();

    public void insertDice1(){
        try {
            s = s.schemaInit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dice d1 = new Dice(Colour.ANSI_GREEN,1);
        Dice d2 = new Dice(Colour.ANSI_GREEN,2);
        Dice d3 = new Dice(Colour.ANSI_GREEN,3);
        Dice d4 = new Dice(Colour.ANSI_GREEN,4);
        Dice d5 = new Dice(Colour.ANSI_YELLOW,5);
        Dice d6 = new Dice(Colour.ANSI_YELLOW,6);
        Dice d7 = new Dice(Colour.ANSI_YELLOW,1);
        Dice d8 = new Dice(Colour.ANSI_YELLOW,2);
        Dice d9 = new Dice(Colour.ANSI_BLUE,3);
        Dice d10 = new Dice(Colour.ANSI_BLUE,4);




        s.insertDice(0,0,d1);
        s.insertDice(0,2,d2);
        s.insertDice(0,4,d3);
        s.insertDice(1,1,d4);
        s.insertDice(1,3,d5);
        s.insertDice(2,0,d6);
        s.insertDice(2,2,d7);
        s.insertDice(2,4,d8);
        s.insertDice(3,1,d9);
        s.insertDice(3,3,d10);
    }

    public void insertDice2(){
        try {
            s = s.schemaInit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dice d1 = new Dice(Colour.ANSI_GREEN,1);
        Dice d2 = new Dice(Colour.ANSI_GREEN,2);
        Dice d3 = new Dice(Colour.ANSI_GREEN,3);
        Dice d4 = new Dice(Colour.ANSI_GREEN,4);


        s.insertDice(1,1,d1);
        s.insertDice(2, 0, d2);
        s.insertDice(2,2,d3);
        s.insertDice(3,1,d4);

    }

    @Test
    void CorrectScore1() throws  Exception{

        insertDice1();

        DiagonalObj card = new DiagonalObj("Diagonali Colorate", "Numero di dadi dello stesso colore adiacenti");
        card.dump();

        assertEquals(6, card.scoreCard(s), "Result Correct");

    }

    @Test
    public void nullScore(){
        try {
            s = s.schemaInit(1);
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
