package it.polimi.ingsw.Model.GameTest;

import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.game.GameMultiplayer;
import it.polimi.ingsw.Server.VirtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMultiTest {
    Player p1 = new Player("Giocatore1");
    Player p2 = new Player("Giocatore2");
    Player p3= new Player("Giocatore3");
    ArrayList<Player> players = new ArrayList<Player>();
    GameMultiplayer gameMultiplayer;

    public void setup(){
        players.add(p1);
        players.add(p2);
        players.add(p3);


        gameMultiplayer = new GameMultiplayer(players);
        gameMultiplayer.setObserver(new VirtualView());
        gameMultiplayer.gameInit();

    }

    @Test
    void number_round(){
        setup();
        assertEquals(10, gameMultiplayer.getRounds().size());
    }
}
