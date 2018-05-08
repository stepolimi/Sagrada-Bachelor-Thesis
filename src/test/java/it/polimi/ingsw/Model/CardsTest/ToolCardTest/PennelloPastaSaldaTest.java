package it.polimi.ingsw.Model.CardsTest.ToolCardTest;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Cards.ToolCards.PennelloPastaSalda;
import it.polimi.ingsw.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PennelloPastaSaldaTest {
    Schema sch;
    private List<Player> players = new ArrayList<Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    PennelloPastaSalda toolCard = new PennelloPastaSalda();
    Dice d1= new Dice(Colour.ANSI_BLUE, 1);
    Dice d2= new Dice(Colour.ANSI_GREEN, 2);

    {
        try {
            sch = new Schema().schemaInit(7);
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


    }



    public void setupSchema() throws IOException {
        player.setSchema(sch);



        sch.insertDice(0, 3, d1);
        sch.insertDice(1,3, d2);

    }

    @Test
    void roll_insertion() throws IOException {
        setup_round();
        setupSchema();
        Dice d3 = new Dice(Colour.ANSI_PURPLE, 1);
        assertTrue(toolCard.placeDiceToSchema(2, 3, d3, player.getSchema(), 6));
        if(toolCard.placeDiceToSchema(2, 3, d3, player.getSchema(), 6))
            sch.insertDice(2,3,d3);
        assertEquals(sch.getTable(2,3).getDice(), d3);

    }
    @Test
    void roll_diceSpace() throws IOException {
        setup_round();
        setupSchema();
        Dice d3 = new Dice(Colour.ANSI_PURPLE, 2);
        assertFalse(toolCard.placeDiceToSchema(2, 3, d3, player.getSchema(), 6));


    }

    @Test
    void not_pending() throws IOException {
        setup_round();
        setupSchema();
        toolCard.effects(player, round, 0, 0);
        assertEquals(0, board.getDiceSpace().getListDice().size());
    }
}
