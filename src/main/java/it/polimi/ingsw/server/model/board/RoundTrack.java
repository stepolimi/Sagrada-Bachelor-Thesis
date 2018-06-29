package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static it.polimi.ingsw.server.costants.Constants.TOT_ROUNDS;
import static it.polimi.ingsw.server.costants.MessageConstants.*;

public class RoundTrack extends Observable {
    private List<Dice>[] listRounds;

    public RoundTrack() {
        listRounds = new ArrayList[TOT_ROUNDS];
        for (int i = 0; i < TOT_ROUNDS; i++)
            listRounds[i] = new ArrayList<>();
    }

    /**
     * Returns the round correspondent to the specified index.
     * @param i is the index of a round.
     * @return the round correspondent to the index.
     */
    public List getListRounds(int i) {
        return this.listRounds[i];
    }

    /**
     * Returns the dice correspondent to the specified index in the specified round.
     * @param i is the index of a round.
     * @param j is the index of a dice in a round of the round track.
     * @return the dice correspondent to the indexes.
     */
    public Dice getDice(int i, int j) {
        return this.listRounds[i].get(j);
    }

    /**
     * Adds the dices at the specified round of the round track and notifies it to the players.
     * @param dices is a list of dice to be added to the round track.
     * @param nRound is the index of the round where the dices will be added.
     */
    public void insertDices(List<Dice> dices, int nRound) {
        List action = new ArrayList<>();
        this.listRounds[nRound].addAll(dices);
        action.add(PLACE_DICE_ROUND_TRACK);
        action.add(nRound);
        dices.forEach(d -> {
            action.add(d.getColour().toString());
            action.add(d.getValue());
        });
        setChanged();
        notifyObservers(action);
    }

    /**
     * Adds the dice at the specified round of the round track and notifies it to the players.
     * @param dice is a dice to be added to the round track.
     * @param nRound is the index of the round where the dice will be added.
     * @throws InsertDiceException if the index of the round is not valid.
     */
    public void insertDice(Dice dice, int nRound) throws InsertDiceException {
        List action = new ArrayList<>();
        if (nRound < TOT_ROUNDS) {
            this.listRounds[nRound].add(dice);
            action.add(PLACE_DICE_ROUND_TRACK);
            action.add(nRound);
            action.add(dice.getColour().toString());
            action.add(dice.getValue());
            setChanged();
            notifyObservers(action);
            return;
        }
        throw new InsertDiceException();
    }

    /**
     * Tests if a dice can be removed from the round track. If it is possible, returns it, otherwise throws an exception.
     * @param nRound is the index of the round where the dice will be removed.
     * @param nDice is the index of a dice in a round of the round track.
     * @param player is the player that is trying to take the dice.
     * @return the dice specified if is possible to remove it.
     * @throws RemoveDiceException if it is not possible to remove the specified dice.
     */
    public Dice testRemoveDice(int nRound, int nDice, String player) throws RemoveDiceException {
        List<String> action = new ArrayList<>();
        Dice dice;
        if (nRound < TOT_ROUNDS) {
            if (listRounds[nRound].size() > nDice) {
                if (listRounds[nRound].get(nDice) != null) {
                    dice = listRounds[nRound].get(nDice);
                    return dice;
                }
            }
        }
        action.add(PICK_DICE_ROUND_TRACK_ERROR);
        action.add(player);
        setChanged();
        notifyObservers(action);
        throw new RemoveDiceException();
    }

    /**
     * Tries to remove a dice from the round track. If it is possible, removes and returns it, otherwise throws an exception.
     * @param nRound is the index of the round where the dice will be removed.
     * @param nDice is the index of a dice in a round of the round track.
     * @return the dice specified if is possible to remove it.
     * @throws RemoveDiceException if it's not possible to remove the specified dice.
     */
    public Dice removeDice(int nRound, int nDice) throws RemoveDiceException {
        List action = new ArrayList<>();
        Dice dice;
        if (listRounds[nRound].get(nDice) != null) {
            dice = listRounds[nRound].get(nDice);
            listRounds[nRound].remove(nDice);
            action.add(PICK_DICE_ROUND_TRACK);
            action.add(nRound);
            action.add(nDice);
            setChanged();
            notifyObservers(action);
            return dice;
        }
        action.add(PICK_DICE_ROUND_TRACK_ERROR);
        setChanged();
        notifyObservers(action);
        throw new RemoveDiceException();
    }

    /**
     * Checks if the round track contains a dice with the specified colour and then returns true or false.
     * @param colour is a colour of a dice.
     * @return true if the round track contains a dice with this colour, false otherwise.
     */
    public boolean containsColour(Colour colour) {
        for (List<Dice> round : listRounds)
            for (Dice dice : round)
                if (dice.getColour() == colour)
                    return true;
        return false;
    }

    public boolean isEmpty() {
        return listRounds[0].isEmpty();
    }

    /**
     * Sends to the player the colours and the values of all of the dices in the round track.
     * @param player is the player that is going to reconnect to the game.
     */
    public void reconnectPlayer(Player player){
        List action = new ArrayList<>();
        for(int i=0; i<TOT_ROUNDS; i++){
            if(!listRounds[i].isEmpty()) {
                action.clear();
                action.add(PLACE_DICE_ROUND_TRACK_ON_RECONNECT);
                action.add(player.getNickname());
                action.add(i);
                listRounds[i].forEach(d -> {
                    action.add(d.getColour().toString());
                    action.add(d.getValue());
                });
                setChanged();
                notifyObservers(action);
            }
            else
                break;
        }
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < TOT_ROUNDS; i++) {
            str += "Round " + (i + 1) + "\n";
            if (!this.listRounds[i].isEmpty())
                str += this.listRounds[i].toString() + "\n";
            else
                str += "[empty]\n";
        }
        return str;
    }

    public void dump() {
        System.out.println(this);
    }
}
