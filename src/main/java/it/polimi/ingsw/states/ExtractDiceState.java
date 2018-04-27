package it.polimi.ingsw.states;

public class ExtractDiceState extends State {
    private String next;

    @Override
    public void execute(Round round) {

        System.out.println(round.getCurrentPlayer().getNickname() + "extract");
        round.changeState();
    }

    @Override
    public String nextState(Round round){
        next = "InitialState";
        return next;
    }
}
