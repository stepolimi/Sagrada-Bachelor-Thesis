package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.ChooseValueState;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.CHOOSE_VALUE;
import static org.junit.jupiter.api.Assertions.assertSame;

class ChooseValueStateTest {
    private Round round;
    private ChooseValueState state;
    private Dice dice;

    private void testInit(){
        state = new ChooseValueState();
        List<Player> players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        dice = new Dice(Colour.ANSI_YELLOW, 5);
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

    private void chooseValue(int value){
        Message message = new Message(CHOOSE_VALUE);
        message.addIntegerArgument(value);
        state.execute(round,message);
    }

    @Test
    void execute(){
        testInit();
        round.setPendingDice(dice);

        //changes dice's value correctly
        chooseValue(4);
        assertSame(4,round.getPendingDice().getValue());
        assertSame(Colour.ANSI_YELLOW,round.getPendingDice().getColour());

        //changes dice's value correctly
        chooseValue(2);
        assertSame(2,round.getPendingDice().getValue());
        assertSame(Colour.ANSI_YELLOW,round.getPendingDice().getColour());

        //changes dice's value correctly
        chooseValue(7);
        assertSame(2,round.getPendingDice().getValue());
        assertSame(Colour.ANSI_YELLOW,round.getPendingDice().getColour());
    }
}
