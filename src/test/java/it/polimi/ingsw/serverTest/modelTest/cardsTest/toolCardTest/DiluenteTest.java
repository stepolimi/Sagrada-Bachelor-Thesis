package it.polimi.ingsw.serverTest.modelTest.cardsTest.toolCardTest;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.model.board.*;
import it.polimi.ingsw.server.model.cards.toolCards.Diluente;
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

public class DiluenteTest {

    Schema sch;
    private List<Player> players = new ArrayList<Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    private Diluente toolCard = new Diluente();
    private VirtualView virtual;

    Dice d1= new Dice(Colour.ANSI_BLUE, 2);
    Dice d2= new Dice(Colour.ANSI_GREEN, 3);
    Dice d3 = new Dice(Colour.ANSI_RED, 1);

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
         round.setPendingDice(d3);

    }
    public void setupSchema() throws IOException {
        List<Schema> schemas = new ArrayList<Schema>();
        schemas.add(sch);
        player.setObserver(virtual);
        sch.setRulesManager(new RulesManager());
        player.setSchemas(schemas);
        player.setSchema(sch.getName());


        sch.insertDice(3, 0, d1);
        sch.insertDice(2,0, d2);

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
    void correct_insertion() throws IOException {

         setup_round();
         setupSchema();


        assertTrue(toolCard.placeDiceToSchema(3, 1, round.getPendingDice(), sch, 11));
        if(toolCard.placeDiceToSchema(3, 1, round.getPendingDice(), sch, 11)){
            try {
                player.getSchema().insertDice(3, 1, round.getPendingDice(),11);
            } catch (InsertDiceException e) {
                e.printStackTrace();
            }
        }
        assertEquals(3, num_dice(sch));


    }

    @Test
    void wrong_insertion() throws IOException{
        setup_round();
        setupSchema();


        assertFalse(toolCard.placeDiceToSchema(0, 0, round.getPendingDice(), sch, 11));
        if(toolCard.placeDiceToSchema(0, 0, round.getPendingDice(), sch, 11)){
            try {
                player.getSchema().insertDice(0, 0, round.getPendingDice(),11);
            } catch (InsertDiceException e) {
                e.printStackTrace();
            }
        }
        assertEquals(2, num_dice(sch));
    }




}
