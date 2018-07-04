package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.PLACE_DICE_STATE;
import static it.polimi.ingsw.server.costants.LogConstants.STATE_EXECUTE;
import static it.polimi.ingsw.server.costants.MessageConstants.PLACE_DICE_ACCEPTED;

public class PlaceDiceState extends State {
    /**
     * Puts the pending dice in the specified row and column of the current player's schema.
     * @param round is the current round
     * @param message contains the current state and the indexes of row and column of the current player's schema
     */
    public void execute(Round round, Message message) {
        Schema schema = round.getCurrentPlayer().getSchema();
        int rowSchema = message.getIntegerArgument(0);
        int columnSchema = message.getIntegerArgument(1);
        try {
            schema.testInsertDice(rowSchema, columnSchema, round.getPendingDice(), round.getUsingTool());
            round.notifyChanges(PLACE_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            schema.insertDice(rowSchema, columnSchema, round.getPendingDice());
            round.setPendingDice(null);

        } catch (InsertDiceException e) {
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE,this.getClass().getName(),STATE_EXECUTE);
        }
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return PLACE_DICE_STATE;
    }
}
