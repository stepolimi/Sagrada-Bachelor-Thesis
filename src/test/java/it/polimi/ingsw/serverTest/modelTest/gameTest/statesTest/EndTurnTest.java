package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.states.EndTurnState;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndTurnTest {
    private List <Player> players = new ArrayList<Player>();
    private Board board;
    private Round round;
    private VirtualView view = new VirtualView();
    private EndTurnState state = new EndTurnState();
    private List action = new ArrayList();

    @Test
    public void nameTest(){
        assertTrue(state.toString() == "EndTurnState");
    }

    @Test
    public void nextStateTest(){
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        board = new Board(players);
        board.setObserver(view);
        view.setConnection(new Connected());
        round = new Round(players.get(0),board);
        round.roundInit();
        action.add("PickDice");
        round.execute(action);
        assertTrue(state.nextState(round,action).toString().equals( "PickDiceState"));
        action.clear();
        action.add("EndTurn");
        round.execute(action);
        assertEquals("player 2",round.getCurrentPlayer().getNickname());
    }
}
