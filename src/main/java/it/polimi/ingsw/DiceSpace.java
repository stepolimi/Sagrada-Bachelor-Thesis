package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class DiceSpace {
   private  List<Dice> dices;

    public DiceSpace(List<Dice> dices)
    {
        this.dices = dices;
    }

   public void insertDice(Dice d)
   {
       this.dices.add(d);
   }
   public Dice removeDice(int n) // indice umano, non binario bisogna adattare tuto
   {
       if(n<(dices.size()+1) && n>0)
       {
           Dice d = dices.get(n - 1);
           dices.remove(n - 1);
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
