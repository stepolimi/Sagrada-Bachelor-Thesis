package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.ChangeDiceValueException;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.CHANGE_VALUE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.CHANGE_VALUE_ACCEPTED;
import static it.polimi.ingsw.server.costants.MessageConstants.CHANGE_VALUE_ERROR;

public class ChangeValueState extends State {
    private static String state = CHANGE_VALUE_STATE;

    public void execute(Round round, List action) {
        try {
            if (action.get(1).equals("Increment")) {
                round.getPendingDice().incrementValue();
            } else if (action.get(1).equals("Decrement"))
                round.getPendingDice().decrementValue();
            round.getNextActions().remove(0);
            round.notifyChanges(CHANGE_VALUE_ACCEPTED);
        } catch (ChangeDiceValueException e) {
            System.out.println(e.getMessage());
            round.notifyChanges(CHANGE_VALUE_ERROR);
        }
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
