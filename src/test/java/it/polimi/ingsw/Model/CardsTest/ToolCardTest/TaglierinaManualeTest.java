package it.polimi.ingsw.Model.CardsTest.ToolCardTest;

import it.polimi.ingsw.Server.Model.board.*;
import it.polimi.ingsw.Server.Model.cards.ToolCards.TaglierinaManuale;
import it.polimi.ingsw.Server.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaglierinaManualeTest {
    Schema sch;
    private List<Player> players = new ArrayList<Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    TaglierinaManuale toolCard = new TaglierinaManuale();
    Dice d1= new Dice(Colour.ANSI_PURPLE, 1);
    Dice d2= new Dice(Colour.ANSI_PURPLE, 5);
    Dice d3= new Dice(Colour.ANSI_BLUE, 2);
    Dice d4= new Dice (Colour.ANSI_PURPLE, 6);
    Dice d5= new Dice (Colour.ANSI_RED, 6);

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

        setup_round();
        sch.insertDice(0, 3, d3);
        sch.insertDice(3,4, d5);
        sch.insertDice(3,1, d2);
        sch.insertDice(2,0, d1);
        player.setSchema(sch);
        int num_dice = num_dice(sch);
        assertTrue(toolCard.effects(player, round, 3, 1, 3, 3, 2,0, 2, 4,2, 3  ));
        assertEquals(d2, sch.getTable(3,3).getDice());
        assertEquals(d1, sch.getTable(2,4).getDice());
        assertEquals(num_dice, num_dice(sch));



    }



    @Test  //because we must respect every restriction
    void wrong_use() throws  IOException{
        setup_round();
        int num_dice = num_dice(sch);
        sch.insertDice(0, 3, d3);
        sch.insertDice(3,4, d5);
        sch.insertDice(3,1, d2);
        sch.insertDice(2,0, d1);
        player.setSchema(sch);



        assertFalse(toolCard.effects(player, round, 3, 1, 3, 3, 2,0, 2, 3,2, 3   ));

        assertEquals(4, num_dice(sch));

    }

    @Test
    void correct_use1() throws IOException {
        setup_round();
        sch.insertDice(0, 3, d3);
        sch.insertDice(3,4, d5);
        sch.insertDice(3,1, d2);
        sch.insertDice(2,0, d1);
        player.setSchema(sch);
        int num_dice = num_dice(sch);
        assertTrue(toolCard.effects(player, round, 3, 1, 3, 3, 2,0, 2, 4,1, 3  ));
        assertEquals(d2, sch.getTable(3,3).getDice());
        assertEquals(null, sch.getTable(2,4).getDice());
        assertEquals(num_dice, num_dice(sch));



    }
}
