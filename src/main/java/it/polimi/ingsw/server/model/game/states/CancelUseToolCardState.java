package it.polimi.ingsw.server.model.game.states;

import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.CANCEL_USE_TOOL_CARD_ACCEPTED;
import static it.polimi.ingsw.server.serverCostants.Constants.*;

public class CancelUseToolCardState extends State {
    private static String state = CANCEL_USE_TOOL_CARD_STATE;

    public void execute(Round round, List action) {
        round.getNextActions().clear();
        round.getCurrentPlayer().incrementFavor(round.getFavorsDecremented());
        round.getUsingTool().setUsed(round.getCardWasUsed());
        round.setUsedCard(false);
        round.notifyChanges(CANCEL_USE_TOOL_CARD_ACCEPTED);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }

}
