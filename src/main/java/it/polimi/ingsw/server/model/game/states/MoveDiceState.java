package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.costants.GameConstants.INSERT_DICE_ACCEPTED;

public class MoveDiceState implements State{
    private static String state = "MoveDiceState";
    private int count = 0;

    public void execute(Round round, List action){
        Schema schema = round.getCurrentPlayer().getSchema();
        int oldRowSchema = Integer.parseInt((String)action.get(1));
        int oldColumnSchema = Integer.parseInt((String)action.get(1));
        int rowSchema = Integer.parseInt((String)action.get(2));
        int columnSchema = Integer.parseInt((String)action.get(3));
        try {
            Dice dice = round.getCurrentPlayer().getSchema().getDice(oldRowSchema,oldColumnSchema);
            schema.testInsertDice(rowSchema, columnSchema , dice, round.getUsingTool());
            round.notifyChanges(INSERT_DICE_ACCEPTED);
            round.getCurrentPlayer().getSchema().removeDice(oldRowSchema,oldColumnSchema);
            schema.insertDice(rowSchema, columnSchema, dice, round.getUsingTool());
            if(round.getUsingTool() == 4)
                count ++;
            System.out.println("dice: "+dice.toString()+" moved from: "+oldRowSchema+", "+oldColumnSchema+" to: "+rowSchema+","+columnSchema+"\n ---");
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
        if(round.getUsingTool() == 4 && count == 1){
            legalActions.add("MoveDice");
        }else {
            count = 0;
            if (!round.isInsertedDice())
                legalActions.add("InsertDice");
            legalActions.add("EndTurn");
        }
        round.setLegalActions(legalActions);
    }

    @Override
    public String toString (){return state; }

}
