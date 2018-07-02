package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.INSERT_DICE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.INSERT_DICE_ACCEPTED;

public class InsertDiceState extends State {
    private static String state = INSERT_DICE_STATE;

    /**
     * Drafts a dice from the dice space, the puts it in the current player's schema at the row and column specified in
     * the action list.
     * @param round is the current round
     * @param message contains the current state, an index of the dice space and the indexes of row and column of the
     */
    public void execute(Round round, Message message) {
        Schema schema = round.getCurrentPlayer().getSchema();
        int indexDiceSpace = message.getIntegerArgument(0);
        int rowDiceSchema = message.getIntegerArgument(1);
        int columnDiceSchema = message.getIntegerArgument(2);
        try {
            Dice dice = round.getBoard().getDiceSpace().getDice(indexDiceSpace, round.getCurrentPlayer().getNickname());
            schema.testInsertDice(rowDiceSchema, columnDiceSchema, dice, round.getUsingTool());
            round.notifyChanges(INSERT_DICE_ACCEPTED);
            if (!round.getNextActions().isEmpty())
                round.getNextActions().remove(0);
            round.getBoard().getDiceSpace().removeDice(indexDiceSpace);
            schema.insertDice(rowDiceSchema, columnDiceSchema, dice);
            if (!round.isDraftedDice())
                round.setDraftedDice(true);
            else
                round.setBonusInsertDice(false);
            Log.getLogger().addLog("dice inserted\n" + " ---" + dice.toString(), Level.INFO,this.getClass().getName(),"execute");
        } catch (RemoveDiceException e) {
            Log.getLogger().addLog(e.getMessage(),Level.SEVERE,this.getClass().getName(),"execute");
        } catch (InsertDiceException e) {
            Log.getLogger().addLog(e.getMessage(),Level.SEVERE,this.getClass().getName(),"execute");
        }
        giveLegalActions(round);

    }

    @Override
    public String toString() {
        return state;
    }

}
