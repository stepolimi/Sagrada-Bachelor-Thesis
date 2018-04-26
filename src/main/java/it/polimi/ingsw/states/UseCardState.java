package it.polimi.ingsw.states;

import it.polimi.ingsw.Player;

public class UseCardState extends State {
    private static String next = "PickDiceState";

    @Override
    public void execute(Round round) {
        System.out.println(round.getCurrentPlayer().getNickname() + "use card");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        return next;
    }
}
