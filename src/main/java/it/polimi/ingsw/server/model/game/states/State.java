package it.polimi.ingsw.server.model.game.states;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.serverCostants.Constants.END_TURN;
import static it.polimi.ingsw.server.serverCostants.Constants.INSERT_DICE;
import static it.polimi.ingsw.server.serverCostants.Constants.USE_TOOL_CARD;

public abstract class State {
    public abstract void execute(Round round, List action);

    public String nextState(List action){
        return action.get(0) + "State";
    }

    void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        if(round.getUsingTool() == null || round.getNextActions().isEmpty()){
            round.setUsingTool(null);
            if(!round.isInsertedDice() || round.hasBonusInsertDice())
                legalActions.add(INSERT_DICE);
            if(!round.isUsedCard())
                legalActions.add(USE_TOOL_CARD);
            legalActions.add(END_TURN);
        } else{
            legalActions.addAll(round.getNextActions().get(0));
            if(legalActions.contains(INSERT_DICE) && round.isInsertedDice())
                legalActions.remove(INSERT_DICE);
        }
        round.setLegalActions(legalActions);
    }
}
