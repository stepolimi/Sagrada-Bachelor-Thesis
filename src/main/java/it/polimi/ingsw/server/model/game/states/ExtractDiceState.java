package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Dice;

import java.util.List;

import static it.polimi.ingsw.server.serverCostants.Constants.*;

public class ExtractDiceState extends State {
    private static String state = EXTRACT_DICE_STATE;


    public void execute(Round round, List action) {
        Board board = round.getBoard();
        List<Dice> dices = board.getDiceBag().extract(board.numPlayers());
        board.setDiceSpace(dices);
        System.out.println("dices extracted\n" + " ---");

        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
