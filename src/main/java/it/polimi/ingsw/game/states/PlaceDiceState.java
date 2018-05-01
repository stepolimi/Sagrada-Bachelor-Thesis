package it.polimi.ingsw.game.states;

public class PlaceDiceState extends State {
    private String state = "PlaceDiceState";
    private String next;

    @Override
    public void setActions(Round round){
        //user can place a dice
    }

    @Override
    public void execute(Round round, String action){

    }

    @Override
    public String nextState(Round round, String action){ return action + "State"; }

    @Override
    public String toString (){return state; }
}
