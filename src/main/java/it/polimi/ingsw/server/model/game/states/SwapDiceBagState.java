package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.model.board.DiceBag;

import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.SWAP_DICE_BAG_ACCEPTED;
import static it.polimi.ingsw.server.serverCostants.Constants.*;

public class SwapDiceBagState extends State {
    private static String state = SWAP_DICE_BAG_STATE;

    public void execute(Round round, List action) {
        DiceBag diceBag = round.getBoard().getDiceBag();
        diceBag.insertDice(round.getPendingDice());
        round.setPendingDice(diceBag.takeDice());
        round.getNextActions().remove(0);
        round.notifyChanges(SWAP_DICE_BAG_ACCEPTED);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
