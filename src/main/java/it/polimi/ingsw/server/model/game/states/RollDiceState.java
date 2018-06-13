package it.polimi.ingsw.server.model.game.states;

import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.ROLL_DICE_ACCEPTED;
import static it.polimi.ingsw.server.serverCostants.Constants.*;

public class RollDiceState extends State {
    private static String state = ROLL_DICE_STATE;

    public void execute(Round round, List action) {
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
