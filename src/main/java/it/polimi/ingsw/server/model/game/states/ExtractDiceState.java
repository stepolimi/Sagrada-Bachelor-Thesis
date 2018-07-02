package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.internalMesages.Message;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Dice;

import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.*;

public class ExtractDiceState extends State {
    private static String state = EXTRACT_DICE_STATE;

    /**
     * Extracts the dices for the current round and put them into the dice space.
     * @param round is the current round
     * @param message contains the current state
     */
    public void execute(Round round, Message message) {
        Board board = round.getBoard();
        List<Dice> dices = board.getDiceBag().extract(board.numPlayers());
        board.setDiceSpace(dices);
        Log.getLogger().addLog("dices extracted\n" + " ---", Level.INFO,this.getClass().getName(),"execute");

        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
