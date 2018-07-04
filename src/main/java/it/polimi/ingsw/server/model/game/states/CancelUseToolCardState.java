package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.internal.mesages.Message;

import static it.polimi.ingsw.server.costants.Constants.CANCEL_USE_TOOL_CARD_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.CANCEL_USE_TOOL_CARD_ACCEPTED;

public class CancelUseToolCardState extends State {
    /**
     * Sets everything at the value that they had before the tool card was used.
     * @param round is the current round
     * @param message contains the current state
     */
    public void execute(Round round, Message message) {
        round.getNextActions().clear();
        round.getCurrentPlayer().incrementFavor(round.getFavorsDecremented());
        round.getUsingTool().setUsed(round.getCardWasUsed());
        round.setUsedCard(false);
        round.notifyChanges(CANCEL_USE_TOOL_CARD_ACCEPTED);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return CANCEL_USE_TOOL_CARD_STATE;
    }

}
