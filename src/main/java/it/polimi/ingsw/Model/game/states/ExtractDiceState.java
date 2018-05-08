package it.polimi.ingsw.Model.game.states;

import it.polimi.ingsw.Model.DiceSpace;

public class ExtractDiceState extends State {
    private String state = "ExtractDiceState";
    private String next;

    @Override
    public void setActions(Round round) {
        //user can extract and roll the dices

    }
//r
    @Override
    public void execute(Round round, String action){
        round.setDices(new DiceSpace());
    }

    @Override
    public String nextState(Round round, String action){ return action + "State"; }

    @Override
    public String toString (){return state; }
}
