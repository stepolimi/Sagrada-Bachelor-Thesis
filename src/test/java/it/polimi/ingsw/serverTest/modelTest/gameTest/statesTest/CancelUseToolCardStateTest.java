package it.polimi.ingsw.serverTest.modelTest.gameTest.statesTest;

import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.CancelUseToolCardState;
import it.polimi.ingsw.server.model.game.states.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.CANCEL_USE_TOOL_CARD;
import static it.polimi.ingsw.server.model.builders.ToolCardBuilder.buildToolCard;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

class CancelUseToolCardStateTest {
    private Round round;
    private CancelUseToolCardState state;

    private void testInit(){
        state = new CancelUseToolCardState();
        List<Player> players = new ArrayList<>();
        players.add(new Player("player 1"));
        players.add(new Player("player 2"));
        players.add(new Player("player 3"));
        GameMultiplayer game = new GameMultiplayer(players);
        Board board = game.getBoard();
        round = new Round(players.get(0),board,game.getRoundManager(), game);
    }

    private void cancelUseToolCard() {
        Message message = new Message(CANCEL_USE_TOOL_CARD);
        state.execute(round, message);
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
