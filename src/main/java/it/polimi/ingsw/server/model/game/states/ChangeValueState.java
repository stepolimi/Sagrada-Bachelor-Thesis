package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.exception.ChangeDiceValueException;

import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.CHANGE_VALUE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.CHANGE_VALUE_ACCEPTED;
import static it.polimi.ingsw.server.costants.MessageConstants.CHANGE_VALUE_ERROR;

public class ChangeValueState extends State {
    private static String state = CHANGE_VALUE_STATE;

    /**
     * Increments or decrements the value of the drafted dice by one.
     * @param round is the current round
     * @param action contains current state and "Increment" or "Decrement" instruction for the pending dice
     */
    public void execute(Round round, List action) {
        try {
            if (action.get(1).equals("Increment")) {
                round.getPendingDice().incrementValue();
            } else if (action.get(1).equals("Decrement"))
                round.getPendingDice().decrementValue();
            round.getNextActions().remove(0);
            round.notifyChanges(CHANGE_VALUE_ACCEPTED);
        } catch (ChangeDiceValueException e) {
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE,this.getClass().getName(),"execute");
            round.notifyChanges(CHANGE_VALUE_ERROR);
        }
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
