package it.polimi.ingsw.states;

import it.polimi.ingsw.Player;

public class InitialState extends State {
    private String next = "PickDiceState";

    @Override
    public void execute(Round round){
        System.out.println(round.getCurrentPlayer().getNickname() + "initial");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        return next;
    }
}
