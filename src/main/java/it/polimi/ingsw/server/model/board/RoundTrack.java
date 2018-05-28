package it.polimi.ingsw.server.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class RoundTrack extends Observable{
    private List<Dice>[] listRounds;
    private static int totRounds = 10;
    public RoundTrack() {
        listRounds = new ArrayList [totRounds];
        for(int i = 0;i<totRounds;i++)
            listRounds[i]=new ArrayList<Dice>();
    }

    public List getListRounds(int i) {
        return this.listRounds[i];
    }

    public Dice getDice(int i, int j) { return this.listRounds[i].get(j);}

    public void insertDices(List<Dice> dices, int nRound) {
        List<String> action = new ArrayList<String>();
        this.listRounds[nRound].addAll(dices);
        action.add("addRoundTrack");
        action.add(((Integer)nRound).toString());
        for(Dice d: dices){
            action.add(d.getcolour().toString());
            action.add(((Integer)d.getValue()).toString());
        }
        setChanged();
        notifyObservers(action);
    }

    public void insertDice(Dice dice, int nRound) {
        List<String> action = new ArrayList<String>();
        this.listRounds[nRound].add(dice);
        action.add("addRoundTrack");
        action.add(((Integer)nRound).toString());
        action.add(dice.getcolour().toString());
        action.add(((Integer)dice.getValue()).toString());
        setChanged();
        notifyObservers(action);
    }

    public Dice removeDice(int nRound,int nDice) {
        List<String> action = new ArrayList<String>();
        Dice dice;
        dice = this.listRounds[nRound].get(nDice);
        this.listRounds[nRound].remove(nDice);
        action.add("removeRoundTrack");
        action.add(((Integer)nRound).toString());
        action.add(((Integer)nDice).toString());
        setChanged();
        notifyObservers(action);
        return dice;
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
