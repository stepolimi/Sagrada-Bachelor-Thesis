package it.polimi.ingsw.server.internalMesages;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private String head;
    private List<String> stringArguments;
    private List<Integer> integerArguments;
    private List<String> players;

    public Message(String head){
        this.head = head;
        stringArguments = new ArrayList<>();
        integerArguments = new ArrayList<>();
        players = new ArrayList<>();
    }

    public String getHead(){ return head; }

    public void setIntegerArguments(List<Integer> integerArguments) { this.integerArguments.addAll(integerArguments); }

    public void addIntegerArgument(Integer integerArgument) { integerArguments.add(integerArgument); }

    public List<Integer> getIntegerArguments(){ return integerArguments;}

    public Integer getIntegerArgument(int index){ return integerArguments.get(index);}

    public void setStringArguments(List<String> stringArguments) { this.stringArguments.addAll(stringArguments); }

    public void addStringArguments(String stringArgument) { stringArguments.add(stringArgument); }

    public List<String> getStringArguments(){ return stringArguments;}

    public String getStringArgument(int index){ return stringArguments.get(index);}

    public void setPlayers(List<String> players) { this.players.addAll(players); }

    public void addPlayer(String player){ players.add(player); }

    public List<String> getPlayers(){ return players; }
}
