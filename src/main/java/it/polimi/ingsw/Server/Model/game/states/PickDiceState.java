package it.polimi.ingsw.Server.Model.game.states;

public class PickDiceState extends State {
    private static String state = "PickDiceState";
    private String next;

    @Override
    public void setActions(Round round){
        //user can pick a dice
    }

    @Override
    public void execute(Round round, String action){
        if(action == "PickDice")
            round.setPendingDice(round.getDices().removeDice(0));
    }

    @Override
    public String nextState(Round round, String action){ return action + "State"; }

    @Override
    public String toString (){return state; }
}
