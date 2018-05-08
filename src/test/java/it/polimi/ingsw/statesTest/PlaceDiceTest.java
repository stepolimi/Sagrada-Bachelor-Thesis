package it.polimi.ingsw.statesTest;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.PlaceDiceState;
import it.polimi.ingsw.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaceDiceTest {
    List <Player> players = new ArrayList<Player>();
    Board board = new Board(players);
    Round round = new Round(new Player("player 1"),board);
    PlaceDiceState state = new PlaceDiceState();

    @Test
    public void nameTest(){
        assertTrue(state.toString() == "PlaceDiceState");
    }

    @Test
    public void nextStateTest(){
        assertTrue(state.nextState(round,"PickDice").toString().equals( "PickDiceState"));
    }
}