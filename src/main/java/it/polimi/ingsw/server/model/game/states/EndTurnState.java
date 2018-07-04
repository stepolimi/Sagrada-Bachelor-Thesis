package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.internal.mesages.Message;

import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.*;
import static it.polimi.ingsw.server.costants.LogConstants.STATE_EXECUTE;
import static it.polimi.ingsw.server.costants.LogConstants.TURN_ENDED;

public class EndTurnState extends State {
    /**
     * Ends the current turn, resets all of the round's values of the old turn at the default's value and calculate the
     * next turn's player.
     * @param round is the current round
     * @param message contains the current state
     */
    public void execute(Round round, Message message) {
        round.setDraftedDice(false);
        round.setUsedCard(false);
        round.setBonusInsertDice(false);
        round.setMovedDiceColour(null);
        if (!round.getNextActions().isEmpty())
            round.getNextActions().remove(0);
        if(round.getPendingDice() != null) {
            round.getBoard().getDiceSpace().insertDice(round.getPendingDice());
            round.setPendingDice(null);
        }
        do {
            if (round.getTurnNumber() == round.getBoard().getPlayerList().size() * 2 - 1) {
                round.getTimer().cancel();
                if (round.getRoundManager().getRoundNumber() < 10) {
                    round.getBoard().getRoundTrack().insertDices(round.getBoard().getDiceSpace().getListDice(), round.getRoundManager().getRoundNumber() - 1);
                    round.getRoundManager().startNewRound();
                }
                return;
            }
            round.getPlayersOrder().remove(0);
            round.setCurrentPlayer(round.getPlayersOrder().get(0));
            round.incrementTurnNumber();
        } while (!round.getCurrentPlayer().isConnected());
        Log.getLogger().addLog(TURN_ENDED, Level.INFO,this.getClass().getName(),STATE_EXECUTE);
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return END_TURN_STATE;
    }
}
