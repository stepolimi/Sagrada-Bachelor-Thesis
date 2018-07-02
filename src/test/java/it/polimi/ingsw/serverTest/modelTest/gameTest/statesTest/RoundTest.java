package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;


import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.*;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.RoundManager;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static it.polimi.ingsw.server.costants.Constants.END_TURN_STATE;
import static it.polimi.ingsw.server.costants.Constants.EXTRACT_DICE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.END_TURN;
import static it.polimi.ingsw.server.costants.MessageConstants.PLACE_DICE;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private Round round;
    private List<Player> players;
    private Board board;
    private RoundManager roundManager;

    private void testInit(){
        players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        VirtualView view = VirtualView.getVirtualView();
        GameMultiplayer game = new GameMultiplayer(players);
        board = game.getBoard();
        board.setObserver(view);
        roundManager = game.getRoundManager();
        roundManager.setObserver(view);
        round = new Round(players.get(0),board,roundManager,game);
    }

    private void endTurn(){
        Message message = new Message(END_TURN);
        round.execute(message);
    }

    private void placeDice(){
        Message message = new Message(PLACE_DICE);
        message.addIntegerArgument(0);
        message.addIntegerArgument(0);
        round.execute(message);
    }

    @Test
    void roundInit(){
        testInit();
        round.roundInit();
        assertEquals(EXTRACT_DICE_STATE,round.getCurrentState());
        assertEquals(round.getCurrentPlayer(),round.getPlayersOrder().get(0));
        assertEquals(players.get(0),round.getCurrentPlayer());
        assertEquals(players.get(1),round.getPlayersOrder().get(1));
        assertEquals(players.get(2),round.getPlayersOrder().get(2));
        assertEquals(players.get(2),round.getPlayersOrder().get(3));
        assertEquals(players.get(1),round.getPlayersOrder().get(4));
        assertEquals(players.get(0),round.getPlayersOrder().get(5));
    }

    @Test
    void execute(){
        testInit();
        round.roundInit();
        Timer timer = round.getTimer();
        endTurn();
        assertEquals(END_TURN_STATE,round.getCurrentState());
        assertNotEquals(timer,round.getTimer());
        timer = round.getTimer();

        placeDice();
        assertEquals(END_TURN_STATE,round.getCurrentState());
        assertEquals(timer,round.getTimer());
    }

    @Test
    void disconnectPlayer() {
        testInit();
        round.roundInit();
        Player currentPlayer = round.getCurrentPlayer();
        int turnNumber = round.getTurnNumber();

        round.disconnectPlayer();

        assertNotSame(currentPlayer,round.getCurrentPlayer());
        assertEquals(turnNumber + 1, round.getTurnNumber());
        assertFalse(currentPlayer.isConnected());
    }
}
