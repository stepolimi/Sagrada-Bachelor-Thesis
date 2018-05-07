package it.polimi.ingsw.Model.CardsTest.ToolCardTest;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Cards.ToolCards.Pinza;
import it.polimi.ingsw.Model.Colour;
import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PinzaTest {
    private List<Player> players = new ArrayList<Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    Pinza toolCard = new Pinza();

    List<Dice> dices = new ArrayList<Dice>();


    public void setup(){
        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        round = new Round(player,board);

        dices.addAll(board.getDiceSpace().getListDice());
        round.setCurrentPlayer(player);
        round.setPlayerIndex(3);
    }

    @Test
    void correct_change(){

        setup();
        Dice d1= new Dice(Colour.ANSI_BLUE, 5);

        round.setPendingDice(d1);
        int sizeSpace = board.getDiceSpace().getListDice().size();
        round.setTurnNumber(4);
        player.setTurn(true);
        assertTrue(toolCard.effects(player, round, -1));
        assertEquals(4, board.getDiceSpace().getListDice().get(0).getValue() );
        assertEquals(1, board.getDiceSpace().getListDice().size());
    }
    @Test
    void wrong_change(){

        setup();
        Dice d1= new Dice(Colour.ANSI_BLUE, 1);

        round.setPendingDice(d1);
        int sizeSpace = board.getDiceSpace().getListDice().size();
        round.setTurnNumber(4);
        player.setTurn(true);
        assertFalse(toolCard.effects(player, round, -1));
        assertEquals(1, board.getDiceSpace().getListDice().size());
    }
}
