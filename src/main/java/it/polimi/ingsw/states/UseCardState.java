package it.polimi.ingsw.states;

public class UseCardState extends State {
    private String next;

    @Override
    public void setActions(Round round) {
        //user can use an utility card

    }

    @Override
    public void execute(Round round, String action){

    }

    @Override
    public String nextState(Round round, String action){
        next = "PickDiceState";
        return next;
    }
}
