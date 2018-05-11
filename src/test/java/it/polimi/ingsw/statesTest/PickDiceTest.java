package it.polimi.ingsw.statesTest;

import it.polimi.ingsw.Server.Model.board.Board;
import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.game.states.PickDiceState;
import it.polimi.ingsw.Server.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PickDiceTest {
    List <Player> players = new ArrayList<Player>();
    Board board = new Board(players);
    Round round = new Round(new Player("player 1"),board);
    PickDiceState state = new PickDiceState();

    @Test
    public void nameTest(){
        assertTrue(state.toString() == "PickDiceState");
    }

    @Test
    public void nextStateTest(){
        assertTrue(state.nextState(round,"PlaceDice").toString().equals( "PlaceDiceState"));
    }
}