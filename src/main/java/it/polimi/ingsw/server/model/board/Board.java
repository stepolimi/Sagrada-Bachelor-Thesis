package it.polimi.ingsw.server.model.board;

import com.google.gson.Gson;
import it.polimi.ingsw.server.exception.UseToolException;
import it.polimi.ingsw.server.internal.mesages.Message;
import it.polimi.ingsw.server.model.cards.objective.cards.ObjectiveCard;
import it.polimi.ingsw.server.model.cards.PrivateObjective;
import it.polimi.ingsw.server.model.cards.tool.cards.ToolCard;
import it.polimi.ingsw.server.virtual.view.VirtualView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

import static it.polimi.ingsw.server.costants.Constants.EVERYONE;
import static it.polimi.ingsw.server.costants.MessageConstants.*;
import static it.polimi.ingsw.server.model.parser.SchemaParser.parseSchema;

public class Board extends Observable {
    private final List<Player> playerList;
    private final DiceBag dicebag;
    private final RoundTrack roundTrack;
    private DiceSpace diceSpace;
    private final List<Schema> deckSchemas;
    private final List<Schema> deckDefaultSchemas;
    private final List<Schema> deckCustomSchemas;
    private List<ObjectiveCard> deckPublic;
    private final List<PrivateObjective> deckPrivate;
    private List<ToolCard> deckTool;

