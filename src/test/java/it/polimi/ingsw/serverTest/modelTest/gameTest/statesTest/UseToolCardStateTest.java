package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.cards.decks.DeckToolsCard;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.game.states.UseToolCardState;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.CANCEL_USE_TOOL_CARD;
import static it.polimi.ingsw.server.costants.MessageConstants.END_TURN;
import static it.polimi.ingsw.server.costants.MessageConstants.USE_TOOL_CARD;
import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static it.polimi.ingsw.server.model.builders.ToolCardBuilder.buildToolCard;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UseToolCardStateTest {
    private List <Player> players;
    private Board board;
    private GameMultiplayer game;
    private Round round;
    private UseToolCardState state = new UseToolCardState();
    private List action = new ArrayList();
    private List<ToolCard> toolCards;
    private VirtualView view;

    private void testInit(){
        players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        view = new VirtualView();
        view.setConnection(new Connected());
        for(int i = 0; i<3; i++) {
            try {
                players.get(i).setObserver(view);
                players.get(i).setSchema(buildSchema(i+1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        game = new GameMultiplayer(players);
        board = game.getBoard();
        board.setObserver(view);
        round = new Round(players.get(0),board,null, game);
        toolCards = new ArrayList<>();
        toolCards.add(buildToolCard(9));
        toolCards.add(buildToolCard(8));
        toolCards.add(buildToolCard(7));
        toolCards.add(buildToolCard(2));
        toolCards.add(buildToolCard(5));
        toolCards.add(buildToolCard(6));
        board.setDeckTool(toolCards);
        round.roundInit();
    }

    private void useToolCard(String n){
        action.clear();
        action.add(USE_TOOL_CARD);
        action.add(n);
        state.execute(round,action);
        action.clear();
    }
    private void endTurn(){
        action.clear();
        action.add(END_TURN);
        round.execute(action);
    }

    @Test
    void execute(){
        testInit();
        int oldFavors = round.getCurrentPlayer().getFavour();

        //incorrect card usage
        useToolCard("7");
        assertEquals(oldFavors,round.getCurrentPlayer().getFavour());

        //correct card usage
        useToolCard("8");
        assertEquals(oldFavors -1 , round.getCurrentPlayer().getFavour());

        endTurn();
        oldFavors = round.getCurrentPlayer().getFavour();
        useToolCard("8");
        assertEquals(oldFavors -2 , round.getCurrentPlayer().getFavour());

        endTurn();
        round.getCurrentPlayer().setFavour(1);

        useToolCard("8");
        assertEquals(1 , round.getCurrentPlayer().getFavour());

        useToolCard("9");
        assertEquals(0 , round.getCurrentPlayer().getFavour());

        endTurn();
        round.getCurrentPlayer().setFavour(1);
        useToolCard("2");
        assertEquals(1 , round.getCurrentPlayer().getFavour());

        useToolCard("5");
        assertEquals(1 , round.getCurrentPlayer().getFavour());

        useToolCard("7");
        assertEquals(0, round.getCurrentPlayer().getFavour());

    }
}