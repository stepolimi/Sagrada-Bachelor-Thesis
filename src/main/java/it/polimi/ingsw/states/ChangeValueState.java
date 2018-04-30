package it.polimi.ingsw.states;

public class ChangeValueState extends State {
    private String next;

    @Override
    public void setActions(Round round) {
        //can be changed the value of pending dice
    }

    @Override
    public void execute(Round round, String action){

    }

    @Override
    public String nextState(Round round, String action){
         next = "PlaceDiceState";
        return next;
    }
}
