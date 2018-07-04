package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.DiceBag;

import static it.polimi.ingsw.server.costants.Constants.SWAP_DICE_BAG_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.SWAP_DICE_BAG_ACCEPTED;

public class SwapDiceBagState extends State {
    /**
     * Puts the pending dice into the dice bag, then extracts a new one and sets it as the pending dice.
     * @param round is the current round
     * @param message contains the current state
     */
    public void execute(Round round, Message message) {
        DiceBag diceBag = round.getBoard().getDiceBag();
        diceBag.insertDice(round.getPendingDice());
        round.setPendingDice(diceBag.takeDice());
        round.getNextActions().remove(0);
        round.notifyChanges(SWAP_DICE_BAG_ACCEPTED);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return SWAP_DICE_BAG_STATE;
    }
}
