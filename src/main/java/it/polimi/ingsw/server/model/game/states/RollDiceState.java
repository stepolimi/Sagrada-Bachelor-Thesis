package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.internalMesages.Message;

import static it.polimi.ingsw.server.costants.Constants.ROLL_DICE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.ROLL_DICE_ACCEPTED;

public class RollDiceState extends State {
    private static String state = ROLL_DICE_STATE;

    /**
     * Changes randomly the value of the pending dice.
     * @param round is the current round
     * @param message contains the current state
     */
    public void execute(Round round, Message message) {
        round.getPendingDice().rollDice();
        round.getNextActions().remove(0);
        round.notifyChanges(ROLL_DICE_ACCEPTED);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
