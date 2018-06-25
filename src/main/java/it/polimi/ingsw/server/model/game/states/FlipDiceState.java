package it.polimi.ingsw.server.model.game.states;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.FLIP_DICE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.FLIP_DICE_ACCEPTED;

public class FlipDiceState extends State {
    private static String state = FLIP_DICE_STATE;

    public void execute(Round round, List action) {
        round.getPendingDice().flipDice();
        round.getNextActions().remove(0);
        round.notifyChanges(FLIP_DICE_ACCEPTED);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
