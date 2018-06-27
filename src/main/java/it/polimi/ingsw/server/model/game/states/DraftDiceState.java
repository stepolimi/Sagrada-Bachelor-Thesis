package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Dice;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.DRAFT_DICE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.DRAFT_DICE_ACCEPTED;

public class DraftDiceState extends State {
    private static String state = DRAFT_DICE_STATE;

    /**
     * Drafts a dice from the dice space and sets it as pending.
     * @param round is the current round
     * @param action contains the current state and an index of the dice space
     */
    public void execute(Round round, List action) {
        try {
            int indexDiceSpace = Integer.parseInt((String) action.get(1));
            Dice dice = round.getBoard().getDiceSpace().getDice(indexDiceSpace, round.getCurrentPlayer().getNickname());
            round.notifyChanges(DRAFT_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            round.getBoard().getDiceSpace().removeDice(indexDiceSpace);
            round.setPendingDice(dice);
            round.setDraftedDice(true);
        } catch (RemoveDiceException e) {
            System.out.println(e.getMessage());
        }

        giveLegalActions(round);

    }

    @Override
    public String toString() {
        return state;
    }
}
