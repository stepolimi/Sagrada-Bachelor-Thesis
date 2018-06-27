package it.polimi.ingsw.server.model.game.states;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.CANCEL_USE_TOOL_CARD_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.CANCEL_USE_TOOL_CARD_ACCEPTED;

public class CancelUseToolCardState extends State {
    private static String state = CANCEL_USE_TOOL_CARD_STATE;

    /**
     * Sets everything at the value that they had before the tool card was used.
     * @param round is the current round
     * @param action contains the current state
     */
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
