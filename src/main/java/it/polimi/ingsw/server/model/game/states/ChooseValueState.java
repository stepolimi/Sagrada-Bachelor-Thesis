package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.ChangeDiceValueException;

import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.CHOOSE_VALUE_ACCEPTED;
import static it.polimi.ingsw.costants.GameConstants.CHOOSE_VALUE_ERROR;
import static it.polimi.ingsw.server.serverCostants.Constants.*;

public class ChooseValueState extends State {
    private static String state = CHOOSE_VALUE_STATE;

    public void execute(Round round, List action) {
        try {
            round.getPendingDice().setValue(Integer.parseInt((String) action.get(1)));
            round.getNextActions().remove(0);
            round.notifyChanges(CHOOSE_VALUE_ACCEPTED);
        } catch (ChangeDiceValueException e) {
            System.out.println(e.getMessage());
            round.notifyChanges(CHOOSE_VALUE_ERROR);
        }
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
