package it.polimi.ingsw.Model;


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
        trackround.insertDice(listDice, 1);
        assertEquals(listDice.size(), trackround.getListRounds(1).size());
        assertEquals(d1, trackround.getListRounds(1).get(0));
    }


    @Test
    public void dice_removing(){
        setOfDice();
        trackround.insertDice(listDice, 1);
        listDice.add(trackround.removeDice(1,1));
        assertEquals(listDice.size(), trackround.getListRounds(1).size());

    }


}
