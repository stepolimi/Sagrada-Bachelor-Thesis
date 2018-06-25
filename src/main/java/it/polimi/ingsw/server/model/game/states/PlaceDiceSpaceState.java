package it.polimi.ingsw.server.model.game.states;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.PLACE_DICE_SPACE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.PLACE_DICE_SPACE_ACCEPTED;

public class PlaceDiceSpaceState extends State {
    private static String state = PLACE_DICE_SPACE_STATE;

    public void execute(Round round, List action) {
        round.getBoard().getDiceSpace().insertDice(round.getPendingDice());
        round.setPendingDice(null);
        round.notifyChanges(PLACE_DICE_SPACE_ACCEPTED);
        round.getNextActions().remove(0);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
