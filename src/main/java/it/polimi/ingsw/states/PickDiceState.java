package it.polimi.ingsw.states;

import it.polimi.ingsw.Player;

public class PickDiceState extends State {
    private String next = "PlaceDiceState";

    @Override
    public void execute(Round round){
        System.out.println(round.getCurrentPlayer().getNickname() + "Pick");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        return next;
    }
}
