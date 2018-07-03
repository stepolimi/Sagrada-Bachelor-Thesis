package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.MoveDiceState;
import it.polimi.ingsw.server.model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.MOVE_DICE;
import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static it.polimi.ingsw.server.model.builders.ToolCardBuilder.buildToolCard;
import static junit.framework.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class MoveDiceStateTest {
    private Round round;
    private MoveDiceState state;
    private Dice dice;
    private List<Player> players;
    private Board board;

    private void testInit(){
        state = new MoveDiceState();
        players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        GameMultiplayer game = new GameMultiplayer(players);
        board = game.getBoard();
        round = new Round(players.get(0),board,game.getRoundManager(), game);
        round.roundInit();
        dice = new Dice(Colour.ANSI_YELLOW, 5);
        List<List<String>> nextActions = new ArrayList<>();
        nextActions.add(new ArrayList<>());
        nextActions.add(new ArrayList<>());
        round.setNextActions(nextActions);

    }

    private void setSchemas(){
        try {
            players.get(0).setSchema(buildSchema(1));
            players.get(1).setSchema(buildSchema(2));
            players.get(2).setSchema(buildSchema(5));
            players.get(0).getSchema().setPlayers(board.getNicknames());
            players.get(1).getSchema().setPlayers(board.getNicknames());
            players.get(2).getSchema().setPlayers(board.getNicknames());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveDice(int oldRow, int oldColumn, int row, int column){
        Message message = new Message(MOVE_DICE);
        message.addIntegerArgument(oldRow);
        message.addIntegerArgument(oldColumn);
        message.addIntegerArgument(row);
        message.addIntegerArgument(column);
        state.execute(round,message);
    }

    @Test
    void execute(){
        testInit();
        setSchemas();
        round.getCurrentPlayer().getSchema().silentInsertDice(0,1,dice);
        round.setUsingTool(buildToolCard(1));

        //Correct moveDice
        moveDice(0,1,0,0);
        assertNull(round.getCurrentPlayer().getSchema().getTable(0,1).getDice());
        assertSame(dice,round.getCurrentPlayer().getSchema().getTable(0,0).getDice());

        //Incorrect moveDice
        moveDice(0,0,2,1);
        assertSame(dice,round.getCurrentPlayer().getSchema().getTable(0,0).getDice());
        assertNull(round.getCurrentPlayer().getSchema().getTable(3,1).getDice());

        //Incorrect moveDice
        moveDice(3,2,0,1);
        assertSame(dice,round.getCurrentPlayer().getSchema().getTable(0,0).getDice());
        assertNull(round.getCurrentPlayer().getSchema().getTable(3,1).getDice());
    }

}