    public Board(List<Player> p) {
        this.playerList = p;
        dicebag = new DiceBag();
        roundTrack = new RoundTrack(this);
        roundTrack.addObserver(VirtualView.getVirtualView());
        deckSchemas = new ArrayList<>();
        deckDefaultSchemas = new ArrayList<>();
        deckCustomSchemas = new ArrayList<>();
        deckPrivate = new ArrayList<>();
        deckPublic = new ArrayList<>();
        deckTool = new ArrayList<>();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Returns a list with the nickname of each player.
     * @return a list with the nickname of each player.
     */
    public List<String> getNicknames(){
        List<String> nicknames = new ArrayList<>();
        playerList.forEach(player -> nicknames.add(player.getNickname()));
        return nicknames;
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

    /**
     * Searches and returns the instance of the player with the specified name. If no player has been found, returns null.
     * @param name is the name of a player
     * @return the instance of the player with the specified name or null if no player has been found
     */
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

    /**
     * Creates a new dice space, sets the list of dice and the observer to it.
     * @param dices is a list of dice to be set to the dice space.
     */
    public void setDiceSpace(List<Dice> dices) {
        diceSpace = new DiceSpace(this);
        diceSpace.addObserver(VirtualView.getVirtualView());
        diceSpace.setDices(dices);
    }

    public List<Schema> getDeckSchemas() {
        return deckSchemas;
    }

    public List<PrivateObjective> getDeckPrivate() {
        return deckPrivate;
    }

    public List<ToolCard> getDeckTool() {
        return deckTool;
    }

    /**
     * Searches and returns the instance of the specified tool card. If no tool card has been fund, throws an exception.
     * @param number number of a tool card.
     * @return the instance of the tool card with the specified number.
     * @throws UseToolException if the tool card wasn't found.
     */
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

    /**
     * Adds the schema to the list of schemas. If all the players have chosen a schema, sends them to players.
     * @param schema is the schema that was chosen by one player.
     */
    public void addDefaultSchema(Schema schema) {
        schema.setPlayers(getNicknames());
        this.deckSchemas.add(schema);
        this.deckDefaultSchemas.add(schema);
        if (deckSchemas.size() == playerList.size()) {
            notifyChanges(SET_OPPONENTS_SCHEMAS,EVERYONE);
            notifyChanges(SET_OPPONENTS_CUSTOM_SCHEMAS,EVERYONE);
        }
    }

    /**
     * Adds the custom schema to the list of schemas. If all the players have chosen a schema, sends them to players.
     * @param schema custom schema that was chosen by one player.
     */
    public void addCustomSchema(Schema schema) {
        schema.setPlayers(getNicknames());
        this.deckSchemas.add(schema);
        this.deckCustomSchemas.add(schema);
        if (deckSchemas.size() == playerList.size()) {
            notifyChanges(SET_OPPONENTS_SCHEMAS,EVERYONE);
            notifyChanges(SET_OPPONENTS_CUSTOM_SCHEMAS,EVERYONE);
        }
    }

    /**
     * Sets the public objectives that has been extracted.
     * @param deck list of the public objectives that has been extracted for the game.
     */
    public void setDeckPublic(List<ObjectiveCard> deck) {
        this.deckPublic = deck;
        notifyChanges(SET_PUBLIC_OBJECTIVES,EVERYONE);
    }

    public void addPrivate(PrivateObjective privateObjective) {
        this.deckPrivate.add(privateObjective);
    }

    /**
     * Sets the tool cards that has been extracted.
     * @param deckTool list of the tool cards that has been extracted for the game.
     * */
    public void setDeckTool(List<ToolCard> deckTool) {
        this.deckTool = deckTool;
        notifyChanges(SET_TOOL_CARDS,EVERYONE);
    }

    /**
     * Counts and returns the number of players connected to the game at the moment.
     * @return the number of players connected to the game.
     */
    public int getConnected() {
        int count = 0;
        for (Player p : playerList) {
            if (p.isConnected())
                count++;
        }
        return count;
    }

    /**
     * Sends the public objectives, the tool cards and the schemas of the players to the player that is going to reconnect.
     * @param player is the player that is going to reconnect to the game.
     */
    public void reconnectPlayer(Player player){
        notifyChanges(SET_PUBLIC_OBJECTIVES_ON_RECONNECT,player.getNickname());
        notifyChanges(SET_TOOL_CARDS_ON_RECONNECT,player.getNickname());
        notifyChanges(SET_SCHEMAS_ON_RECONNECT,player.getNickname());
    }

    /**
     * Notifies different changes to the observer.
     * @param string head of the message that will be sent to the observer.
     * @param player name of the player to whom the message will be sent. Can be sent to all of them.
     */
    private void notifyChanges(String string, String player) {
        Message message = new Message(string);
        List<String> publicObjectives;
        List<Integer> toolCards;

        switch (string) {
            case SET_PUBLIC_OBJECTIVES:
                publicObjectives = deckPublic.stream()
                        .map(ObjectiveCard::getName)
                        .collect(Collectors.toList());
                message.setStringArguments(publicObjectives);
                message.setPlayers(getNicknames());
                break;
            case SET_TOOL_CARDS:
                toolCards = deckTool.stream()
                        .map(ToolCard::getNumber)
                        .collect(Collectors.toList());
                message.setIntegerArguments(toolCards);
                message.setPlayers(getNicknames());
                break;
            case SET_OPPONENTS_SCHEMAS:
                for (Player p : playerList) {
                    if (deckDefaultSchemas.contains(p.getSchema())) {
                        message.addStringArguments(p.getNickname());
                        message.addStringArguments(p.getSchema().getName());
                    }
                }
                message.setPlayers(getNicknames());
                break;
            case SET_OPPONENTS_CUSTOM_SCHEMAS:
                for (Player p : playerList) {
                    if (deckCustomSchemas.contains(p.getSchema())) {
                        message.addStringArguments(p.getNickname());
                        message.addStringArguments(p.getSchema().getJson());
                    }
                }
                message.setPlayers(getNicknames());
                break;
            case SET_SCHEMAS_ON_RECONNECT:
                Gson gson = new Gson();
                for (Player p : playerList) {
                    message.addStringArguments(p.getNickname());
                    if(p.getSchema()!= null)
                        message.addStringArguments(gson.toJson(parseSchema(p.getSchema())));
                    else
                        message.addStringArguments("");
                }
                message.addPlayer(player);
                break;
            case SET_PUBLIC_OBJECTIVES_ON_RECONNECT:
                publicObjectives = deckPublic.stream()
                        .map(ObjectiveCard::getName)
                        .collect(Collectors.toList());
                message.setStringArguments(publicObjectives);
                message.addPlayer(player);
                break;
            case SET_TOOL_CARDS_ON_RECONNECT:
                toolCards = deckTool.stream()
                        .map(ToolCard::getNumber)
                        .collect(Collectors.toList());
                message.setIntegerArguments(toolCards);
                message.addPlayer(player);
                break;
            default:
                break;
        }
        setChanged();
        notifyObservers(message);
    }
}
