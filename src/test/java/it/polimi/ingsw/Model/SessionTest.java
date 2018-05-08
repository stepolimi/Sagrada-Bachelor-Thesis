package it.polimi.ingsw.Model;

import it.polimi.ingsw.game.Session;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionTest {
    Player p1 = new Player("Giocatore1");
    String p3= new String("Giocatore3");
    Player p2 = new Player("Giocatore2");
    Session session;

    ArrayList<Player> players = new ArrayList<Player>();

    public void setup(){
        players.add(p1);
        players.add(p2);
        session = new Session(players);

    }

    @Test
    void join_players(){
        setup();
        session.joinPlayer(p3);
        assertEquals(3, session.getPlayers().size());

    }

    @Test
    void remove_player(){
        setup();
        session.removePlayer(p3);
        assertEquals(2, session.getPlayers().size() );
    }


}
