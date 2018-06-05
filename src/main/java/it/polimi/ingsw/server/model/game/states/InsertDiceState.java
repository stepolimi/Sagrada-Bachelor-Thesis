package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.ILLEGAL_ACTION;
import static it.polimi.ingsw.costants.GameConstants.INSERT_DICE_ACCEPTED;

public class InsertDiceState implements State {
    private static String state = "InsertDiceState";

    public void execute(Round round, List  action){
     //   if(!round.isInsertedDice()) {
            Schema schema = round.getCurrentPlayer().getSchema();
            int indexDiceSpace = Integer.parseInt((String) action.get(1));
            int rowDiceSchema = Integer.parseInt((String) action.get(2));
            int columnDiceSchema = Integer.parseInt((String) action.get(3));
            try {
                Dice dice = round.getBoard().getDiceSpace().getDice(indexDiceSpace,round.getCurrentPlayer().getNickname());
                schema.testInsertDice(rowDiceSchema, columnDiceSchema, dice, round.getUsingTool());
                round.notifyChanges(INSERT_DICE_ACCEPTED);
                if(!round.getNextActions().isEmpty())
                    round.getNextActions().remove(0);
                round.getBoard().getDiceSpace().removeDice(indexDiceSpace);
                schema.insertDice(rowDiceSchema, columnDiceSchema, dice, round.getUsingTool());
                round.setInsertedDice(true);
                System.out.println("dice inserted\n" + " ---" + dice.toString());
            } catch (RemoveDiceException e) {
                System.out.println("illegal dice removal\n" + " ---");
            } catch (InsertDiceException e) {
                System.out.println("illegal dice insertion\n" + " ---");
            }
            giveLegalActions(round);
      //  } else
        //    round.notifyChanges(ILLEGAL_ACTION);

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
