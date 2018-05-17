package it.polimi.ingsw.serverTest.modelTest.boardTest;


import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.RoundTrack;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class RoundTrackTest {

    RoundTrack trackround = new RoundTrack();
    ArrayList<Dice> listDice = new ArrayList<Dice>();
    Dice d1 = new Dice(Colour.ANSI_PURPLE, 3);
    Dice d2 = new Dice(Colour.ANSI_GREEN, 2);

    public void setOfDice(){

        listDice.add(d1);
        listDice.add(d2);

    }

    @Test
    public void dice_insertion(){
        setOfDice();
        trackround.insertDices(listDice, 1);
        assertEquals(listDice.size(), trackround.getListRounds(1).size());
        assertEquals(d1, trackround.getListRounds(1).get(0));
    }


    @Test
    public void roundtrack_empty(){
        setOfDice();
        trackround.insertDices(listDice, 1);
        trackround.removeDice(1,1);
        trackround.removeDice(1,0);

        assertEquals(0, trackround.getListRounds(1).size());

    }


}
