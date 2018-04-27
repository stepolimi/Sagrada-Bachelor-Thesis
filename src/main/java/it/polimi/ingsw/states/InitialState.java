package it.polimi.ingsw.states;

public class InitialState extends State {
    private String next;

    @Override
    public void execute(Round round){
        System.out.println(round.getCurrentPlayer().getNickname() + "initial");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        next = "PickDiceState";
        return next;
    }
}
