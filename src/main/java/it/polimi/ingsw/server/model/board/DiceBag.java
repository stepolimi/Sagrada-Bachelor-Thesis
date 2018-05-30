//it's the set of dice given at the beginning of every game. there only the constructor with 18 dices for each colour

package it.polimi.ingsw.server.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static it.polimi.ingsw.costants.GameConstants.PLACE_DICE_BAG;
import static it.polimi.ingsw.server.serverCostants.Costants.TOT_DICES;

public class DiceBag extends Observable{
    private List<Dice> dices;

    public DiceBag() {
        dices = new ArrayList<Dice>();

        for (int i = 0; i < TOT_DICES; i++) {
            if (i < 18) {
                dices.add(new Dice(Colour.ANSI_GREEN, 0));
            } else if (i < 36) {
                dices.add(new Dice(Colour.ANSI_BLUE, 0));
            } else if (i < 54) {
                dices.add(new Dice(Colour.ANSI_PURPLE, 0));
            } else if (i < 72) {
                dices.add(new Dice(Colour.ANSI_RED, 0));
            } else {
                dices.add(new Dice(Colour.ANSI_YELLOW, 0));
            }

        }


    }

    public List<Dice> extract(int nPlayer) {
        // qua bisogna lanciare un'eccezione
        if (2 * nPlayer + 1 > dices.size())
            return null;

        List<Dice> extra = new ArrayList<Dice>();
        int nDice = 2 * nPlayer + 1;
        for (int i = 0; i < nDice; i++) {
            int casual = (int) (Math.random() * this.dices.size());
            dices.get(casual).rollDice();
            extra.add(dices.get(casual));
            dices.remove(casual);
        }

        return extra;
    }

    public void insertDice(Dice dice) {
        List<String> action = new ArrayList<String>();
        dice.setValue(0);
        this.dices.add(dice);

        action.add(PLACE_DICE_BAG);
        setChanged();
        notifyObservers(action);
    }

    public List <Dice> getDices()
    {
        return this.dices;
    }

    @Override
    public String toString() {
        String str="";
        str+= "dices in the dicebag:" + dices.size() + "\n";
        int g,y,b,p,r;
        g=0;
        y=0;
        b=0;
        p=0;
        r=0;
        for(int i=0;i<dices.size();i++)
            switch(dices.get(i).getColour())
            {
                case ANSI_RED: r++; break;
                case ANSI_BLUE: b++; break;
                case ANSI_GREEN: g++; break;
                case ANSI_PURPLE: p++; break;
                default: y++;
            }
        str+= "Red:"+r+"\nGreen:"+g+"\nYellow:"+y+"\nBlue:"+b+"\nPurple:"+p ;
         return str;
    }

    public Dice takeDice()
    {
        Dice d;
        int random = (int) (Math.random() * this.dices.size());
        d= dices.get(random);
        dices.remove(random);
        d.rollDice();
       return d;
    }
    public void dump()
    {
        System.out.println(this);
    }




}
