package it.polimi.ingsw.states;

import it.polimi.ingsw.Player;

public class ExtractDiceState extends State {
    private static String next = "InitialState";

    @Override
    public void execute(Round round) {
        System.out.println(round.getCurrentPlayer().getNickname() + "extract");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        return next;
    }
}
