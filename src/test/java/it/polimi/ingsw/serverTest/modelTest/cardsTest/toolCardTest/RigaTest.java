package it.polimi.ingsw.serverTest.modelTest.cardsTest.toolCardTest;

import it.polimi.ingsw.server.model.board.*;
import it.polimi.ingsw.server.model.cards.toolCards.Riga;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.rules.RulesManager;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RigaTest {
    Schema sch;
    private List<Player> players = new ArrayList<Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    private Riga toolCard = new Riga();
    private Dice d1= new Dice(Colour.ANSI_BLUE, 2);
    private Dice d2= new Dice(Colour.ANSI_GREEN, 1);
    private Dice d3 = new Dice(Colour.ANSI_RED, 5);
    private VirtualView virtual;

    {
        try {
            sch = new Schema().schemaInit(13);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup_round(){
        virtual = new VirtualView();
        virtual.setConnection(new Connected());
        toolCard.dump();

        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        round = new Round(player,board,null);
        board.setObserver(virtual);
        board.setDiceSpace(new ArrayList<Dice>());

    }



    public void setupSchema() throws IOException {
        List<Schema> schemas = new ArrayList<Schema>();
        schemas.add(sch);
        player.setSchemas(schemas);
        player.setObserver(virtual);
        sch.setRulesManager(new RulesManager());
        player.setSchema(sch.getName());

        sch.insertDice(0, 3, d1);
        sch.insertDice(1,3, d2);

    }

    public int num_dice(Schema sch) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++ ){
                if (sch.getTable(i, j).getDice() != null)
                    count++;

            }
        }
        return count;
    }
    @Test
    void correct_use() throws IOException {

        setup_round();
        setupSchema();
        round.setPendingDice(d3);
        assertTrue(toolCard.effects(player,  round, 3, 0));
        assertEquals(d3, sch.getTable(3,0).getDice());
        assertEquals(3, num_dice(sch));
        assertEquals(3,player.getFavour());



    }
    @Test
    void wrong_use() throws IOException {
        setup_round();
        setupSchema();
        round.setPendingDice(d3);
        assertFalse(toolCard.effects(player,  round, 1, 4));
        assertEquals(null, sch.getTable(3,0).getDice());

        assertEquals(2, num_dice(sch));
        assertEquals(1, board.getDiceSpace().getListDice().size());

    }
    @Test
    void notSetDice() throws IOException {
        setup_round();
        setupSchema();
        assertFalse(toolCard.effects(player,  round, 1, 4));
        assertEquals(2, num_dice(sch));
        assertEquals(null, sch.getTable(3,0).getDice());

        assertEquals(0, board.getDiceSpace().getListDice().size());

    }
}
