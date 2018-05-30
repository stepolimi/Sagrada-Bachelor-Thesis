package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.InsertDiceException;

import java.util.List;

public class PlaceDiceState implements State {
    private static String state = "PlaceDiceState";

    public void execute(Round round, List action){
        if(action.get(1) == "DiceSpace")
            round.getBoard().getDiceSpace().insertDice(round.getPendingDice());
        else if(action.get(1) == "RoundTrack")
            round.getBoard().getRoundTrack().insertDice(round.getPendingDice(),(Integer)action.get(2));
        else if(action.get(1) == "DiceBag")
            round.getBoard().getDiceBag().insertDice(round.getPendingDice());
        else if(action.get(1) == "Schema") {
            try {
                round.getCurrentPlayer().getSchema().
                        insertDice((Integer) action.get(2), (Integer) action.get(3), round.getPendingDice(), round.getUsingTool());
            } catch (InsertDiceException e) {
                System.out.println("illegal dice insertion\n" + " ---");
            }
        }
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    @Override
    public String toString (){return state; }
}
