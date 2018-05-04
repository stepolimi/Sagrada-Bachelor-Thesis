//board class represent the entire material used toplay like cards schema and the dicebag. it contains also players

package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Cards.Decks.*;
import java.util.List;

public class Board {
    private List<Player>  playerList;
    private DiceBag dicebag;
    private DeckPublicObjective deckpubl;
    private DeckPrivateObjective deckpriv;
    // private DeckToolCard decktool;
    private setSchemi deckSchemi;
    private List<Schema> schemaList;

    public Board (List <Player> p){

        this.playerList = p;
        dicebag = new DiceBag();
        deckSchemi = new setSchemi(this.playerList.size());

    }


    public Player getPlayer(int index){
        return this.playerList.get(index);
    }

    public int getIndex(Player player){
        return this.playerList.indexOf(player);
    }

    public int numPlayers(){
        return playerList.size();
    }

    public DiceBag getDicebag() { return dicebag;  }

}