package it.polimi.ingsw.server.model.game.states;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.ROLL_DICE_SPACE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.ROLL_DICE_SPACE_ACCEPTED;

public class RollDiceSpaceState extends State {
    private static String state = ROLL_DICE_SPACE_STATE;

    public void execute(Round round, List action) {
        round.getBoard().getDiceSpace().rollDices();
        round.notifyChanges(ROLL_DICE_SPACE_ACCEPTED);
        round.getNextActions().remove(0);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
