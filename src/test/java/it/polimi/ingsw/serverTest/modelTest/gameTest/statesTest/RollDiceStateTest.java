package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiPlayer;
import it.polimi.ingsw.server.model.game.states.RollDiceState;
import it.polimi.ingsw.server.model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.ROLL_DICE;
import static org.junit.jupiter.api.Assertions.assertSame;

class RollDiceStateTest {
    private Round round;
    private RollDiceState state;
    private Dice dice;

    private void testInit(){
        state = new RollDiceState();
        List<Player> players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        GameMultiPlayer game = new GameMultiPlayer(players);
        Board board = game.getBoard();
        round = new Round(players.get(0),board,game.getRoundManager(), game);
        round.roundInit();
        dice = new Dice(Colour.ANSI_RED, 6);
        List<List<String>> nextActions = new ArrayList<>();
        nextActions.add(new ArrayList<>());
        round.setNextActions(nextActions);
    }

    private void rollDice(){
        Message message = new Message(ROLL_DICE);
        state.execute(round,message);
    }

    @Test
    void execute(){
        testInit();
        round.setPendingDice(dice);

        //rolls the dice correctly
        rollDice();
        assertSame(dice.getColour(),round.getPendingDice().getColour());
    }
}
