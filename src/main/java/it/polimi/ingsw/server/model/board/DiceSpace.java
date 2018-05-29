package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.exception.RemoveDiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static it.polimi.ingsw.costants.GameConstants.*;

public class DiceSpace extends Observable {
    private  List<Dice> dices;

    public void setDices(List <Dice> dices) {
        List action = new ArrayList();
        this.dices = dices;
        action.add(setDiceSpace);
        for(Dice d: dices){
            action.add(d.getcolour().toString());
            action.add(((Integer)d.getValue()).toString());
        }
        setChanged();
        notifyObservers(action);
    }

    public List<Dice> getListDice(){ return this.dices; }

    public void insertDice(Dice d) {
        List action = new ArrayList();
        this.dices.add(d);
        action.add(placeDiceSpace);
        action.add(d.getcolour().toString());
        action.add(((Integer)d.getValue()).toString());
        setChanged();
        notifyObservers(action);
    }

    public Dice getDice(int index) throws RemoveDiceException{
        List action = new ArrayList();
        if(index < dices.size() && index >= 0) {
            return dices.get(index);
        }
        action.add(pickDiceSpaceError);
        action.add("diceSpace");
        setChanged();
        notifyObservers(action);
        throw new RemoveDiceException();
    }

    public Dice removeDice(int index) throws RemoveDiceException {
        List action = new ArrayList();
        if(index < dices.size() && index >= 0)                                      //can be useless
        {
            Dice d = dices.get(index);
            dices.remove(index);
            action.add(pickDiceSpace);
            action.add(((Integer)index).toString());
            setChanged();
            notifyObservers(action);
            return d;
        }
        throw new RemoveDiceException();
    }

    @Override
    public String toString() {
        String str="DiceSpace:\n";
        for(int i=0;i<dices.size();i++)
        str += dices.get(i).toString();
        return str;
    }

    public void dump()
    {
        System.out.println(this);
    }

}
