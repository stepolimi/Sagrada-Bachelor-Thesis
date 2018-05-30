package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Dice;

import java.util.ArrayList;
import java.util.List;

public class ExtractDiceState implements State {
    private static String state = "ExtractDiceState";


    public void execute(Round round, List action){
        Board board = round.getBoard();
        List<Dice> dices = board.getDiceBag().extract(board.numPlayers());
        board.setDiceSpace(dices);
        System.out.println("dices extracted\n" + " ---");

        giveLegalActions(round);
    }

    public String nextState(Round round, List action){ return action.get(0) + "State"; }

    private void giveLegalActions(Round round){
        List<String> legalActions = new ArrayList<String>();
        legalActions.add("InsertDice");
        legalActions.add("UseCard");
        legalActions.add("EndTurn");
        round.setLegalActions(legalActions);
    }

    @Override
    public String toString (){return state; }
}
