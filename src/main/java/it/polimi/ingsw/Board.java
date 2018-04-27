//board class represent the entire material used toplay like cards schema and the dicebag. it contains also players

package it.polimi.ingsw;

import java.util.List;

public class Board {
    private List<Player>  playerList;
    private Bag dicebag;
    //private DeckPublicObjective deckpubl;
    //private DeckPrivateObjective deckpriv;
    //private DeckToolCard decktool;
    private List<Schema> schemaList;

    public Board (List <Player> p){
        this.playerList = p;
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




}
