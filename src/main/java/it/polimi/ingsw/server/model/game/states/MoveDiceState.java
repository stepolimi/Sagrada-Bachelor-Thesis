package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.MOVE_DICE_ACCEPTED;

public class MoveDiceState implements State{
    private static String state = "MoveDiceState";

    public void execute(Round round, List action){
        Schema schema = round.getCurrentPlayer().getSchema();
        int oldRowSchema = Integer.parseInt((String)action.get(1));
        int oldColumnSchema = Integer.parseInt((String)action.get(2));
        int rowSchema = Integer.parseInt((String)action.get(3));
        int columnSchema = Integer.parseInt((String)action.get(4));
        Dice dice = null;
        try {
            dice = round.getCurrentPlayer().getSchema().testRemoveDice(oldRowSchema,oldColumnSchema);
            round.getCurrentPlayer().getSchema().removeDice(oldRowSchema,oldColumnSchema);
            schema.testInsertDice(rowSchema, columnSchema , dice, round.getUsingTool());
            round.notifyChanges(MOVE_DICE_ACCEPTED);
            round.getNextActions().remove(0);
            schema.insertDice(rowSchema, columnSchema, dice, round.getUsingTool());
            System.out.println("dice: "+dice.toString()+" moved from: "+oldRowSchema+","+oldColumnSchema+" to: "+rowSchema+","+columnSchema+"\n ---");
        }catch(RemoveDiceException e){
            System.out.println("illegal dice removal\n" + " ---");
        }catch (InsertDiceException e) {
            round.getCurrentPlayer().getSchema().insertDice(oldRowSchema,oldColumnSchema,dice);
            System.out.println("illegal dice insertion\n" + " ---");
        }
        giveLegalActions(round);
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        System.out.println(round.getNextActions());
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
