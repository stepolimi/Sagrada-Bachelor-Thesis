package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class RoundTrack {
    private List<Dice>[] listRounds;
    private static int totRounds = 10;
    public RoundTrack()
    {
        listRounds = new List [totRounds];
        for(int i = 0;i<totRounds;i++)
            listRounds[i]=new ArrayList<Dice>();
    }

    // forse conviene ricevere in ingresso una lista (da vedere)
    public void insertDice(List <Dice> d, int nRound)
    {
        this.listRounds[nRound] = d;
    }
    public Dice removeDice(int nRound,int nDice)
    {
        Dice d;
        d = this.listRounds[nRound].get(nDice);
        this.listRounds[nRound].remove(nDice);
        return d;
    }

    public List getListRounds(int i) {
        return this.listRounds[i];
    }

    @Override
    public String toString() {
        String str = "";
        for(int i=0;i<totRounds;i++)
        {
            str+="Round "+(i+1)+"\n";
            if(this.listRounds[i].isEmpty()==false)
            str+= this.listRounds[i].toString()+"\n";
            else
            str+="[empty]\n";
        }
        return str;
    }
    public void dump()
    {
        System.out.println(this);
    }
}
