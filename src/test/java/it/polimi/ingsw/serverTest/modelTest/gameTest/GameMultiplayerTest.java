package it.polimi.ingsw.serverTest.modelTest.gameTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.model.builders.PrivateObjectiveBuilder.buildPrivateObjective;
import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static org.junit.jupiter.api.Assertions.*;

class GameMultiplayerTest {
    private Player p1 = new Player("Giocatore1");
    private Player p2 = new Player("Giocatore2");
    private Player p3= new Player("Giocatore3");
    private ArrayList<Player> players = new ArrayList<>();
    private GameMultiplayer game;

    private void setup(){
        players.add(p1);
        players.add(p2);
        players.add(p3);
        game = new GameMultiplayer(players);
        game.getBoard().setDiceSpace(new ArrayList<>());

    }
    private void setPrivateObjectives(){
        p1.setPrCard(buildPrivateObjective(1));
        p2.setPrCard(buildPrivateObjective(2));
        p3.setPrCard(buildPrivateObjective(3));
    }

    private void setSchemas(){
        try {
            p1.setSchema(buildSchema(1));
            p2.setSchema(buildSchema(2));
            p3.setSchema(buildSchema(3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertDices(){
        p1.getSchema().silentInsertDice(0,0,new Dice(Colour.ANSI_RED,1));
        p1.getSchema().silentInsertDice(0,2,new Dice(Colour.ANSI_RED,6));
        p1.getSchema().silentInsertDice(0,4,new Dice(Colour.ANSI_RED,6));
        p1.getSchema().silentInsertDice(1,1,new Dice(Colour.ANSI_RED,6));
        p1.getSchema().silentInsertDice(1,3,new Dice(Colour.ANSI_RED,6));

        p2.getSchema().silentInsertDice(0,0,new Dice(Colour.ANSI_YELLOW,5));
        p2.getSchema().silentInsertDice(0,2,new Dice(Colour.ANSI_YELLOW,5));
        p2.getSchema().silentInsertDice(0,4,new Dice(Colour.ANSI_YELLOW,5));
        p2.getSchema().silentInsertDice(1,1,new Dice(Colour.ANSI_YELLOW,5));
        p2.getSchema().silentInsertDice(1,3,new Dice(Colour.ANSI_YELLOW,5));

        p3.getSchema().silentInsertDice(0,0,new Dice(Colour.ANSI_GREEN,6));
        p3.getSchema().silentInsertDice(0,2,new Dice(Colour.ANSI_GREEN,6));
        p3.getSchema().silentInsertDice(0,4,new Dice(Colour.ANSI_GREEN,5));
        p3.getSchema().silentInsertDice(1,1,new Dice(Colour.ANSI_GREEN,5));
        p3.getSchema().silentInsertDice(1,3,new Dice(Colour.ANSI_GREEN,5));
    }

    @Test
    void gameInit(){
        setup();
        game.gameInit();
        for(Player p:game.getBoard().getPlayerList()) {
            assertNotNull(p.getPrCard());
            assertEquals(4,p.getSchemas().size());

            for(Schema schema: p.getSchemas()){
                assertNotNull(schema);
            }
        }
        assertEquals(3, game.getBoard().getDeckTool().size() );
        assertEquals(3, game.getBoard().getDeckPublic().size() );
    }

    @Test
    void endGame() {
        setup();
        setPrivateObjectives();
        setSchemas();
        insertDices();
        p1.setFavour(5);

        game.endGame(p1);
        List<Player> rankings = game.getRankings();

        assertTrue(game.isEnded());
        assertTrue(rankings.get(0).getScore() >= rankings.get(1).getScore());
        assertTrue(rankings.get(1).getScore() >= rankings.get(2).getScore());
    }

    @Test
    void reconnectPlayer() {
        setup();
        setPrivateObjectives();
        setSchemas();

        p1.setConnected(false);
        assertEquals(2,game.getBoard().getConnected());

        game.reconnectPlayer(p1.getNickname());
        assertEquals(3,game.getBoard().getConnected());
    }

    @Test
    void timerElapsed(){
        setup();
        setSchemas();

        assertNull(game.getRoundManager().getRound());
        game.timerElapsed();

        assertNotNull(game.getRoundManager().getRound());
    }
}
