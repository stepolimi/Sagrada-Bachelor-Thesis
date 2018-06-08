package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.PLACE_DICE_ACCEPTED;

public class PlaceDiceState implements State {
    private static String state = "PlaceDiceState";

    public void execute(Round round, List action){
        Schema schema = round.getCurrentPlayer().getSchema();
        int rowSchema = Integer.parseInt((String) action.get(1));
        int columnSchema = Integer.parseInt((String) action.get(2));
        try {
            schema.testInsertDice(rowSchema, columnSchema, round.getPendingDice(), round.getUsingTool());
            round.notifyChanges(PLACE_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            schema.insertDice(rowSchema, columnSchema, round.getPendingDice(), round.getUsingTool());

        } catch (InsertDiceException e) {
            System.out.println("illegal dice insertion\n" + " ---");
        }
        giveLegalActions(round);
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        if(round.getUsingTool() == null || round.getNextActions().isEmpty()) {
            round.setUsingTool(null);
            if (!round.isInsertedDice() || round.hasBonusInsertDice())
                legalActions.add("InsertDice");
            if(!round.isUsedCard())
                legalActions.add("UseToolCard");
            legalActions.add("EndTurn");
        } else{
            legalActions.addAll(round.getNextActions().get(0));
            if(legalActions.contains("InsertDice") && round.isInsertedDice())
                legalActions.remove("InsertDice");
        }
        round.setLegalActions(legalActions);
    }

    @Override
    public String toString (){return state; }
}
