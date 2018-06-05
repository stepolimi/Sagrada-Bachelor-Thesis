package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.RoundTrack;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.SWAP_DICE_ACCEPTED;

public class SwapDiceState implements State{
    private static String state = "SwapDiceState";

    public void execute(Round round, List action){
        RoundTrack roundTrack = round.getBoard().getRoundTrack();
        int indexRound = Integer.parseInt((String) action.get(1));
        int indexDiceRound = Integer.parseInt((String) action.get(2));
        try {
            Dice dice = roundTrack.testRemoveDice(indexRound, indexDiceRound,round.getCurrentPlayer().getNickname());
            roundTrack.insertDice(round.getPendingDice(),indexRound);
            round.notifyChanges(SWAP_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            roundTrack.removeDice(indexRound,indexDiceRound);
            round.setPendingDice(dice);

        } catch (InsertDiceException e) {
            System.out.println("illegal dice insertion\n" + " ---");
        } catch (RemoveDiceException e) {
            System.out.println("illegal dice removal\n" + " ---");
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
