package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.NotFoundToolException;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;

import java.util.ArrayList;
import java.util.List;

public class UseCardState implements State{
    private static String state = "UseCardState";
    private String next;

    public void execute(Round round, List action){
        ToolCard card = null;
        try {
            card = round.getBoard().getToolCard((Integer)action.get(1));
            int favor = round.getCurrentPlayer().getFavour();
            if(favor > 1) {
                if (card.isUsed())
                    round.getCurrentPlayer().decrementFavor(2);
                else
                    round.getCurrentPlayer().decrementFavor(1);
                round.setUsingTool((Integer)action.get(1));
                round.setUsedCard(true);
            } else if(favor == 1){
                if (card.isUsed()) {
                    //not enough flavor
                } else {
                    round.getCurrentPlayer().decrementFavor(1);
                    round.setUsingTool((Integer)action.get(1));
                    round.setUsedCard(true);
                }
            } else {
                //not enough favor
            }

        } catch (NotFoundToolException e) {
            e.printStackTrace();
        }
        giveLegalActions(round,card);
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round, ToolCard card){
        List<String> legalActions = new ArrayList<String>();
        if(card == null){
            if(!round.isUsedCard())
                legalActions.add("InsertDice");
            if(!round.isInsertedDice())
                legalActions.add("UseCard");
            legalActions.add("EndTurn");
        } else{
            //legalActions.add(card.getNextStates());
        }
        round.setLegalActions(legalActions);
    }

    @Override
    public String toString (){return state; }
}
