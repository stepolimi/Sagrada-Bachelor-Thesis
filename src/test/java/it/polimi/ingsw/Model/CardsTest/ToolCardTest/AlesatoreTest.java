package it.polimi.ingsw.Model.CardsTest.ToolCardTest;

import it.polimi.ingsw.Server.Model.board.*;
import it.polimi.ingsw.Server.Model.cards.ToolCards.Alesatore;
import it.polimi.ingsw.Server.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlesatoreTest  {
    Schema sch;
    private List<Player> players = new ArrayList <Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    Alesatore toolCard = new Alesatore();
    Dice d1= new Dice(Colour.ANSI_BLUE, 2);
    Dice d2= new Dice(Colour.ANSI_GREEN, 1);

     {
        try {
            sch = new Schema().schemaInit(7);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup_round(){
        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        round = new Round(player,board);


    }



    public void setupSchema() throws IOException {
        player.setSchema(sch);

        toolCard.dump();


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
       int num_dice = num_dice(sch);
       assertTrue(toolCard.effects(player,  1, 3, 0, 4 ));
       assertEquals(d2, sch.getTable(0,4).getDice());
       assertEquals(num_dice, num_dice(sch));
       assertEquals(3,player.getFavour());



    }

    @Test  //because we must respect every restriction
    void wrong_use() throws  IOException{
        setup_round();
        setupSchema();
        int num_dice = num_dice(sch);


        assertFalse(toolCard.effects(player, 1, 3, 0, 0 ));
        assertEquals(num_dice, num_dice(sch));

    }



}
