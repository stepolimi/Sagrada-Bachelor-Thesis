package it.polimi.ingsw.server.model.game.states;

import java.util.ArrayList;
import java.util.List;

public class RollDiceSpaceState implements State{
    private static String state = "RollDiceSpaceState";

    public void execute(Round round, List action){
        round.getBoard().getDiceSpace().rollDices();
        round.notifyChanges("rollDiceSpaceAccepted");
        round.getNextActions().remove(0);
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
