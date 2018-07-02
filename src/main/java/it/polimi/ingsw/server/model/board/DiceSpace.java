package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.internalMesages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static it.polimi.ingsw.server.costants.MessageConstants.*;

public class DiceSpace extends Observable {
    private List<Dice> dices;
    private Board board;

    public DiceSpace(Board board){
        this.board = board;
    }
    /**
     * Sets the dices to the dice space and notify it to the observer.
     * @param dices is a list of dice to be set as the dices of the dice space.
     */
    public synchronized void setDices(List<Dice> dices) {
        Message message = new Message(SET_DICE_SPACE);
        this.dices = dices;

        dices.forEach(d -> {
            message.addStringArguments(d.getColour().toString());
            message.addIntegerArgument(d.getValue());
        });
        message.setPlayers(board.getNicknames());
        setChanged();
        notifyObservers(message);
    }

    public List<Dice> getListDice() {
        return this.dices;
    }

    /**
     * Adds the dice to the dices of the dice space and notify it to the observer.
     * @param d is the dice to be added to the dices.
     */
    public synchronized void insertDice(Dice d) {
        this.dices.add(d);

        Message message = new Message(PLACE_DICE_DICESPACE);
        message.addStringArguments(d.getColour().toString());
        message.addIntegerArgument(d.getValue());
        message.setPlayers(board.getNicknames());
        setChanged();
        notifyObservers(message);
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
        if (index < dices.size() && index >= 0) {
            return dices.get(index);
        }

        Message message = new Message(PICK_DICE_SPACE_ERROR);
        message.addPlayer(player);
        setChanged();
        notifyObservers(message);
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
        if (index < dices.size() && index >= 0) {
            Dice d = dices.get(index);
            dices.remove(index);

            Message message = new Message(PICK_DICE_SPACE);
            message.addIntegerArgument(index);
            message.setPlayers(board.getNicknames());
            setChanged();
            notifyObservers(message);
            return d;
        }
        throw new RemoveDiceException();
    }

    /**
     * Sets randomly the value of every dice and notify them to all players.
     */
    public synchronized void rollDices() {
        dices.forEach(Dice::rollDice);

        Message message = new Message(SET_DICE_SPACE);
        dices.forEach(d ->{
            message.addStringArguments(d.getColour().toString());
            message.addIntegerArgument(d.getValue());
        });
        message.setPlayers(board.getNicknames());
        setChanged();
        notifyObservers(message);
    }

    /**
     * Sends to the player the colour and the value of every dice.
     * @param player is the player that is  going to reconnect.
     */
    public synchronized void reconnectPlayer(Player player){
        Message message = new Message(SET_DICE_SPACE_ON_RECONNECT);
        dices.forEach(d ->{
            message.addStringArguments(d.getColour().toString());
            message.addIntegerArgument(d.getValue());
        });
        message.addPlayer(player.getNickname());
        setChanged();
        notifyObservers(message);
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
