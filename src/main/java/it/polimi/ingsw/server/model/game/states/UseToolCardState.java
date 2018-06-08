package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.UseToolException;
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

            checkRestrictions(card,round);
            checkSpecialEffects(card,round);
            if(favor > 1) {
                if (card.isUsed()) {
                    round.getCurrentPlayer().decrementFavor(2);
                    round.setFavorsDecremented(2);
                    round.setCardWasUsed(true);
                } else {
                    round.getCurrentPlayer().decrementFavor(1);
                    round.setFavorsDecremented(1);
                    round.setCardWasUsed(false);
                    card.setUsed(true);
                }
                round.setUsingTool(card);
                round.setNextActions(card.getNextActions());
                round.setUsedCard(true);
                round.notifyChanges(USE_TOOL_CARD_ACCEPTED);
            } else if(favor == 1){
                if (card.isUsed()) {
                    round.notifyChanges(USE_TOOL_CARD_ERROR);
                    //not enough favor
                } else {
                    round.getCurrentPlayer().decrementFavor(1);
                    round.setFavorsDecremented(1);
                    round.setCardWasUsed(false);
                    card.setUsed(true);
                    round.setUsingTool(card);
                    round.setNextActions(card.getNextActions());
                    round.setUsedCard(true);
                    round.notifyChanges(USE_TOOL_CARD_ACCEPTED);
                }
            } else {
                round.notifyChanges(USE_TOOL_CARD_ERROR);
                //not enough favor
            }

        } catch (UseToolException e) {
            round.notifyChanges(USE_TOOL_CARD_ERROR);
        }
        giveLegalActions(round);
    }

    private void checkRestrictions(ToolCard card, Round round) throws UseToolException {
        if(card.getRestrictions().containsKey("action")) {
            if ((card.getRestrictions().get("action").equals("DraftDice") || card.getRestrictions().get("action").equals("InsertDice")) && round.isInsertedDice())
                throw new UseToolException();
        }
        if(card.getRestrictions().containsKey("roundTrack")) {
            if(card.getRestrictions().get("roundTrack").equals("notEmpty") && round.getBoard().getRoundTrack().isEmpty())
                throw new UseToolException();
        }
        if(card.getRestrictions().containsKey("schema")) {
            if(card.getRestrictions().get("schema").equals("notEmpty") && round.getCurrentPlayer().getSchema().isEmpty())
                throw new UseToolException();
        }
        if (card.getRestrictions().containsKey("turn")) {
            if (card.getRestrictions().get("turn").equals("first") && round.getTurnNumber() >= round.getBoard().numPlayers())
                throw new UseToolException();
            else if (card.getRestrictions().get("turn").equals("second") && round.getTurnNumber() < round.getBoard().numPlayers())
                throw new UseToolException();
        }
        if (card.getRestrictions().containsKey("beforeAction")) {
            if (card.getRestrictions().get("beforeAction").equals("InsertDice") && round.isInsertedDice())
                throw new UseToolException();
        }
    }
    private void checkSpecialEffects(ToolCard card,Round round){
        if(card.getSpecialEffects().contains("skipNextTurn")){
            round.incrementTurnNumber();
            round.getPlayersOrder().remove(round.getCurrentPlayer());
            round.getPlayersOrder().remove(round.getCurrentPlayer());
            round.getPlayersOrder().add(0,round.getCurrentPlayer());
        }

        if(card.getSpecialEffects().contains("bonusInsertDice")) {
            round.setBonusInsertDice(true);
        }
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        System.out.println(round.getNextActions());
        if(round.getUsingTool() == null || round.getNextActions().isEmpty()){
            round.setUsingTool(null);
            if(!round.isInsertedDice() || round.hasBonusInsertDice())
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
