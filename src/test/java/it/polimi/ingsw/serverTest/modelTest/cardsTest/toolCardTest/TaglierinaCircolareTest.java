package it.polimi.ingsw.serverTest.modelTest.cardsTest.toolCardTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.cards.toolCards.TaglierinaCircolare;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaglierinaCircolareTest {
    private List<Player> players = new ArrayList<Player>();
    private Player player = new Player("player 1");
    private Player player2 = new Player("player 2");
    private Player player3 = new Player("player 3");
    private Board board ;
    private Round round ;
    private VirtualView view= new VirtualView();
    private TaglierinaCircolare toolCard = new TaglierinaCircolare();
    private Dice d1= new Dice(Colour.ANSI_BLUE, 1);
    private Dice d2= new Dice(Colour.ANSI_GREEN, 2);



    private void setup_round(){
        toolCard.dump();

        players.add(player);
        players.add(player2);
        players.add(player3);
        board = new Board(players);
        round = new Round(player,board,null);
        board.setObserver(view);
        view.setConnection(new Connected());
        board.setDiceSpace(new ArrayList<Dice>());
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
