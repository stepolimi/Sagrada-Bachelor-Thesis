package it.polimi.ingsw.Model.game.states;

public class UseCardState extends State {
    private static String state = "UseCardState";
    private String next;

    @Override
    public void setActions(Round round) {
        //user can use an utility card

    }

    @Override
    public void execute(Round round, String action){

    }

    @Override
    public String nextState(Round round, String action){ return action + "State"; }

    @Override
    public String toString (){return state; }
}
