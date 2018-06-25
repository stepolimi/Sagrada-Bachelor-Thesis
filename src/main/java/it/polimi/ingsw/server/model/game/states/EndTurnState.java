package it.polimi.ingsw.server.model.game.states;

import java.util.List;

import static it.polimi.ingsw.server.costants.Constants.*;

public class EndTurnState extends State {
    private static String state = END_TURN_STATE;

    public void execute(Round round, List action) {
        round.setInsertedDice(false);
        round.setUsedCard(false);
        round.setBonusInsertDice(false);
        round.setMovedDiceColour(null);
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
        System.out.println("turn ended\n" + " ---");
        giveLegalActions(round);
    }

    @Override
    public String toString() {
        return state;
    }
}
