//board class represent the entire material used toplay like cards schema and the dicebag. it contains also players

package it.polimi.ingsw.Server.Model.board;

import it.polimi.ingsw.Server.Model.cards.Decks.DeckPrivateObjective;
import it.polimi.ingsw.Server.Model.cards.Decks.DeckPublicObjective;
import it.polimi.ingsw.Server.Model.cards.Decks.DeckToolsCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Board extends Observable{
    private List<Player>  playerList;
    private DiceBag dicebag;
    private RoundTrack roundTrack;
    private DiceSpace diceSpace;
    private DeckPublicObjective deckpubl;
    private DeckPrivateObjective deckpriv;
    private DeckToolsCard decktool;
    private SetSchemas deckSchemas;
    private List<Schema> schemaList;
    private Observer obs;

    public Board (List <Player> p){
        this.playerList = p;
        dicebag = new DiceBag();
        deckSchemas = new SetSchemas(this.playerList.size());
        roundTrack = new RoundTrack();
        diceSpace = new DiceSpace();
        schemaList = new ArrayList<Schema>();
        deckpriv = new DeckPrivateObjective();
        deckpubl = new DeckPublicObjective();
        decktool = new DeckToolsCard();

    }
    //vanno estratti e creati obbiettivi publici, privati, e toolcard
    public void setObserver(Observer obs) {
        this.obs = obs;
        roundTrack.addObserver(obs);
        diceSpace.addObserver(obs);
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public DiceSpace getDiceSpace() {
        return diceSpace;
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

    public SetSchemas getDeckSchemas() { return deckSchemas; }

    public void addSchema(Schema schema){ this.schemaList.add(schema); }

    public DeckPrivateObjective getDeckpriv() { return deckpriv; }

    public DeckToolsCard getDecktool() { return decktool; }

    public DeckPublicObjective getDeckpubl() { return deckpubl; }
}
