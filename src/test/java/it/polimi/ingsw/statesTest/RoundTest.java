package it.polimi.ingsw.statesTest;

import it.polimi.ingsw.Board;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.game.states.Round;
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

    private void TestInit(){
        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
    }

    @Test
    void ChangeStateCheck(){
        TestInit();
        round = new Round(player,board);
        round.setReference(round);
        round.roundInit();

        //Round starts in the correct state
        assertTrue(round.getCurrentState().toString() == "ExtractDiceState" );

        //Round switch states correctly
        round.changeState("Initial");
        assertTrue(round.getCurrentState().toString() == "InitialState");
        round.changeState("EndTurn");
        assertTrue(round.getCurrentState().toString() == "EndTurnState");
    }

    @Test
    void ChangeCurrentPlayerCheck (){
        TestInit();
        round = new Round(player,board);
        round2 = new Round(player2,board);
        round.setReference(round);
        round2.setReference(round2);
        round.roundInit();
        round2.roundInit();

        //Round starts with the correct player
        assertTrue(round.getCurrentPlayer()== player);
        assertTrue(round2.getCurrentPlayer()== player2);
        assertTrue(round.getTurnNumber()== 0);

        //Round change the currentPlayer correctly
        round.changeState("Initial");
        round.changeState("EndTurn");
        round.changeState("Initial");
        assertTrue(round.getCurrentPlayer()== player2);
        round.changeState("EndTurn");
        round.changeState("Initial");
        assertTrue(round.getCurrentPlayer() == player3);
        round.changeState("EndTurn");
        round.changeState("Initial");
        assertTrue(round.getCurrentPlayer() == player3);
        round.changeState("EndTurn");
        round.changeState("Initial");
        assertTrue(round.getCurrentPlayer()== player2);

    }
}
