package it.polimi.ingsw.Model.CardsTest.ToolCardTest;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Cards.ToolCards.Martelletto;
import it.polimi.ingsw.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        assertTrue(toolCard.effects(player, round, board.getDiceSpace()));
        assertEquals(sizeSpace, board.getDiceSpace().getListDice().size() );
    }


}
