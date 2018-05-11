package it.polimi.ingsw.statesTest;

import it.polimi.ingsw.Server.Model.board.Board;
import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.game.states.Round;
import it.polimi.ingsw.Server.Model.game.states.UseCardState;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UseCardTest {
    List <Player> players = new ArrayList<Player>();
    Board board = new Board(players);
    Round round = new Round(new Player("player 1"),board);
    UseCardState state = new UseCardState();

    @Test
    public void nameTest(){
        assertTrue(state.toString() == "UseCardState");
    }

    @Test
    public void nextStateTest(){
        assertTrue(state.nextState(round,"PickDice").toString().equals( "PickDiceState"));
    }
}