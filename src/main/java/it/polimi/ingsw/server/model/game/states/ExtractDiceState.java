package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Dice;

import java.util.List;

public class ExtractDiceState implements State {
    private static String state = "ExtractDiceState";

    public void execute(Round round, List action){
        Board board = round.getBoard();
        List<Dice> dices = board.getDicebag().extract(board.numPlayers());
        board.setDiceSpace(dices);
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    @Override
    public String toString (){return state; }
}
