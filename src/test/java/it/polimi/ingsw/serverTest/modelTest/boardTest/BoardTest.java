package it.polimi.ingsw.serverTest.modelTest.boardTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
    Board board;
    List <Player> players = new ArrayList<Player>();
    Player player,player2;

    public void initBoard(){
        player =new Player("player 1");
        player2 = new Player("player 2");
        players.add(player);
        players.add(player2);
        board = new Board( players);
    }

    @Test
    public void playersTest(){
        initBoard();
        assertTrue(board.getPlayerList() == players);
        assertTrue(board.numPlayers() == players.size());
        assertTrue(board.getIndex(player) == 0);
    }
}
