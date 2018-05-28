package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.insertDiceAccepted;

public class InsertDiceState implements State {
    private static String state = "InsertDiceState";

    public void execute(Round round, List action){
        Schema schema = round.getCurrentPlayer().getSchema();
        try {
            Dice dice = round.getBoard().getDiceSpace().getDice((Integer) action.get(2));
            schema.testInsertDice((Integer) action.get(3), (Integer) action.get(4), dice, round.getUsingTool());
            round.notifyChanges(insertDiceAccepted);
            round.getBoard().getDiceSpace().removeDice((Integer) action.get(2));
            schema.insertDice((Integer) action.get(3), (Integer) action.get(4), dice, round.getUsingTool());
            round.setInsertedDice(true);
            System.out.println("dice inserted\n" + " ---");
        }catch(RemoveDiceException e){
            System.out.println("illegal dice removal\n" + " ---");
        }catch (InsertDiceException e) {
            System.out.println("illegal dice insertion\n" + " ---");
        }
        giveLegalActions(round);
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        if(!round.isUsedCard())
            legalActions.add("UseCard");
        if(!round.isInsertedDice())
            legalActions.add("InsertDice");
        legalActions.add("EndTurn");
        round.setLegalActions(legalActions);
    }

    @Override
    public String toString (){return state; }

}
