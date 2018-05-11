package it.polimi.ingsw.statesTest;

import it.polimi.ingsw.Server.Model.board.Board;
import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.game.states.RollDiceState;
import it.polimi.ingsw.Server.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RollDiceTest {
    List <Player> players = new ArrayList<Player>();
    Board board = new Board(players);
    Round round = new Round(new Player("player 1"),board);
    RollDiceState state = new RollDiceState();

    @Test
    public void nameTest(){
        assertTrue(state.toString() == "RollDiceState");
    }

    @Test
    public void nextStateTest(){
        assertTrue(state.nextState(round,"PickDice").toString().equals( "PickDiceState"));
    }
}
