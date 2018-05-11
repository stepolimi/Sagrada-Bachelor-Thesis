package it.polimi.ingsw.statesTest;

import it.polimi.ingsw.Server.Model.board.Board;
import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.game.states.ChangeValueState;
import it.polimi.ingsw.Server.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangeValueTest {
    List <Player> players = new ArrayList<Player>();
    Board board = new Board(players);
    Round round = new Round(new Player("player 1"),board);
    ChangeValueState state = new ChangeValueState();

    @Test
    public void nameTest(){
        assertTrue(state.toString() == "ChangeValueState");
    }

    @Test
    public void nextStateTest(){
        assertTrue(state.nextState(round,"PickDice").toString().equals( "PickDiceState"));
    }
}
