package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.exception.RemoveDiceException;

import java.util.List;

public class PickDiceState implements State {
    private static String state = "PickDiceState";

    public void execute(Round round, List action){
        if(action.get(1) == "DiceSpace") {
            try {
                round.setPendingDice(round.getBoard().getDiceSpace().removeDice((Integer) action.get(2)));
            } catch (RemoveDiceException e) {
                System.out.println("illegal dice removal\n" + " ---");
            }
        }else if(action.get(1) == "RoundTrack")
            round.setPendingDice(round.getBoard().getRoundTrack().removeDice((Integer)action.get(2),(Integer)action.get(3)));
        else if(action.get(1) == "DiceBag")
            round.setPendingDice(round.getBoard().getDicebag().takeDice());
        else if(action.get(1) == "Schema")
            round.setPendingDice(round.getCurrentPlayer().getSchema().removeDice((Integer)action.get(2),(Integer)action.get(3)));
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    @Override
    public String toString (){return state; }
}
