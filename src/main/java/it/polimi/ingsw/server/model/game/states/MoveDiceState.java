package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.MOVE_DICE_ACCEPTED;
import static it.polimi.ingsw.costants.GameConstants.MOVE_DICE_ERROR;
import static it.polimi.ingsw.server.serverCostants.Constants.*;

public class MoveDiceState extends State {
    private static String state = MOVE_DICE_STATE;

    public void execute(Round round, List action) {
        Schema schema = round.getCurrentPlayer().getSchema();
        int oldRowSchema = Integer.parseInt((String) action.get(1));
        int oldColumnSchema = Integer.parseInt((String) action.get(2));
        int rowSchema = Integer.parseInt((String) action.get(3));
        int columnSchema = Integer.parseInt((String) action.get(4));
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
            schema.silentRemoveDice(oldRowSchema, oldColumnSchema);
            schema.testInsertDice(rowSchema, columnSchema, dice, round.getUsingTool());
            schema.silentInsertDice(oldRowSchema, oldColumnSchema, dice);
            round.notifyChanges(MOVE_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            round.getCurrentPlayer().getSchema().removeDice(oldRowSchema, oldColumnSchema);
            schema.insertDice(rowSchema, columnSchema, dice);
            System.out.println("dice: " + dice.toString() + " moved from: " + oldRowSchema + "," + oldColumnSchema + " to: " + rowSchema + "," + columnSchema + "\n ---");
        } catch (RemoveDiceException e) {
            System.out.println(e.getMessage());
        } catch (InsertDiceException e) {
            schema.silentInsertDice(oldRowSchema, oldColumnSchema, dice);
            System.out.println(e.getMessage());
        }
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }

}
