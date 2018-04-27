package it.polimi.ingsw.states;

public class PickDiceState extends State {
    private String next;

    @Override
    public void execute(Round round){
        System.out.println(round.getCurrentPlayer().getNickname() + "Pick");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        next = "PlaceDiceState";
        return next;
    }
}
