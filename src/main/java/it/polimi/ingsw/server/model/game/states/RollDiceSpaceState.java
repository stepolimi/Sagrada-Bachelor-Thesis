package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.internal.mesages.Message;

import static it.polimi.ingsw.server.costants.Constants.ROLL_DICE_SPACE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.ROLL_DICE_SPACE_ACCEPTED;

public class RollDiceSpaceState extends State {
    /**
     * Changes randomly the values of every dice of the dice space.
     * @param round is the current round
     * @param message contains the current state
     */
    public void execute(Round round, Message message) {
        round.getBoard().getDiceSpace().rollDices();
        round.notifyChanges(ROLL_DICE_SPACE_ACCEPTED);
        round.getNextActions().remove(0);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return ROLL_DICE_SPACE_STATE;
    }
}
