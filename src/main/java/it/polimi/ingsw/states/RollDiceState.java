package it.polimi.ingsw.states;

import it.polimi.ingsw.Player;

public class RollDiceState extends State {
    private static String next = "PlaceDiceState";

    @Override
    public void execute(Round round) {
        System.out.println(round.getCurrentPlayer().getNickname() + "roll");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        return next;
    }
}
