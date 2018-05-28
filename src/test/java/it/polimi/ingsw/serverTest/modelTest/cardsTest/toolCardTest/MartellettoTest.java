package it.polimi.ingsw.serverTest.modelTest.cardsTest.toolCardTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.cards.toolCards.Martelletto;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
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
    private VirtualView view = new VirtualView();
    private Martelletto toolCard = new Martelletto();
    private Dice d1= new Dice(Colour.ANSI_BLUE, 5);
    private Dice d2= new Dice(Colour.ANSI_GREEN, 1);
    private Dice d3= new Dice(Colour.ANSI_RED, 3);
    private List<Dice> dices = new ArrayList<Dice>();


    public void setup(){
        toolCard.dump();

        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        round = new Round(player,board);
        board.setObserver(view);
        view.setConnection(new Connected());
        board.setDiceSpace(new ArrayList<Dice>());
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
