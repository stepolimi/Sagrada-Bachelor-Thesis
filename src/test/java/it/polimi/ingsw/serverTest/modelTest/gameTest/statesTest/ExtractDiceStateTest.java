package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.TOT_DICES;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExtractDiceStateTest {
    private Board board;
    private Round round;

    private void testInit(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        GameMultiplayer game = new GameMultiplayer(players);
        board = game.getBoard();
        round = new Round(players.get(0),board,game.getRoundManager(), game);
    }

    @Test
    void execute(){
        testInit();
        assertNull(board.getDiceSpace());

        //extracts the correct number of dices from the diceBag and puts them into the diceSpace
        round.roundInit();
        assertNotNull(board.getDiceSpace());
        assertEquals(7,board.getDiceSpace().getListDice().size());
        assertEquals(TOT_DICES - 7, board.getDiceBag().getDices().size());
    }
}