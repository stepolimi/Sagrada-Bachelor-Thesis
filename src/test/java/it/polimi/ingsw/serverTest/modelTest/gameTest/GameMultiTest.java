package it.polimi.ingsw.serverTest.modelTest.gameTest;

import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameMultiTest {
    private Player p1 = new Player("Giocatore1");
    private Player p2 = new Player("Giocatore2");
    private Player p3= new Player("Giocatore3");
    private ArrayList<Player> players = new ArrayList<Player>();
    private GameMultiplayer game;
    private VirtualView virtual;

    public void setup(){
        virtual = new VirtualView();
        virtual.setConnection(new Connected());
        players.add(p1);
        players.add(p2);
        players.add(p3);
        game = new GameMultiplayer(players);
        game.setObserver(virtual);
        game.getBoard().setDiceSpace(new ArrayList<Dice>());

    }

    @Test
    public void gameInitTest(){
        setup();
        game.gameInit();
        for(Player p:game.getBoard().getPlayerList()) {
            assertTrue(p.getPrCard() != null);
            assertTrue(p.getSchemas().size() == 4);
            for(Schema schema: p.getSchemas()){
                assertTrue(schema != null);
            }
        }
        assertEquals(3, game.getBoard().getDeckTool().size() );
        assertEquals(3, game.getBoard().getDeckPublic().size() );
    }
}
