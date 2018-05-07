package it.polimi.ingsw.Model.CardsTest.ToolCardTest;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Cards.ToolCards.Riga;
import it.polimi.ingsw.Model.game.states.Round;
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
    Riga toolCard = new Riga();
    Dice d1= new Dice(Colour.ANSI_BLUE, 2);
    Dice d2= new Dice(Colour.ANSI_GREEN, 1);
    Dice d3 = new Dice(Colour.ANSI_RED, 5);
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