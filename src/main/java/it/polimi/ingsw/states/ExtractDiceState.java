package it.polimi.ingsw.states;

import it.polimi.ingsw.DiceBag;
import it.polimi.ingsw.DiceSpace;

public class ExtractDiceState extends State {
    private static String next;

    @Override
    public void setActions(Round round) {
        //user can extract and roll the dices

    }

    @Override
    public void execute(Round round, String action){
        round.setDices(new DiceSpace(round.getBoard().getDicebag().extract(round.getBoard().numPlayers())));
    }

    @Override
    public String nextState(Round round, String action){
        next = "InitialState";
        return next;
    }
}
