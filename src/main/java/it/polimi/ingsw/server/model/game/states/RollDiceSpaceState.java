package it.polimi.ingsw.server.model.game.states;

import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.ROLL_DICE_SPACE_ACCEPTED;
import static it.polimi.ingsw.server.serverCostants.Constants.*;

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
