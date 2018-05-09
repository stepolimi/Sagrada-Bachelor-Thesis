package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.DiceBag;
import it.polimi.ingsw.Model.DiceSpace;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ServerController implements Observer{
    private DiceSpace ds;
    private DiceBag bag;

    public ServerController(DiceSpace ds,DiceBag bag)
    {
        this.ds = ds;
        this.bag = bag;
    }

    public DiceSpace getDiceSpace()
    {
        return this.ds;
    }


    public void update(Observable os, Object obj) {
        if(((ArrayList)obj).get(0).equals("RemoveDice"))
            ds.removeDice(((ArrayList<Integer>)obj).get(1));
        else if(((ArrayList)obj).get(0).equals("InsertDice"))
            ds.insertDice(bag.takeDice());

        ds.dump();
    }
}
