package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.internal.mesages.Message;

import static it.polimi.ingsw.server.costants.Constants.FLIP_DICE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.FLIP_DICE_ACCEPTED;

public class FlipDiceState extends State {
    /**
     * Flips the pending dice.
     * @param round is the current round
     * @param message contains the current state
     */
    public void execute(Round round, Message message) {
        round.getPendingDice().flipDice();
        round.getNextActions().remove(0);
        round.notifyChanges(FLIP_DICE_ACCEPTED);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return FLIP_DICE_STATE;
    }
}
