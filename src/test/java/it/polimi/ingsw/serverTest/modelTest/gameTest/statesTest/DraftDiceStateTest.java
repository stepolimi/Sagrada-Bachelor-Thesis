package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiPlayer;
import it.polimi.ingsw.server.model.game.states.DraftDiceState;
import it.polimi.ingsw.server.model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.DRAFT_DICE;
import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DraftDiceStateTest {
    private Round round;
    private DraftDiceState state;
    private Dice dice;
    private List<Player> players;
    private Board board;

    private void testInit(){
        state = new DraftDiceState();
        players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        GameMultiPlayer game = new GameMultiPlayer(players);
        board = game.getBoard();
        round = new Round(players.get(0),board,game.getRoundManager(), game);
        round.roundInit();
        dice = new Dice(Colour.ANSI_YELLOW, 5);
        List<List<String>> nextActions = new ArrayList<>();
        nextActions.add(new ArrayList<>());
        round.setNextActions(nextActions);
    }

    private void setSchemas() {
        players.get(0).setSchema(buildSchema(2));
        players.get(1).setSchema(buildSchema(3));
        players.get(2).setSchema(buildSchema(5));
    }

    private void draftDice(int index){
        Message message = new Message(DRAFT_DICE);
        message.addIntegerArgument(index);
        state.execute(round,message);
    }

    @Test
    void execute(){
        testInit();
        setSchemas();
        board.getDiceSpace().getListDice().add(0,dice);
        assertFalse(round.isDraftedDice());
        int oldDiceSpaceSize = board.getDiceSpace().getListDice().size();

        //Extracts the dice correctly from the diceSpace and sets it as pendingDice
        draftDice(0);
        assertSame(dice,round.getPendingDice());
        assertFalse(board.getDiceSpace().getListDice().contains(dice));
        assertTrue(round.isDraftedDice());
        assertEquals(oldDiceSpaceSize -1, board.getDiceSpace().getListDice().size());

        //Manages correctly an invalid index of the dice
        round.setPendingDice(null);
        oldDiceSpaceSize = board.getDiceSpace().getListDice().size();
        draftDice(10);
        assertNull(round.getPendingDice());
        assertEquals(oldDiceSpaceSize,board.getDiceSpace().getListDice().size());
    }
}