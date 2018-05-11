package it.polimi.ingsw.Server.Model.game.states;

public class ChangeValueState extends State {
    private static String state = "ChangeValueState";
    private String next;

    @Override
    public void setActions(Round round) {
        //can be changed the value of pending dice
    }

    @Override
    public void execute(Round round, String action){

    }

    @Override
    public String nextState(Round round, String action){ return action + "State"; }

    @Override
    public String toString (){return state; }
}
