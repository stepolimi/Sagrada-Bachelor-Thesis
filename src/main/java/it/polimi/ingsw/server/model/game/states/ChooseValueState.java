package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.exception.ChangeDiceValueException;

import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.CHOOSE_VALUE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.CHOOSE_VALUE_ACCEPTED;
import static it.polimi.ingsw.server.costants.MessageConstants.CHOOSE_VALUE_ERROR;

public class ChooseValueState extends State {
    private static String state = CHOOSE_VALUE_STATE;

    /**
     * Sets the value contained in the action list to the pending dice.
     * @param round is the current state
     * @param action contains the current state and the value to be set to the pending dice
     */
    public void execute(Round round, List action) {
        try {
            round.getPendingDice().setValue(Integer.parseInt((String) action.get(1)));
            round.getNextActions().remove(0);
            round.notifyChanges(CHOOSE_VALUE_ACCEPTED);
        } catch (ChangeDiceValueException e) {
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE,this.getClass().getName(),"execute");
            round.notifyChanges(CHOOSE_VALUE_ERROR);
        }
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
