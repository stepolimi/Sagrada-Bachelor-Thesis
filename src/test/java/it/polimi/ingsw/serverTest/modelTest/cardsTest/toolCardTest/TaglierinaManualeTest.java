package it.polimi.ingsw.serverTest.modelTest.cardsTest.toolCardTest;

import it.polimi.ingsw.server.model.board.*;
import it.polimi.ingsw.server.model.cards.toolCards.TaglierinaManuale;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.rules.RulesManager;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaglierinaManualeTest {
    private Schema sch;
    private List<Player> players = new ArrayList<Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    private TaglierinaManuale toolCard = new TaglierinaManuale();
    private Dice d1= new Dice(Colour.ANSI_PURPLE, 1);
    private Dice d2= new Dice(Colour.ANSI_PURPLE, 5);
    private Dice d3= new Dice(Colour.ANSI_BLUE, 2);
    private Dice d4= new Dice (Colour.ANSI_PURPLE, 6);
    private Dice d5= new Dice (Colour.ANSI_RED, 6);
    private VirtualView virtual = new VirtualView();

    {
        try {
            sch = new Schema().schemaInit(15);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setup_round(){
        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        round = new Round(player,board);
        virtual.setConnection(new Connected());
        board.setObserver(virtual);
        board.setDiceSpace(new ArrayList<Dice>());
        toolCard.dump();

        board.getRoundTrack().insertDice(d4, 3);

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
    void correct_use2() throws IOException {
        List<Schema> schemas = new ArrayList<Schema>();
        setup_round();
        sch.insertDice(0, 3, d3);
        sch.insertDice(3,4, d5);
        sch.insertDice(3,1, d2);
        sch.insertDice(2,0, d1);
        schemas.add(sch);
        player.setSchemas(schemas);
        player.setObserver(virtual);
        sch.setRulesManager(new RulesManager());
        player.setSchema(sch.getName());
        int num_dice = num_dice(sch);
        assertTrue(toolCard.effects(player, round, 3, 1, 3, 3, 2,0, 2, 4,2, 3  ));
        assertEquals(d2, sch.getTable(3,3).getDice());
        assertEquals(d1, sch.getTable(2,4).getDice());
        assertEquals(num_dice, num_dice(sch));



    }



    @Test  //because we must respect every restriction
    void wrong_use() throws  IOException{
        List<Schema> schemas = new ArrayList<Schema>();
        setup_round();
        int num_dice = num_dice(sch);
        sch.insertDice(0, 3, d3);
        sch.insertDice(3,4, d5);
        sch.insertDice(3,1, d2);
        sch.insertDice(2,0, d1);
        schemas.add(sch);
        player.setSchemas(schemas);
        player.setObserver(virtual);
        sch.setRulesManager(new RulesManager());
        player.setSchema(sch.getName());



        assertFalse(toolCard.effects(player, round, 3, 1, 3, 3, 2,0, 2, 3,2, 3   ));

        assertEquals(4, num_dice(sch));

    }

    @Test
    void correct_use1() throws IOException {
        List<Schema> schemas = new ArrayList<Schema>();
        setup_round();
        sch.insertDice(0, 3, d3);
        sch.insertDice(3,4, d5);
        sch.insertDice(3,1, d2);
        sch.insertDice(2,0, d1);
        schemas.add(sch);
        player.setSchemas(schemas);
        player.setObserver(virtual);
        sch.setRulesManager(new RulesManager());
        player.setSchema(sch.getName());
        int num_dice = num_dice(sch);
        assertTrue(toolCard.effects(player, round, 3, 1, 3, 3, 2,0, 2, 4,1, 3  ));
        assertEquals(d2, sch.getTable(3,3).getDice());
        assertEquals(null, sch.getTable(2,4).getDice());
        assertEquals(num_dice, num_dice(sch));

    }
}
