package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.PLACE_DICE_ACCEPTED;
import static it.polimi.ingsw.server.serverCostants.Constants.*;

public class PlaceDiceState extends State {
    private static String state = PLACE_DICE_STATE;

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
            System.out.println(e.getMessage());
        }
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
