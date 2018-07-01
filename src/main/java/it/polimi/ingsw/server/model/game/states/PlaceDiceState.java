package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.PLACE_DICE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.PLACE_DICE_ACCEPTED;

public class PlaceDiceState extends State {
    private static String state = PLACE_DICE_STATE;

    /**
     * Puts the pending dice in the specified row and column of the current player's schema.
     * @param round is the current round
     * @param action contains the current state and the indexes of row and column of the current player's schema
     */
    public void execute(Round round, List action) {
        Schema schema = round.getCurrentPlayer().getSchema();
        int rowSchema = Integer.parseInt((String) action.get(1));
        int columnSchema = Integer.parseInt((String) action.get(2));
        try {
            schema.testInsertDice(rowSchema, columnSchema, round.getPendingDice(), round.getUsingTool());
            round.notifyChanges(PLACE_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            schema.insertDice(rowSchema, columnSchema, round.getPendingDice());

        } catch (InsertDiceException e) {
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE,this.getClass().getName(),"execute");
        }
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
