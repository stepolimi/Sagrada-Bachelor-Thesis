package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Dice;

import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.*;
import static it.polimi.ingsw.server.costants.LogConstants.DICES_EXTRACTED;
import static it.polimi.ingsw.server.costants.LogConstants.STATE_EXECUTE;

public class ExtractDiceState extends State {
    /**
     * Extracts the dices for the current round and put them into the dice space.
     * @param round is the current round
     * @param message contains the current state
     */
    public void execute(Round round, Message message) {
        Board board = round.getBoard();
        List<Dice> dices = board.getDiceBag().extract(board.numPlayers());
        board.setDiceSpace(dices);
        Log.getLogger().addLog(DICES_EXTRACTED, Level.INFO,this.getClass().getName(),STATE_EXECUTE);

        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return EXTRACT_DICE_STATE;
    }
}
