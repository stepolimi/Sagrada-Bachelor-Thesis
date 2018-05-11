package it.polimi.ingsw.Model.CardsTest.ToolCardTest;

import it.polimi.ingsw.Server.Model.board.*;
import it.polimi.ingsw.Server.Model.cards.ToolCards.Diluente;
import it.polimi.ingsw.Server.Model.game.states.Round;
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
    Diluente toolCard = new Diluente();

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
        toolCard.dump();

        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        round = new Round(player,board);
        round.setPendingDice(d3);

    }
    public void setupSchema() throws IOException {
        player.setSchema(sch);


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
            player.getSchema().insertDice(3, 1, round.getPendingDice());
        }
        assertEquals(3, num_dice(sch));


    }

    @Test
    void wrong_insertion() throws IOException{
        setup_round();
        setupSchema();


        assertFalse(toolCard.placeDiceToSchema(0, 0, round.getPendingDice(), sch, 11));
        if(toolCard.placeDiceToSchema(0, 0, round.getPendingDice(), sch, 11)){
            player.getSchema().insertDice(0, 0, round.getPendingDice());
        }
        assertEquals(2, num_dice(sch));
    }




}
