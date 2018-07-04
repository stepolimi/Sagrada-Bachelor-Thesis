package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiPlayer;
import it.polimi.ingsw.server.model.game.RoundManager;
import it.polimi.ingsw.server.model.game.states.EndTurnState;
import it.polimi.ingsw.server.model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.END_TURN;
import static junit.framework.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class EndTurnStateTest {
    private Round round;
    private EndTurnState state;
    private Dice dice;
    private RoundManager roundManager;

    private void testInit(){
        state = new EndTurnState();
        List<Player> players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        GameMultiPlayer game = new GameMultiPlayer(players);
        roundManager = game.getRoundManager();
        roundManager.startNewRound();
        round = roundManager.getRound();
        dice = new Dice(Colour.ANSI_YELLOW, 6);
    }

    private void endTurn(){
        Message message = new Message(END_TURN);
        state.execute(round,message);
    }

    @Test
    void execute(){
        testInit();

        //changes currentPlayer correctly each time
        round.setPendingDice(dice);
        Player firstPlayer = round.getCurrentPlayer();
        endTurn();
//        assertNull(round.getPendingDice());
        Player secondPlayer = round.getCurrentPlayer();
        assertNotEquals(firstPlayer,secondPlayer);
        endTurn();
        Player thirdPlayer = round.getCurrentPlayer();
        assertNotEquals(firstPlayer,thirdPlayer);
        assertNotEquals(secondPlayer,thirdPlayer);
        endTurn();
        assertEquals(thirdPlayer,round.getCurrentPlayer());
        endTurn();
        assertEquals(secondPlayer,round.getCurrentPlayer());
        endTurn();
        assertEquals(firstPlayer,round.getCurrentPlayer());

        //a new round is started when the turns are over
        endTurn();
        assertNotSame(round,roundManager.getRound());

        //changes currentPlayer correctly each time with one disconnected
        round = roundManager.getRound();
        round.disconnectPlayer();
        firstPlayer = round.getCurrentPlayer();
        endTurn();
        secondPlayer = round.getCurrentPlayer();
        assertNotSame(firstPlayer,secondPlayer);
        endTurn();
        assertSame(secondPlayer,round.getCurrentPlayer());
        endTurn();
        assertSame(firstPlayer,round.getCurrentPlayer());
        endTurn();
        assertNotSame(round,roundManager.getRound());
    }
}
