package it.polimi.ingsw.states;

public class UseCardState extends State {
    private String next;

    @Override
    public void execute(Round round) {
        System.out.println(round.getCurrentPlayer().getNickname() + "use card");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        next = "PickDiceState";
        return next;
    }
}
