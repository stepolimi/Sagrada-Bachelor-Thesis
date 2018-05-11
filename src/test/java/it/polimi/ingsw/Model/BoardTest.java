package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.View.View;
import it.polimi.ingsw.Server.Model.board.Board;
import it.polimi.ingsw.Server.Model.board.Player;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
