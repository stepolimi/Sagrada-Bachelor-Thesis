package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.exception.ChangeDiceValueException;
import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.ChangeValueState;
import it.polimi.ingsw.server.model.game.states.ChooseValueState;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.DECREMENT;
import static it.polimi.ingsw.server.costants.Constants.INCREMENT;
import static it.polimi.ingsw.server.costants.MessageConstants.CHOOSE_VALUE;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChangeValueStateTest {
    private Round round;
    private ChangeValueState state;
    private Dice dice;
    private Dice dice2;
    private Dice dice3;

    private void testInit() {
        state = new ChangeValueState();
        List<Player> players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        dice = new Dice(Colour.ANSI_YELLOW, 5);
        dice2 = new Dice(Colour.ANSI_YELLOW, 6);
        dice3 = new Dice(Colour.ANSI_YELLOW, 1);
        VirtualView view = new VirtualView();
        view.setConnection(new Connected());
        GameMultiplayer game = new GameMultiplayer(players);
        Board board = game.getBoard();
        board.setObserver(view);
        round = new Round(players.get(0), board, game.getRoundManager(), game);
        round.roundInit();
        List<List<String>> nextActions = new ArrayList<>();
        nextActions.add(new ArrayList<>());
        nextActions.add(new ArrayList<>());
        round.setNextActions(nextActions);
    }

    private void changeValue(String change) {
        Message message = new Message(CHOOSE_VALUE);
        message.addStringArguments(change);
        state.execute(round, message);
    }

    @Test
    void execute() {
        testInit();

        //Increments the pending dice's value correctly
        round.setPendingDice(dice);
        changeValue(INCREMENT);
        assertSame(6, round.getPendingDice().getValue());
        assertSame(Colour.ANSI_YELLOW, round.getPendingDice().getColour());

        //Decrements the pending dice's value correctly
        changeValue(DECREMENT);
        assertSame(5, round.getPendingDice().getValue());
        assertSame(Colour.ANSI_YELLOW, round.getPendingDice().getColour());

        //Doesn't increment the dice's value if it is 6
        round.setPendingDice(dice2);
        changeValue(INCREMENT);
        assertSame(6, round.getPendingDice().getValue());
        assertSame(Colour.ANSI_YELLOW, round.getPendingDice().getColour());

        //Doesn't decrement the dice's value if it is 1
        round.setPendingDice(dice3);
        changeValue(DECREMENT);
        assertSame(1, round.getPendingDice().getValue());
        assertSame(Colour.ANSI_YELLOW, round.getPendingDice().getColour());


    }
}
