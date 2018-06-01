package it.polimi.ingsw.serverTest.modelTest.cardsTest.toolCardTest;

import it.polimi.ingsw.server.model.board.*;
import it.polimi.ingsw.server.model.cards.toolCards.Lathekin;
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

public class LathekinTest {
    Schema sch;
    private List<Player> players = new ArrayList<Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    private Lathekin toolCard = new Lathekin();
    private Dice d1= new Dice(Colour.ANSI_BLUE, 5);
    private Dice d2= new Dice(Colour.ANSI_GREEN, 1);
    private Dice d3= new Dice(Colour.ANSI_RED, 3);
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


    }



    public void setupSchema() throws IOException {
        List<Schema> schemas = new ArrayList<Schema>();
        schemas.add(sch);
        player.setObserver(virtual);
        sch.setRulesManager(new RulesManager());
        player.setSchemas(schemas);
        player.setSchema(sch.getName());


        sch.insertDice(1, 0, d1);
        sch.insertDice(2,0, d2);
        sch.insertDice(1,4, d3);
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
        int num_dice = num_dice(sch);
        assertTrue(toolCard.effects(player, round, 2, 0, 1, 3, 1,0, 2, 4  ));
        assertEquals(d2, sch.getTable(1,3).getDice());
        assertEquals(d1, sch.getTable(2,4).getDice());
        assertEquals(num_dice, num_dice(sch));
        assertEquals(3,player.getFavour());



    }

    @Test  //because we must respect every restriction
    void wrong_use() throws  IOException{
        setup_round();
        setupSchema();
        int num_dice = num_dice(sch);


        assertFalse(toolCard.effects(player, round, 2, 0, 1, 3, 1, 0, 2, 3 ));
        assertEquals(num_dice, num_dice(sch));

    }

}
