package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.Dice;

import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.DRAFT_DICE_STATE;
import static it.polimi.ingsw.server.costants.LogConstants.STATE_EXECUTE;
import static it.polimi.ingsw.server.costants.MessageConstants.DRAFT_DICE_ACCEPTED;

public class DraftDiceState extends State {
    /**
     * Drafts a dice from the dice space and sets it as pending.
     * @param round is the current round
     * @param message contains the current state and an index of the dice space
     */
    public void execute(Round round, Message message) {
        try {
            int indexDiceSpace = message.getIntegerArgument(0);
            Dice dice = round.getBoard().getDiceSpace().getDice(indexDiceSpace, round.getCurrentPlayer().getNickname());
            round.notifyChanges(DRAFT_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            round.getBoard().getDiceSpace().removeDice(indexDiceSpace);
            round.setPendingDice(dice);
            round.setDraftedDice(true);
        } catch (RemoveDiceException e) {
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE,this.getClass().getName(),STATE_EXECUTE);
        }

        giveLegalActions(round);

    }

    @Override
    public String toString() {
        return DRAFT_DICE_STATE;
    }
}
