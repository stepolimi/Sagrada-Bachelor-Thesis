package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DiceSpace extends Observable {
   private  List<Dice> dices;

   public List<Dice> getListDice(){
       return this.dices;
   }

    public DiceSpace()
    {
        this.dices = new ArrayList<Dice>();
    }

   public void insertDice(Dice d)
   {
       this.dices.add(d);
        setChanged();
        notifyObservers(dices);
   }


   public Dice removeDice(int n) // indice umano, non binario bisogna adattare tutto
   {
       if(n<(dices.size()+1) && n>0)
       {
           Dice d = dices.get(n - 1);
           dices.remove(n - 1);
           setChanged();
           notifyObservers(dices);
           return d;
       }
       return null;
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
