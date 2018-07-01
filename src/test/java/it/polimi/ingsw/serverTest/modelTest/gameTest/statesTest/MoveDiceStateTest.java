package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.MoveDiceState;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
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
    private List action = new ArrayList();
    private Dice dice;
    private List<Player> players;

    private void testInit(){
        state = new MoveDiceState();
        players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        VirtualView view = new VirtualView();
        view.setConnection(new Connected());
        GameMultiplayer game = new GameMultiplayer(players);
        Board board = game.getBoard();
        board.setObserver(view);
        players.forEach(player -> player.setObserver(view));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveDice(String oldRow, String oldColumn, String row, String column){
        action.clear();
        action.add(MOVE_DICE);
        action.add(oldRow);
        action.add(oldColumn);
        action.add(row);
        action.add(column);
        state.execute(round,action);
    }

    @Test
    void execute(){
        testInit();
        setSchemas();
        round.getCurrentPlayer().getSchema().silentInsertDice(0,1,dice);
        round.setUsingTool(buildToolCard(1));

        //Correct moveDice
        moveDice("0","1","0","0");
        assertNull(round.getCurrentPlayer().getSchema().getTable(0,1).getDice());
        assertSame(dice,round.getCurrentPlayer().getSchema().getTable(0,0).getDice());

        //Incorrect moveDice
        moveDice("0","0","2","1");
        assertSame(dice,round.getCurrentPlayer().getSchema().getTable(0,0).getDice());
        assertNull(round.getCurrentPlayer().getSchema().getTable(3,1).getDice());

        //Incorrect moveDice
        moveDice("3","2","0","1");
        assertSame(dice,round.getCurrentPlayer().getSchema().getTable(0,0).getDice());
        assertNull(round.getCurrentPlayer().getSchema().getTable(3,1).getDice());
    }

}
