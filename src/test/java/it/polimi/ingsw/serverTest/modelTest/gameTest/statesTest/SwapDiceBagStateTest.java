package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.game.states.SwapDiceBagState;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.SWAP_DICE_BAG;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SwapDiceBagStateTest {
    private Board board;
    private Round round;
    private SwapDiceBagState state;
    private Dice dice;

    private void testInit(){
        state = new SwapDiceBagState();
        List<Player> players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        VirtualView view = VirtualView.getVirtualView();
        GameMultiplayer game = new GameMultiplayer(players);
        board = game.getBoard();
        board.setObserver(view);
        round = new Round(players.get(0),board,game.getRoundManager(), game);
        round.roundInit();
        dice = new Dice(Colour.ANSI_RED, 6);
        List<List<String>> nextActions = new ArrayList<>();
        nextActions.add(new ArrayList<>());
        round.setNextActions(nextActions);
    }

    private void swapDice(){
        Message message = new Message(SWAP_DICE_BAG);
        state.execute(round,message);
    }

    @Test
    void execute(){
        testInit();
        round.setPendingDice(dice);
        int oldDiceBagSize = board.getDiceBag().getDices().size();

        //swaps the dices correctly
        swapDice();
        assertNotNull(round.getPendingDice());
        assertEquals(oldDiceBagSize,board.getDiceBag().getDices().size());
    }

}
