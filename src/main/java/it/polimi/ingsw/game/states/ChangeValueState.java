package it.polimi.ingsw.game.states;

public class ChangeValueState extends State {
    private String state = "ChangeValueState";
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
