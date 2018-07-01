package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.FlipDiceState;
import it.polimi.ingsw.server.model.game.states.RollDiceSpaceState;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.ROLL_DICE_SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlipDiceStateTest {
    private Round round;
    private FlipDiceState state;
    private Dice dice;
    private Dice dice2;
    private List action = new ArrayList();

    private void testInit(){
        state = new FlipDiceState();
        List<Player> players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        dice = new Dice(Colour.ANSI_YELLOW, 5);
        dice2 = new Dice(Colour.ANSI_BLUE,3);
        VirtualView view = new VirtualView();
        view.setConnection(new Connected());
        GameMultiplayer game = new GameMultiplayer(players);
        Board board = game.getBoard();
        board.setObserver(view);
        round = new Round(players.get(0),board,game.getRoundManager(), game);
        round.roundInit();
        List<List<String>> nextActions = new ArrayList<>();
        nextActions.add(new ArrayList<>());
        nextActions.add(new ArrayList<>());
        round.setNextActions(nextActions);
    }

    private void flipDice(){
        action.clear();
        action.add(ROLL_DICE_SPACE);
        state.execute(round,action);
    }

    @Test
    void execute(){
        testInit();

        //flips the dice correctly
        round.setPendingDice(dice);
        flipDice();
        assertEquals(2,round.getPendingDice().getValue());
        assertEquals(Colour.ANSI_YELLOW,round.getPendingDice().getColour());

        //flips the dice correctly
        round.setPendingDice(dice2);
        flipDice();
        assertEquals(4,round.getPendingDice().getValue());
        assertEquals(Colour.ANSI_BLUE,round.getPendingDice().getColour());
    }
}
