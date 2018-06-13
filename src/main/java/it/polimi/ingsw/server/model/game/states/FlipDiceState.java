package it.polimi.ingsw.server.model.game.states;

import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.FLIP_DICE_ACCEPTED;
import static it.polimi.ingsw.server.serverCostants.Constants.*;

public class FlipDiceState extends State {
    private static String state = FLIP_DICE_STATE;

    public void execute(Round round, List action) {
        round.getPendingDice().flipDice();
        round.getNextActions().remove(0);
        round.notifyChanges(FLIP_DICE_ACCEPTED);
        giveLegalActions(round);
    }

    public String nextState(List action) {
        return action.get(0) + "State";
    }

    @Override
    public String toString() {
        return state;
    }
}
