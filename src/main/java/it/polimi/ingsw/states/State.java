package it.polimi.ingsw.states;

import it.polimi.ingsw.Player;

public abstract class State {
    private String next;

    public void execute(Round round){}

    public String nextState(Round round){
        return next;
    }

}
