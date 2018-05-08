package it.polimi.ingsw.statesTest;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.EndTurnState;
import it.polimi.ingsw.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndTurnTest {
    List <Player> players = new ArrayList<Player>();
    Board board = new Board(players);
    Round round = new Round(new Player("player 1"),board);
    EndTurnState state = new EndTurnState();

    @Test
    public void nameTest(){
        assertTrue(state.toString() == "EndTurnState");
    }

    @Test
    public void nextStateTest(){
        players.add(new Player("player 1"));
        assertTrue(state.nextState(round,"Initial").toString().equals( "InitialState"));
    }
}
