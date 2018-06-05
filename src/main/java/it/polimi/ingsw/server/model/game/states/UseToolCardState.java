package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.NotFoundToolException;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.USE_TOOL_CARD_ACCEPTED;
import static it.polimi.ingsw.costants.GameConstants.USE_TOOL_CARD_ERROR;

public class UseToolCardState implements State{
    private static String state = "UseToolCardState";

    public void execute(Round round, List action){
        ToolCard card;
        try {
            card = round.getBoard().getToolCard(Integer.parseInt((String) action.get(1)));
            int favor = round.getCurrentPlayer().getFavour();
            if(favor > 1) {
                if (card.isUsed())
                    round.getCurrentPlayer().decrementFavor(2);
                else
                    round.getCurrentPlayer().decrementFavor(1);
                round.setUsingTool(card);
                round.setNextActions(card.getNextActions());
                round.setUsedCard(true);
                round.notifyChanges(USE_TOOL_CARD_ACCEPTED);
            } else if(favor == 1){
                if (card.isUsed()) {
                    round.notifyChanges(USE_TOOL_CARD_ERROR);
                    //not enough flavor
                } else {
                    round.getCurrentPlayer().decrementFavor(1);
                    round.setUsingTool(card);
                    round.setNextActions(card.getNextActions());
                    round.setUsedCard(true);
                    round.notifyChanges(USE_TOOL_CARD_ACCEPTED);
                }
            } else {
                round.notifyChanges(USE_TOOL_CARD_ERROR);
                //not enough favor
            }

        } catch (NotFoundToolException e) {
            round.notifyChanges(USE_TOOL_CARD_ERROR);
        }
        giveLegalActions(round);
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        System.out.println(round.getNextActions());
        if(round.getUsingTool() == null || round.getNextActions().isEmpty()){
            round.setUsingTool(null);
            if(!round.isInsertedDice())
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
