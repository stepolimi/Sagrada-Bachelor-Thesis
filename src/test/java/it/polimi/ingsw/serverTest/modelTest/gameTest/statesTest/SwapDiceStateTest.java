package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.game.states.SwapDiceState;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.SWAP_DICE;
import static org.junit.jupiter.api.Assertions.assertSame;


public class SwapDiceStateTest {
    private List<Player> players;
    private Board board;
    private GameMultiplayer game;
    private Round round;
    private SwapDiceState state = new SwapDiceState();
    private List action = new ArrayList();
    private VirtualView view;
    private Dice dice;
    private Dice dice2;

    private void testInit(){
        players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        view = new VirtualView();
        view.setConnection(new Connected());
        game = new GameMultiplayer(players);
        board = game.getBoard();
        board.setObserver(view);
        round = new Round(players.get(0),board,null, game);
        round.roundInit();
        dice = new Dice(Colour.ANSI_RED, 6);
        dice2 = new Dice(Colour.ANSI_GREEN,5);
        List<List<String>> nextActions = new ArrayList<>();
        nextActions.add(new ArrayList<>());
        round.setNextActions(nextActions);
    }

    private void insertDiceRoundTrack() {
        try {
            board.getRoundTrack().insertDice( dice,0);
        } catch (InsertDiceException e) {
            e.printStackTrace();
        }
    }

    private void swapDice(String nRound, String dice){
        action.clear();
        action.add(SWAP_DICE);
        action.add(nRound);
        action.add(dice);
        state.execute(round,action);
    }

    @Test
    void execute(){
        testInit();
        insertDiceRoundTrack();
        round.setPendingDice(dice2);
        swapDice("0","0");

        assertSame(dice2,board.getRoundTrack().getDice(0,0));
        assertSame(dice,round.getPendingDice());
        swapDice("1","0");

        assertSame(dice2,board.getRoundTrack().getDice(0,0));
        assertSame(dice,round.getPendingDice());
    }
}
