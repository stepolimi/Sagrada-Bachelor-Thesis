package it.polimi.ingsw.server.model.game.states;

import java.util.List;

public class RollDiceState implements State{
    private static String state = "RollDiceState";

    public void execute(Round round, List action){

    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    @Override
    public String toString (){return state; }
}
