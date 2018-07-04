package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.MOVE_DICE_STATE;
import static it.polimi.ingsw.server.costants.LogConstants.STATE_EXECUTE;
import static it.polimi.ingsw.server.costants.MessageConstants.MOVE_DICE_ACCEPTED;
import static it.polimi.ingsw.server.costants.MessageConstants.MOVE_DICE_ERROR;

public class MoveDiceState extends State {
    /**
     * Moves a dice from a position to another of the current player's schema.
     * If a tool card with special restrictions has been used, checks if those are respected.
     * @param round is the current round
     * @param message contains the current state, the indexes of row and column of the current player's schema from where a dice
     */
    public void execute(Round round, Message message) {
        Schema schema = round.getCurrentPlayer().getSchema();
        int oldRowSchema = message.getIntegerArgument(0);
        int oldColumnSchema = message.getIntegerArgument(1);
        int rowSchema = message.getIntegerArgument(2);
        int columnSchema = message.getIntegerArgument(3);
        Dice dice = null;
        try {
            dice = round.getCurrentPlayer().getSchema().testRemoveDice(oldRowSchema, oldColumnSchema);
            if (round.getUsingTool().getRestrictions().containsKey("colour") && round.getUsingTool().getRestrictions().get("colour").equals("same")) {
                if (!round.getBoard().getRoundTrack().containsColour(dice.getColour()) || (round.getMovedDiceColour() != null && round.getMovedDiceColour() != dice.getColour())) {
                    round.notifyChanges(MOVE_DICE_ERROR);
                    throw new RemoveDiceException();
                }
                round.setMovedDiceColour(dice.getColour());
            }
            if(oldRowSchema == rowSchema && oldColumnSchema == columnSchema)
                throw new RemoveDiceException();
            schema.silentRemoveDice(oldRowSchema, oldColumnSchema);
            schema.testInsertDice(rowSchema, columnSchema, dice, round.getUsingTool());
            schema.silentInsertDice(oldRowSchema, oldColumnSchema, dice);
            round.notifyChanges(MOVE_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            round.getCurrentPlayer().getSchema().removeDice(oldRowSchema, oldColumnSchema);
            schema.insertDice(rowSchema, columnSchema, dice);
        } catch (RemoveDiceException e) {
            Log.getLogger().addLog(e.getMessage(),Level.SEVERE,this.getClass().getName(),STATE_EXECUTE);
        } catch (InsertDiceException e) {
            schema.silentInsertDice(oldRowSchema, oldColumnSchema, dice);
            Log.getLogger().addLog(e.getMessage(),Level.SEVERE,this.getClass().getName(),STATE_EXECUTE);
        }
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return MOVE_DICE_STATE;
    }

}
