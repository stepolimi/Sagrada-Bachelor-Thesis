package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.internalMesages.Message;

import static it.polimi.ingsw.server.costants.Constants.PLACE_DICE_SPACE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.PLACE_DICE_SPACE_ACCEPTED;

public class PlaceDiceSpaceState extends State {
    private static String state = PLACE_DICE_SPACE_STATE;

    /**
     * Puts the pending dice into the dice space.
     * @param round is the current round
     * @param message contains the current state
     */
    public void execute(Round round, Message message) {
        round.getBoard().getDiceSpace().insertDice(round.getPendingDice());
        round.setPendingDice(null);
        round.notifyChanges(PLACE_DICE_SPACE_ACCEPTED);
        round.getNextActions().remove(0);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
