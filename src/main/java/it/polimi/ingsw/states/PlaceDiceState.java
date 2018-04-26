package it.polimi.ingsw.states;

import it.polimi.ingsw.Player;

public class PlaceDiceState extends State{
    private String next = "EndTurnState";

    @Override
    public void execute(Round round){
        System.out.println(round.getCurrentPlayer().getNickname() + "Place");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        return next;
    }
}
