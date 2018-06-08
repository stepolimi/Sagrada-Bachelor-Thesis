package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.ChangeDiceValueException;

import java.util.ArrayList;
import java.util.List;

public class ChooseValueState implements State {
    private static String state = "ChooseValueState";

    public void execute(Round round, List action){
        try {
            round.getPendingDice().setValue(Integer.parseInt((String)action.get(1)));
            round.getNextActions().remove(0);
            round.notifyChanges("chooseValueAccepted");
        }catch (ChangeDiceValueException changeDiceValueException) {
            System.out.println("impossible to set this value");
            round.notifyChanges("chooseValueError");
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
