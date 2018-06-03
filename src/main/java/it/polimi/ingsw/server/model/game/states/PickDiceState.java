package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Dice;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.PICK_DICE_ACCEPTED;

public class PickDiceState implements State {
    private static String state = "PickDiceState";

    public void execute(Round round, List action){
        //todo castare a integer come nel primo blocco
        try {
            int indexDiceSpace =Integer.parseInt((String)action.get(1));
            Dice dice = round.getBoard().getDiceSpace().getDice(indexDiceSpace,round.getCurrentPlayer().getNickname());
            round.notifyChanges(PICK_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            round.getBoard().getDiceSpace().removeDice(indexDiceSpace);
            round.setPendingDice(dice);
            round.setInsertedDice(true);
        } catch (RemoveDiceException e) {
            System.out.println("illegal dice removal\n" + " ---");
        }

        giveLegalActions(round);

    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        if(round.getUsingTool() == 0 || round.getNextActions().isEmpty()) {
            round.setUsingTool(0);
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
