package it.polimi.ingsw.CardsTest.ObjCardTest;

import com.google.gson.Gson;
import it.polimi.ingsw.Cards.ObjCards.DiagonalObj;
import it.polimi.ingsw.Colour;
import it.polimi.ingsw.Dice;
import it.polimi.ingsw.Schema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//testing ONLY the correct calculus of score  IGNORING restriction of the current schema. restriction we'll be tested in
//other tests

public class DiagonalCTest {


    Gson g = new Gson();
    String stringa = "{\"name\":\"Kaleidoscopic Dream\",\"difficult\":4,\"table\":[[{\"c\":\"ANSI_YELLOW\",\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":1,\"full\":false}],[{\"c\":\"ANSI_GREEN\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":5,\"full\":false},{\"number\":0,\"full\":false},{\"number\":4,\"full\":false}],[{\"number\":3,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_GREEN\",\"number\":0,\"full\":false}],[{\"number\":2,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"c\":\"ANSI_YELLOW\",\"number\":0,\"full\":false}]]}";
    Schema s = g.fromJson(stringa,Schema.class);

    public void insertDice(){
        Dice d1 = new Dice(Colour.ANSI_GREEN,1);
        Dice d2 = new Dice(Colour.ANSI_GREEN,2);
        Dice d3 = new Dice(Colour.ANSI_GREEN,3);
        Dice d4 = new Dice(Colour.ANSI_GREEN,4);
        Dice d5= new Dice(Colour.ANSI_YELLOW,5);
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

    @Test
    void CorrectScore() throws  Exception{

        insertDice();

        DiagonalObj card = new DiagonalObj("Diagonali Colorate", "Numero di dadi dello stesso colore adiacenti");
        assertEquals(6, card.ScoreCard(s), "Result Correct");

    }
    @Test
    public void nullScore(){

        DiagonalObj card = new DiagonalObj("Diagonali Colorate", "Numero di dadi dello stesso colore adiacenti");

        assertEquals(0, card.ScoreCard(s));
    }


}
