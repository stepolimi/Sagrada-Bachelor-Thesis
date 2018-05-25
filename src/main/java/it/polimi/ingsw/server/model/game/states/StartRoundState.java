package it.polimi.ingsw.server.model.game.states;

import java.util.ArrayList;
import java.util.List;

public class StartRoundState implements State {
    private static String state = "StartRoundState";
    private List<String> legalActions = new ArrayList<String>();

    public void execute(Round round, List action){

        //round.setLegalActions();
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    @Override
    public String toString (){return state; }
}
