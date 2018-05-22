//board class contains all materials used to play, like cards, schemas and the dicebag. it also contains players

package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.cards.objCards.ObjectiveCard;
import it.polimi.ingsw.server.model.cards.PrivateObjective;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.costants.GameCreationMessages.setPublicObjectives;
import static it.polimi.ingsw.costants.GameCreationMessages.setToolCards;

public class Board extends Observable{
    private List<Player>  playerList;
    private DiceBag dicebag;
    private RoundTrack roundTrack;
    private DiceSpace diceSpace;
    private List<Schema> deckSchemas;
    private List<ObjectiveCard> deckPubl;
    private List<PrivateObjective> deckPriv;
    private List<ToolCard> deckTool;
    private Observer obs;

    public Board (List <Player> p){
        this.playerList = p;
        dicebag = new DiceBag();
        roundTrack = new RoundTrack();
        deckSchemas = new ArrayList<Schema>();
        deckPriv = new ArrayList<PrivateObjective>();
        deckPubl = new ArrayList<ObjectiveCard>();
        deckTool = new ArrayList<ToolCard>();

    }

    public void setObserver(Observer obs) {
        this.obs = obs;
        roundTrack.addObserver(obs);
        dicebag.addObserver(obs);
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

    public Player getPlayer(String name){
        for(Player p: playerList)
            if(p.getNickname().equals(name))
                return p;
        return null;
    }

    public int getIndex(Player player){
        return this.playerList.indexOf(player);
    }

    public int numPlayers(){
        return playerList.size();
    }

    public DiceBag getDicebag() { return dicebag;  }

    public void setDiceSpace(List<Dice> dices){
        diceSpace = new DiceSpace(dices);
        diceSpace.addObserver(obs);
    }

    public List<Schema> getDeckSchemas() { return deckSchemas; }

    public List<PrivateObjective> getDeckpriv() { return deckPriv; }

    public List<ToolCard> getDecktool() { return deckTool; }

    public List<ObjectiveCard> getDeckpubl() { return deckPubl; }

    public void addSchema(Schema schema){ this.deckSchemas.add(schema); }

    public void setDeckpubl(List<ObjectiveCard> deck){
        this.deckPubl = deck;
        forwardAction(setPublicObjectives);
    }

    public void addPriv(PrivateObjective Priv) { this.deckPriv.add(Priv); }

    public void setDeckTool(List<ToolCard> deckTool) {
        this.deckTool = deckTool;
        forwardAction(setToolCards);
    }

    public int getConnected(){
        int count=0;
        for(Player p: playerList){
            if(p.isConnected())
                count ++;
        }
        return count;
    }

    public void forwardAction(String string){
        List action = new ArrayList();
        action.add(string);
        if(string.equals(setPublicObjectives))
            for(ObjectiveCard o: deckPubl)
                action.add(o.getName());                    //to be changed (maybe)
        else if (string.equals(setToolCards))
            for(ToolCard tool: deckTool)
                action.add(tool.getNum().toString());
        setChanged();
        notifyObservers(action);
    }
}
