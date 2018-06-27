package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.INSERT_DICE_STATE;
import static it.polimi.ingsw.server.costants.MessageConstants.INSERT_DICE_ACCEPTED;

public class InsertDiceState extends State {
    private static String state = INSERT_DICE_STATE;

    /**
     * Drafts a dice from the dice space, the puts it in the current player's schema at the row and column specified in
     * the action list.
     * @param round is the current round
     * @param action contains the current state, an index of the dice space and the indexes of row and column of the
     *               current player's schema
     */
    public void execute(Round round, List action) {
        Schema schema = round.getCurrentPlayer().getSchema();
        int indexDiceSpace = Integer.parseInt((String) action.get(1));
        int rowDiceSchema = Integer.parseInt((String) action.get(2));
        int columnDiceSchema = Integer.parseInt((String) action.get(3));
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
            System.out.println("dice inserted\n" + " ---" + dice.toString());
        } catch (RemoveDiceException e) {
            System.out.println(e.getMessage());
        } catch (InsertDiceException e) {
            System.out.println(e.getMessage());
        }
        giveLegalActions(round);

    }

    @Override
    public String toString() {
        return state;
    }

}
