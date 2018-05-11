package it.polimi.ingsw.Model.CardsTest.ToolCardTest;

import it.polimi.ingsw.Server.Model.board.Board;
import it.polimi.ingsw.Server.Model.cards.ToolCards.Martelletto;
import it.polimi.ingsw.Server.Model.board.Colour;
import it.polimi.ingsw.Server.Model.board.Dice;
import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MartellettoTest {
    private List<Player> players = new ArrayList<Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    Martelletto toolCard = new Martelletto();
    Dice d1= new Dice(Colour.ANSI_BLUE, 5);
    Dice d2= new Dice(Colour.ANSI_GREEN, 1);
    Dice d3= new Dice(Colour.ANSI_RED, 3);
    List<Dice> dices = new ArrayList<Dice>();


    public void setup(){
        toolCard.dump();

        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        round = new Round(player,board);
        board.getDiceSpace().insertDice(d1);
        board.getDiceSpace().insertDice(d2);
        board.getDiceSpace().insertDice(d3);
        dices.addAll(board.getDiceSpace().getListDice());
        round.setCurrentPlayer(player);
        round.setPlayerIndex(3);
    }




    @Test
    void correct_roll(){

        setup();
        int sizeSpace = board.getDiceSpace().getListDice().size();
        round.setTurnNumber(4);
        player.setTurn(true);
        assertTrue(toolCard.effects(player, round));
        assertEquals(sizeSpace, board.getDiceSpace().getListDice().size() );

    }

    @Test
    void is_not_my_turn(){
        setup();
        int sizeSpace = board.getDiceSpace().getListDice().size();
        round.setTurnNumber(4);
        player.setTurn(false);
        assertFalse(toolCard.effects(player, round));
        assertEquals(sizeSpace, board.getDiceSpace().getListDice().size() );
    }

    @Test
    void pending_dice(){
        setup();
        int sizeSpace = board.getDiceSpace().getListDice().size();
        round.setPendingDice(new  Dice(Colour.ANSI_RED, 3));
        round.setTurnNumber(4);
        player.setTurn(true);
        assertFalse(toolCard.effects(player, round));
        assertEquals(sizeSpace, board.getDiceSpace().getListDice().size() );
    }

    @Test
    void first_turn(){
        setup();
        int sizeSpace = board.getDiceSpace().getListDice().size();
        round.setPendingDice(new  Dice(Colour.ANSI_RED, 3));
        round.setTurnNumber(1);
        player.setTurn(true);
        assertFalse(toolCard.effects(player, round));
        assertEquals(sizeSpace, board.getDiceSpace().getListDice().size() );
    }


}
