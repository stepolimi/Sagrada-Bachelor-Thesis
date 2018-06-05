package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.ChangeDiceValueException;

import java.util.ArrayList;
import java.util.List;

public class ChangeValueState implements State {
    private static String state = "ChangeValueState";

    public void execute(Round round, List action){
        try {
            if (action.get(1).equals("Increment")) {
                round.getPendingDice().incrementValue();
            }else if(action.get(1).equals("Decrement"))
                round.getPendingDice().decrementValue();
            round.getNextActions().remove(0);
            round.notifyChanges("ChangeValueAccepted");
        }catch (ChangeDiceValueException changeDiceValueException) {
            System.out.println("impossible");
            round.notifyChanges("ChangeValueError");
        }
        giveLegalActions(round);
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        if(round.getUsingTool() == null || round.getNextActions().isEmpty()) {
            round.setUsingTool(null);
            if (!round.isInsertedDice())
                legalActions.add("InsertDice");
            if(!round.isUsedCard())
                legalActions.add("UseToolCard");
            legalActions.add("EndTurn");
        } else{
            legalActions.addAll(round.getNextActions().get(0));
        }
        round.setLegalActions(legalActions);
    }

    @Override
    public String toString (){return state; }
}
