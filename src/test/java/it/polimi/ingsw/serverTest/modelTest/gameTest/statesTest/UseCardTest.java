package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.game.states.UseToolCardState;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UseCardTest {
    private List <Player> players = new ArrayList<Player>();
    private Board board = new Board(players);
    private Round round = new Round(new Player("player 1"),board,null, null);
    private UseToolCardState state = new UseToolCardState();
    private List action = new ArrayList();

    @Test
    void nameTest(){
        assertTrue(state.toString() == "UseToolCardState");
    }

    @Test
    void nextStateTest(){
        action.add("DraftDice");
        assertTrue(state.nextState(action).toString().equals( "DraftDiceState"));
    }
}