package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.RoundTrack;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.SWAP_DICE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.SWAP_DICE_ACCEPTED;

public class SwapDiceState extends State {
    private static String state = SWAP_DICE_STATE;

    /**
     * Swaps the pending dice with the specified dice of the specified round of the round track.
     * @param round is the current round
     * @param action contains the current state, an index of a round track's round and an index of a dice of that round
     */
    public void execute(Round round, List action) {
        RoundTrack roundTrack = round.getBoard().getRoundTrack();
        int indexRound = Integer.parseInt((String) action.get(1));
        int indexDiceRound = Integer.parseInt((String) action.get(2));
        try {
            Dice dice = roundTrack.testRemoveDice(indexRound, indexDiceRound, round.getCurrentPlayer().getNickname());
            roundTrack.insertDice(round.getPendingDice(), indexRound);
            roundTrack.removeDice(indexRound, indexDiceRound);
            round.notifyChanges(SWAP_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            round.setPendingDice(dice);

        } catch (InsertDiceException e) {
            System.out.println(e.getMessage());
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
