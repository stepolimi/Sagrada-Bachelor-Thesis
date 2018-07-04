package it.polimi.ingsw.serverTest.modelTest.gameTest;

import it.polimi.ingsw.server.model.game.Session;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionTest {
    private String p3 = "Player 3";
    private String p4 = "Player 4";
    private Session session;

    private ArrayList<String> players = new ArrayList<>();

    private void setup(){
        players.add("Player 1");
        players.add("Player 2");
        players.add(p3);
        players.add(p4);

        session = Session.getSession();
    }

    @Test
    void join_players(){
        setup();
        String p5 = "Player 5";

        session.joinPlayer(players.get(0));
        assertEquals(1,session.getPlayersInLobby().size());

        session.removePlayer(players.get(0));

        //game will be created when lobby reaches 4 players
        for(String s: players) {
            assertTrue(session.getPlayersInGames().isEmpty());
            session.joinPlayer(s);
        }
        assertFalse(session.getPlayersInGames().isEmpty());

        //a new lobby for a new game is created after the start of the first one
        session.joinPlayer(p5);
        assertEquals(1,session.getPlayersInLobby().size());
        session.removePlayer(p5);
    }

    @Test
    void remove_player(){
        setup();

        //remove a player from lobby correctly
        players.remove(p4);
        for(String s: players) {
            session.joinPlayer(s);
        }
        session.removePlayer(p3);
        assertEquals(2, session.getPlayersInLobby().size() );

        //disconnects player correctly after game start
        session.joinPlayer(p3);
        session.joinPlayer(p4);
        session.disconnectPlayer(p4,session.getPlayersInGames().get(p4));
        assertFalse(session.getPlayersInGames().get(p4).getBoard().getPlayer(p4).isConnected());
    }


}