package it.polimi.ingsw.states;

public class InitialState extends State {
    private String next;

    @Override
    public void setActions(Round round){
        //user can take a dice from the DiceSpace, use a toolCard or end the turn

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
