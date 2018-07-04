package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.internal.mesages.Message;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.costants.MessageConstants.END_TURN;
import static it.polimi.ingsw.server.costants.MessageConstants.INSERT_DICE;
import static it.polimi.ingsw.server.costants.MessageConstants.USE_TOOL_CARD;

public abstract class State {
    public abstract void execute(Round round, Message message);

    public String nextState(Message message){
        return message.getHead() + "State";
    }

    /**
     * Sets the turn's possible actions remaining.
     * @param round is the current round
     */
    void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<>();
        if(round.getUsingTool() == null || round.getNextActions().isEmpty()){
            round.setUsingTool(null);
            if(!round.isDraftedDice() || round.hasBonusInsertDice())
                legalActions.add(INSERT_DICE);
            if(!round.isUsedCard())
                legalActions.add(USE_TOOL_CARD);
            legalActions.add(END_TURN);
        } else{
            legalActions.addAll(round.getNextActions().get(0));
            if(legalActions.contains(INSERT_DICE) && round.isDraftedDice())
                legalActions.remove(INSERT_DICE);
        }
        round.setLegalActions(legalActions);
    }
}
