package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.cards.tool.cards.ToolCard;
import it.polimi.ingsw.server.model.game.GameMultiPlayer;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.game.states.UseToolCardState;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.END_TURN;
import static it.polimi.ingsw.server.costants.MessageConstants.USE_TOOL_CARD;
import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static it.polimi.ingsw.server.model.builders.ToolCardBuilder.buildToolCard;
import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UseToolCardStateTest {
    private Round round;
    private UseToolCardState state = new UseToolCardState();

    private void testInit(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        for(int i = 0; i<3; i++) {
            players.get(i).setSchema(buildSchema(i+1));
        }
        GameMultiPlayer game = new GameMultiPlayer(players);
        Board board = game.getBoard();
        round = new Round(players.get(0),board,game.getRoundManager(), game);
        List<ToolCard> toolCards = new ArrayList<>();
        toolCards.add(buildToolCard(9));
        toolCards.add(buildToolCard(8));
        toolCards.add(buildToolCard(7));
        toolCards.add(buildToolCard(2));
        toolCards.add(buildToolCard(5));
        toolCards.add(buildToolCard(6));
        board.setDeckTool(toolCards);
        round.roundInit();
    }

    private void useToolCard(int n){
        Message message = new Message(USE_TOOL_CARD);
        message.addIntegerArgument(n);
        state.execute(round,message);
    }
    private void endTurn(){
        Message message = new Message(END_TURN);
        round.execute(message);
    }

    @Test
    void execute(){
        testInit();
        int oldFavors = round.getCurrentPlayer().getFavour();

        //incorrect card usage for card's restrictions
        useToolCard(7);
        assertEquals(oldFavors,round.getCurrentPlayer().getFavour());
        assertFalse(round.getCardWasUsed());

        //correct card usage
        useToolCard(8);
        assertEquals(oldFavors -1 , round.getCurrentPlayer().getFavour());

        endTurn();
        //correct usage of an already used card
        oldFavors = round.getCurrentPlayer().getFavour();
        useToolCard(8);
        assertEquals(oldFavors -2 , round.getCurrentPlayer().getFavour());

        endTurn();
        round.getCurrentPlayer().setFavour(1);

        //incorrect card usage for favors
        useToolCard(8);
        assertEquals(1 , round.getCurrentPlayer().getFavour());

        useToolCard(9);
        assertEquals(0 , round.getCurrentPlayer().getFavour());

        endTurn();
        round.getCurrentPlayer().setFavour(1);
        useToolCard(2);
        assertEquals(1 , round.getCurrentPlayer().getFavour());

        useToolCard(5);
        assertEquals(1 , round.getCurrentPlayer().getFavour());


        useToolCard(7);
        assertEquals(0, round.getCurrentPlayer().getFavour());
        assertEquals(7,round.getUsingTool().getNumber());
    }
}