package it.polimi.ingsw.serverTest.modelTest.gameTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.RoundManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RoundManagerTest {
    private RoundManager roundManager;

    private void roundManagerInit(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        GameMultiplayer gameMultiplayer = new GameMultiplayer(players);
        Board board = new Board(players);
        roundManager = new RoundManager(board,gameMultiplayer);
    }

    @Test
    void startNewRound(){
        roundManagerInit();
        roundManager.setFirstPlayer();
        assertEquals(0,roundManager.getRoundNumber());
        roundManager.startNewRound();
        assertNotEquals(null,roundManager.getRound());
        assertNotEquals(null,roundManager.getRound().getCurrentPlayer());
        assertEquals(1,roundManager.getRoundNumber());
    }
}
