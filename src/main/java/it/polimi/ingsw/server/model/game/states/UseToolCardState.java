package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.exception.UseToolException;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.*;
import static it.polimi.ingsw.server.costants.MessageConstants.*;

public class UseToolCardState extends State {
    private static String state = USE_TOOL_CARD_STATE;

    /**
     * Checks the restrictions of the specified tool card and if the player have enough favors to use it.
     * If those are ok, sets the player's new favors, the next actions of the tool card and the tool card that has been used.
     * @param round is the current round
     * @param action contains the current state and the number of the tool card that will be used.
     */
    public void execute(Round round, List action) {
        try {
            ToolCard card = round.getBoard().getToolCard(Integer.parseInt((String) action.get(1)));
            int favor = round.getCurrentPlayer().getFavour();

            checkRestrictions(card, round);

            if (favor > 1) {
                if (card.isUsed()) {
                    round.getCurrentPlayer().decrementFavor(2);
                    round.setFavorsDecremented(2);
                    round.setCardWasUsed(true);
                } else {
                    round.getCurrentPlayer().decrementFavor(1);
                    round.setFavorsDecremented(1);
                    round.setCardWasUsed(false);
                    card.setUsed(true);
                }
                round.setUsingTool(card);
                round.setNextActions(card.getNextActions());
                round.setUsedCard(true);
                round.notifyChanges(USE_TOOL_CARD_ACCEPTED);
            } else if (favor == 1) {
                if (card.isUsed())
                    round.notifyChanges(USE_TOOL_CARD_ERROR);
                else {
                    round.getCurrentPlayer().decrementFavor(1);
                    round.setFavorsDecremented(1);
                    round.setCardWasUsed(false);
                    card.setUsed(true);
                    round.setUsingTool(card);
                    round.setNextActions(card.getNextActions());
                    round.setUsedCard(true);
                    round.notifyChanges(USE_TOOL_CARD_ACCEPTED);
                }
            } else {
                throw new UseToolException();
            }

            checkSpecialEffects(card, round);

        } catch (UseToolException e) {
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE,this.getClass().getName(),"execute");
            round.notifyChanges(USE_TOOL_CARD_ERROR);
        }

        giveLegalActions(round);
    }

    /**
     * Checks if the tool card has special restrictions and eventually if they are respected.
     * @param card is the tool card that has been used
     * @param round is the current round
     * @throws UseToolException is thrown when a restriction has not been respected
     */
    private void checkRestrictions(ToolCard card, Round round) throws UseToolException {
        Map<String, String> restrictions = card.getRestrictions();

        if (restrictions.containsKey(ACTION_RESTRICTION))
            checkActionRestriction(restrictions.get(ACTION_RESTRICTION), round);

        if (restrictions.containsKey(ROUND_TRACK_RESTRICTION))
            checkRoundTrackRestriction(restrictions.get(ROUND_TRACK_RESTRICTION), round);

        if (restrictions.containsKey(SCHEMA_RESTRICTION))
            checkSchemaRestriction(restrictions.get(SCHEMA_RESTRICTION), round);

        if (restrictions.containsKey(TURN_RESTRICTION))
            checkTurnRestriction(restrictions.get(TURN_RESTRICTION), round);

        if (restrictions.containsKey(BEFORE_ACTION_RESTRICTION))
            checkBeforeActionRestriction(restrictions.get(BEFORE_ACTION_RESTRICTION), round);

    }


    /**
     * Checks if the specified restriction has been respected.
     * @param restriction is the actions' restriction to be checked
     * @param round is the current round
     * @throws UseToolException is thrown if the restriction has not been respected
     */
    private void checkActionRestriction(String restriction, Round round) throws UseToolException {
        if ((restriction.equals(DRAFT_DICE) || restriction.equals(INSERT_DICE)) && round.isDraftedDice())
            throw new UseToolException();
    }

    /**
     * Checks if the specified restriction has been respected.
     * @param restriction is the round track's restriction to be checked
     * @param round is the current round
     * @throws UseToolException is thrown if the restriction has not been respected
     */
    private void checkRoundTrackRestriction(String restriction, Round round) throws UseToolException {
        if (restriction.equals(NOT_EMPTY) && round.getBoard().getRoundTrack().isEmpty())
            throw new UseToolException();
    }

    /**
     * Checks if the specified restriction has been respected.
     * @param restriction is the schema's restriction to be checked
     * @param round is the current round
     * @throws UseToolException is thrown if the restriction has not been respected
     */
    private void checkSchemaRestriction(String restriction, Round round) throws UseToolException {
        if (restriction.equals(NOT_EMPTY) && round.getCurrentPlayer().getSchema().isEmpty())
            throw new UseToolException();
    }

    /**
     * Checks if the specified restriction has been respected.
     * @param restriction is the turn's restriction to be checked
     * @param round is the current round
     * @throws UseToolException is thrown if the restriction has not been respected
     */
    private void checkTurnRestriction(String restriction, Round round) throws UseToolException {
        if (restriction.equals(FIRST) && round.getTurnNumber() >= round.getBoard().numPlayers())
            throw new UseToolException();
        else if (restriction.equals(SECOND) && round.getTurnNumber() < round.getBoard().numPlayers())
            throw new UseToolException();
    }

    /**
     * Checks if the specified restriction has been respected.
     * @param restriction is the "before action" 's restriction to be checked
     * @param round is the current round
     * @throws UseToolException is thrown if the restriction has not been respected
     */
    private void checkBeforeActionRestriction(String restriction, Round round) throws UseToolException {
        if (restriction.equals(INSERT_DICE) && round.isDraftedDice())
            throw new UseToolException();
    }

    /**
     * Checks if the tool card has special effects.
     * @param card is the tool card that has been used
     * @param round is the current round
     */
    private void checkSpecialEffects(ToolCard card, Round round) {
        if (card.getSpecialEffects().contains(SKIP_NEXT_TURN)) {
            round.incrementTurnNumber();
            round.getPlayersOrder().remove(round.getCurrentPlayer());
            round.getPlayersOrder().remove(round.getCurrentPlayer());
            round.getPlayersOrder().add(0, round.getCurrentPlayer());
        }

        if (card.getSpecialEffects().contains(BONUS_INSERT_DICE)) {
            round.setBonusInsertDice(true);
        }
    }

    @Override
    public String toString() {
        return state;
    }
}
