package it.polimi.ingsw.Model.CardsTest.ToolCardTest;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Cards.ToolCards.TaglierinaCircolare;
import it.polimi.ingsw.Model.Colour;
import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaglierinaCircolareTest {
    private List<Player> players = new ArrayList<Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    TaglierinaCircolare toolCard = new TaglierinaCircolare();
    Dice d1= new Dice(Colour.ANSI_BLUE, 1);
    Dice d2= new Dice(Colour.ANSI_GREEN, 2);



    private void setup_round(){
        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        round = new Round(player,board);
        round.setPendingDice(d1);
        board.getRoundTrack().insertDice(d2, 3);



    }

    @Test
    void correct_use(){
        setup_round();
        toolCard.effects(player, round, 3, 0 );
        assertEquals(d1, board.getRoundTrack().getDice(3,0));
        assertEquals(d2, board.getDiceSpace().getListDice().get(0));
    }




}
