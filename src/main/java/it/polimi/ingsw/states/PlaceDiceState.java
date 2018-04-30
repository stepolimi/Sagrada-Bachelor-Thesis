package it.polimi.ingsw.states;

public class PlaceDiceState extends State{
    private String next;

    @Override
    public void setActions(Round round){
        //user can place a dice
    }

    @Override
    public void execute(Round round, String action){

    }

    @Override
    public String nextState(Round round, String action){
        next = "EndTurnState";
        return next;
    }
}
