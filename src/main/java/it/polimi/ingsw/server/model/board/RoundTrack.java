package it.polimi.ingsw.server.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class RoundTrack extends Observable{
    private List<Dice>[] listRounds;
    private static int totRounds = 10;
    public RoundTrack()
    {
        listRounds = new List [totRounds];
        for(int i = 0;i<totRounds;i++)
            listRounds[i]=new ArrayList<Dice>();
    }

    public List getListRounds(int i) {
        return this.listRounds[i];
    }

    public Dice getDice(int i, int j) { return this.listRounds[i].get(j);}

    public void insertDices(List<Dice> d, int nRound)
    {
        this.listRounds[nRound].addAll(d);
    }

    public void insertDice(Dice d, int nRound)
    {
        this.listRounds[nRound].add(d);
    }

    public Dice removeDice(int nRound,int nDice)
    {
        Dice d;
        d = this.listRounds[nRound].get(nDice);
        this.listRounds[nRound].remove(nDice);
        return d;
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
