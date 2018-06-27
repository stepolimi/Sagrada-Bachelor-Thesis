package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.exception.RemoveDiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static it.polimi.ingsw.server.costants.MessageConstants.*;

public class DiceSpace extends Observable {
    private List<Dice> dices;

    /**
     * Sets the dices to the dice space and notify it to the observer.
     * @param dices is a list of dice to be set as the dices of the dice space.
     */
    public synchronized void setDices(List<Dice> dices) {
        List action = new ArrayList();
        this.dices = dices;
        action.add(SET_DICE_SPACE);
        dices.forEach(d -> {
            action.add(d.getColour().toString());
            action.add(d.getValue());
        });
        setChanged();
        notifyObservers(action);
    }

    public List<Dice> getListDice() {
        return this.dices;
    }

    /**
     * Adds the dice to the dices of the dice space and notify it to the observer.
     * @param d is the dice to be added to the dices.
     */
    public synchronized void insertDice(Dice d) {
        List action = new ArrayList();
        this.dices.add(d);
        action.add(PLACE_DICE_DICESPACE);
        action.add(d.getColour().toString());
        action.add(d.getValue());
        setChanged();
        notifyObservers(action);
    }

    /**
     * Returns the dice correspondent to the index without removing it. If the index is not valid, notify it to the player
     * and throws an exception.
     * @param index is the index of the dice that a player is trying to take.
     * @param player is the player that is trying to take a dice.
     * @return the dice correspondent to the index.
     * @throws RemoveDiceException if the index of the dice is not valid.
     */
    public Dice getDice(int index, String player) throws RemoveDiceException {
        List<String> action = new ArrayList<>();
        if (index < dices.size() && index >= 0) {
            return dices.get(index);
        }
        action.add(PICK_DICE_SPACE_ERROR);
        action.add(player);
        setChanged();
        notifyObservers(action);
        throw new RemoveDiceException();
    }

    /**
     * Removes and returns the dice correspondent to the index and notify it to all players.
     * If the index is not valid, throws an exception.
     * @param index is the index of the dice that a player is trying to take.
     * @return the dice correspondent to the index.
     * @throws RemoveDiceException if the index of the dice is not valid.
     */
    public synchronized Dice removeDice(int index) throws RemoveDiceException {
        List action = new ArrayList();
        if (index < dices.size() && index >= 0)
        {
            Dice d = dices.get(index);
            dices.remove(index);
            action.add(PICK_DICE_SPACE);
            action.add(index);
            setChanged();
            notifyObservers(action);
            return d;
        }
        throw new RemoveDiceException();
    }

    /**
     * Sets randomly the value of every dice and notify them to all players.
     */
    public synchronized void rollDices() {
        List action = new ArrayList();
        dices.forEach(Dice::rollDice);
        action.add(SET_DICE_SPACE);
        dices.forEach(d ->{
            action.add(d.getColour().toString());
            action.add(d.getValue());
        });
        setChanged();
        notifyObservers(action);
    }

    /**
     * Sends to the player the colour and the value of every dice.
     * @param player is the player that is  going to reconnect.
     */
    public synchronized void reconnectPlayer(Player player){
        List action = new ArrayList();
        action.add(SET_DICE_SPACE_ON_RECONNECT);
        action.add(player.getNickname());
        dices.forEach(d ->{
            action.add(d.getColour().toString());
            action.add(d.getValue());
        });
        setChanged();
        notifyObservers(action);
    }

    @Override
    public String toString() {
        String str = "DiceSpace:\n";
        for (Dice dice: dices)
            str += dice.toString();
        return str;
    }

    public void dump() {
        System.out.println(this);
    }

}
