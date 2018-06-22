package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.exception.RemoveDiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static it.polimi.ingsw.costants.GameConstants.*;

public class DiceSpace extends Observable {
    private List<Dice> dices;

    public void setDices(List<Dice> dices) {
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

    public void insertDice(Dice d) {
        List action = new ArrayList();
        this.dices.add(d);
        action.add(PLACE_DICE_DICESPACE);
        action.add(d.getColour().toString());
        action.add(d.getValue());
        setChanged();
        notifyObservers(action);
    }

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

    public Dice removeDice(int index) throws RemoveDiceException {
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

    public void rollDices() {
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
