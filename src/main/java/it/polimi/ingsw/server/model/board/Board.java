//board class contains all materials used to play, like cards, schemas and the dicebag. it also contains players

package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.exception.UseToolException;
import it.polimi.ingsw.server.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.ingsw.server.model.cards.PrivateObjective;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import static it.polimi.ingsw.server.costants.MessageConstants.*;

public class Board extends Observable {
    private List<Player> playerList;
    private DiceBag dicebag;
    private RoundTrack roundTrack;
    private DiceSpace diceSpace;
    private List<Schema> deckSchemas;
    private List<Schema> deckDefaultSchemas;
    private List<Schema> deckCustomSchemas;
    private List<ObjectiveCard> deckPublic;
    private List<PrivateObjective> deckPrivate;
    private List<ToolCard> deckTool;
    private Observer obs;

    public Board(List<Player> p) {
        this.playerList = p;
        dicebag = new DiceBag();
        roundTrack = new RoundTrack();
        deckSchemas = new ArrayList<>();
        deckDefaultSchemas = new ArrayList<>();
        deckCustomSchemas = new ArrayList<>();
        deckPrivate = new ArrayList<>();
        deckPublic = new ArrayList<>();
        deckTool = new ArrayList<>();

    }

    public void setObserver(Observer obs) {
        this.obs = obs;
        roundTrack.addObserver(obs);
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

    public Player getPlayer(int index) {
        return this.playerList.get(index);
    }

    public Player getPlayer(String name) {
        for (Player p : playerList)
            if (p.getNickname().equals(name))
                return p;
        return null;
    }

    public int getIndex(Player player) {
        return this.playerList.indexOf(player);
    }

    public int numPlayers() {
        return playerList.size();
    }

    public DiceBag getDiceBag() {
        return dicebag;
    }

    public void setDiceSpace(List<Dice> dices) {
        diceSpace = new DiceSpace();
        diceSpace.addObserver(obs);
        diceSpace.setDices(dices);
    }

    public List<Schema> getDeckSchemas() {
        return deckSchemas;
    }

    public List<PrivateObjective> getDeckpriv() {
        return deckPrivate;
    }

    public List<ToolCard> getDeckTool() {
        return deckTool;
    }

    public ToolCard getToolCard(int number) throws UseToolException {
        for (ToolCard card : deckTool) {
            if (card.getNumber() == number)
                return card;
        }
        throw new UseToolException();
    }

    public List<ObjectiveCard> getDeckPublic() {
        return deckPublic;
    }

    public void addDefaultSchema(Schema schema) {
        this.deckSchemas.add(schema);
        this.deckDefaultSchemas.add(schema);
        if (deckSchemas.size() == playerList.size()) {
            notifyChanges(SET_OPPONENTS_SCHEMAS);
            notifyChanges(SET_OPPONENTS_CUSTOM_SCHEMAS);
        }
    }

    public void addCustomSchema(Schema schema) {
        this.deckSchemas.add(schema);
        this.deckCustomSchemas.add(schema);
        if (deckSchemas.size() == playerList.size()) {
            notifyChanges(SET_OPPONENTS_SCHEMAS);
            notifyChanges(SET_OPPONENTS_CUSTOM_SCHEMAS);
        }
    }

    public void setDeckPublic(List<ObjectiveCard> deck) {
        this.deckPublic = deck;
        notifyChanges(SET_PUBLIC_OBJECTIVES);
    }

    public void addPrivate(PrivateObjective privateObjective) {
        this.deckPrivate.add(privateObjective);
    }

    public void setDeckTool(List<ToolCard> deckTool) {
        this.deckTool = deckTool;
        notifyChanges(SET_TOOL_CARDS);
    }

    public int getConnected() {
        int count = 0;
        for (Player p : playerList) {
            if (p.isConnected())
                count++;
        }
        return count;
    }

    public void notifyChanges(String string) {
        List action = new ArrayList();

        switch (string) {
            case SET_PUBLIC_OBJECTIVES:
                action = deckPublic.stream()
                        .map(ObjectiveCard::getName)
                        .collect(Collectors.toList());
                break;
            case SET_TOOL_CARDS:
                action = deckTool.stream()
                        .map(ToolCard::getNumber)
                        .collect(Collectors.toList());
                break;
            case SET_OPPONENTS_SCHEMAS:
                for (Player p : playerList)
                    if (deckDefaultSchemas.contains(p.getSchema())) {
                        action.add(p.getNickname());
                        action.add(p.getSchema().getName());
                    }
                break;
            case SET_OPPONENTS_CUSTOM_SCHEMAS:
                for (Player p : playerList) {
                    if (deckCustomSchemas.contains(p.getSchema())) {
                        action.add(p.getNickname());
                        action.add(p.getSchema().getJson());
                    }
                }
                break;
            default:
                break;
        }
        action.add(0,string);
        setChanged();
        notifyObservers(action);
    }
}
