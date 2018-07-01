package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.CancelUseToolCardState;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.CANCEL_USE_TOOL_CARD;
import static it.polimi.ingsw.server.model.builders.ToolCardBuilder.buildToolCard;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

class CancelUseToolCardStateTest {
    private Board board;
    private Round round;
    private CancelUseToolCardState state;
    private List action = new ArrayList();

    private void testInit(){
        state = new CancelUseToolCardState();
        List<Player> players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        VirtualView view = new VirtualView();
        view.setConnection(new Connected());
        GameMultiplayer game = new GameMultiplayer(players);
        board = game.getBoard();
        board.setObserver(view);
        round = new Round(players.get(0),board,game.getRoundManager(), game);
    }

    private void cancelUseToolCard() {
        action.clear();
        action.add(CANCEL_USE_TOOL_CARD);
        state.execute(round, action);
    }

    @Test
    void execute(){
        testInit();
        round.roundInit();
        ToolCard toolCard = buildToolCard(1);
        round.setUsingTool(toolCard);
        round.setFavorsDecremented(1);
        int oldFavors = round.getCurrentPlayer().getFavour();

        cancelUseToolCard();
        assertNull(round.getUsingTool());
        assertFalse(round.getCardWasUsed());
        assertEquals(oldFavors +1,round.getCurrentPlayer().getFavour());

    }

}
