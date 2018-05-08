package it.polimi.ingsw.statesTest;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.ExtractDiceState;
import it.polimi.ingsw.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtractDiceTest {
    List <Player> players = new ArrayList<Player>();
    Board board = new Board(players);
    Round round = new Round(new Player("player 1"),board);
    ExtractDiceState state = new ExtractDiceState();

    @Test
    public void nameTest(){
        assertTrue(state.toString() == "ExtractDiceState");
    }

    @Test
    public void nextStateTest(){
        assertTrue(state.nextState(round,"PickDice").toString().equals( "PickDiceState"));
    }
}