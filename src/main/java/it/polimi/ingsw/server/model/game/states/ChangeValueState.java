package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.exception.ChangeDiceValueException;
import it.polimi.ingsw.server.internal.mesages.Message;

import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.CHANGE_VALUE_STATE;
import static it.polimi.ingsw.server.costants.Constants.DECREMENT;
import static it.polimi.ingsw.server.costants.Constants.INCREMENT;
import static it.polimi.ingsw.server.costants.LogConstants.STATE_EXECUTE;
import static it.polimi.ingsw.server.costants.MessageConstants.CHANGE_VALUE_ACCEPTED;
import static it.polimi.ingsw.server.costants.MessageConstants.CHANGE_VALUE_ERROR;

public class ChangeValueState extends State {
    /**
     * Increments or decrements the value of the drafted dice by one.
     * @param round is the current round
     * @param message contains current state and "Increment" or "Decrement" instruction for the pending dice
     */
    public void execute(Round round, Message message) {
        try {
            if (message.getStringArgument(0).equals(INCREMENT)) {
                round.getPendingDice().incrementValue();
            } else if (message.getStringArgument(0).equals(DECREMENT))
                round.getPendingDice().decrementValue();
            round.getNextActions().remove(0);
            round.notifyChanges(CHANGE_VALUE_ACCEPTED);
        } catch (ChangeDiceValueException e) {
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE,this.getClass().getName(),STATE_EXECUTE);
            round.notifyChanges(CHANGE_VALUE_ERROR);
        }
        giveLegalActions(round);
    }

    @Override
    public String toString() { return CHANGE_VALUE_STATE; }
}
