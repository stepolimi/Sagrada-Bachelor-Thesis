package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;


import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoundTest {
    private List<Player> players = new ArrayList <Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    private Round round2;
    List action = new ArrayList();

    private void TestInit(){
        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        board.setObserver(new VirtualView());
    }

    @Test
    void ChangeStateCheck(){
        TestInit();
        round = new Round(player,board);
        round.roundInit();

        //Round switch states correctly
        action.add("PickDice");
        round.execute(action);
        assertTrue(round.getCurrentState().toString() == "ExtractDiceState" );
        //action.add("UseCard");
        //round.execute(action);
        //assertTrue(round.getCurrentState().toString() == "UseCardState");
        action.add(0,"PickDice");
        round.execute(action);
        assertTrue(round.getCurrentState().toString() == "PickDiceState");
        //action.add("RollDice");
        //round.execute(action);
        //assertTrue(round.getCurrentState().toString() == "RollDiceState");
        //round.execute("ChangeValue");
        //assertTrue(round.getCurrentState().toString() == "ChangeValueState");
        action.add(0,"PlaceDice");
        round.execute(action);
        assertTrue(round.getCurrentState().toString() == "PlaceDiceState");
        action.add(0,"EndTurn");
        round.execute(action);
        assertTrue(round.getCurrentState().toString() == "EndTurnState");
    }

    @Test
    void ChangeCurrentPlayerCheck (){
        TestInit();
        round = new Round(player,board);
        round2 = new Round(player2,board);
        round.roundInit();
        round2.roundInit();
        action.add("EndTurn");

        //Round starts with the correct player
        assertTrue(round.getCurrentPlayer()== player);
        assertTrue(round2.getCurrentPlayer()== player2);
        assertTrue(round.getTurnNumber()== 0);

        //Round change the currentPlayer correctly
        round.execute(action);
        round.execute(action);
        assertTrue(round.getCurrentPlayer()== player2);
        round.execute(action);
        assertTrue(round.getCurrentPlayer() == player3);
        round.execute(action);
        assertTrue(round.getCurrentPlayer() == player3);
        round.execute(action);
        assertTrue(round.getCurrentPlayer()== player2);

    }
}
