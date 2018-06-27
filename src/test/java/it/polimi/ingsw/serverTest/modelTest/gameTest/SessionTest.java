package it.polimi.ingsw.serverTest.modelTest.gameTest;

import it.polimi.ingsw.server.model.game.Session;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionTest {
    private String p1 = "Giocatore1";
    private String p2 = "Giocatore2";
    private String p3 = "Giocatore3";
    private String p4 = "Giocatore4";
    private String p5 = "Giocatore5";
    private Session session;
    private VirtualView virtual;

    ArrayList<String> players = new ArrayList<String>();

    public void setup(){
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        session = new Session();
        virtual = new VirtualView();
        session.setObserver(virtual);
        virtual.setConnection(new Connected());

    }

    @Test
    void join_players(){
        setup();
        //no duplicate nickname
        session.joinPlayer(players.get(0));
        session.joinPlayer(players.get(0));
        assertEquals(1,session.getPlayers().size());

        session.removePlayer(players.get(0));

        //game will be created when lobby reaches 4 players
        for(String s: players) {
            assertTrue(session.getGame() == null);
            session.joinPlayer(s);
        }
        assertTrue(session.getGame()!= null);

        //no player can join the game if it's already running
        session.joinPlayer(p5);
        assertEquals(4,session.getPlayers().size());

    }

    @Test
    void remove_player(){

        setup();
        session.setObserver(virtual);

        //remove a player from lobby correctly
        players.remove(p4);
        for(String s: players) {
            session.joinPlayer(s);
        }
        session.removePlayer(p3);
        assertEquals(2, session.getPlayers().size() );

        //disconnects player correctly after game start
        session.joinPlayer(p3);
        session.joinPlayer(p4);
        session.removePlayer(p4);
        assertFalse(session.getGame().getBoard().getPlayer(p4).isConnected());
    }


}