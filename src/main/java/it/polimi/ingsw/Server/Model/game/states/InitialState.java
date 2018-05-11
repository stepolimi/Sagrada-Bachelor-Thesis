package it.polimi.ingsw.Server.Model.game.states;

public class InitialState extends State {
    private static String state = "InitialState";
    private String next;

    @Override
    public void setActions(Round round){
        //user can take a dice from the DiceSpace, use a toolCard or end the turn

    }

    @Override
    public void execute(Round round, String action){

    }

    @Override
    public String nextState(Round round, String action){ return action + "State"; }

    @Override
    public String toString (){return state; }
}
