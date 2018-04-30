package it.polimi.ingsw.states;

public class RollDiceState extends State {
    private static String next = "PlaceDiceState";

    @Override
    public void setActions(Round round) {
        //a dice can be rerolled
    }
    @Override
    public void execute(Round round, String action){

    }

    @Override
    public String nextState(Round round, String action){
        return next;
    }
}
