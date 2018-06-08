package it.polimi.ingsw.server.model.cards.toolCards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolCard {

    private String name;
    private int number;
    private boolean used;
    private List<String> ignoredRules;
    private List<String> specialEffects;
    private HashMap<String,String> restrictions;
    private List<List<String>> nextActions;

    public ToolCard() {
        this.name = "bella";
        this.number = 1;
        this.used = false;
        this.ignoredRules = new ArrayList<String>();
        ignoredRules.add("rule1");
        ignoredRules.add("rule2");
        this.restrictions = new HashMap<String, String>();
        this.nextActions = new ArrayList<List<String>>();
    }

    public void setUsed(boolean used) { this.used = used; }

    public boolean isUsed() { return used; }

    public String getName() { return name; }

    public int getNumber() { return number; }

    public List<String> getIgnoredRules() { return ignoredRules; }

    public List<String> getSpecialEffects() { return specialEffects; }

    public Map<String, String> getRestrictions() { return restrictions; }

    public List<List<String>> getNextActions() {
        List<List<String>> copy = new ArrayList<List<String>>();
        for(List<String> list: nextActions) {
            ArrayList<String> listCopy = new ArrayList<String>();
            copy.add(listCopy);
            for (String string : list)
                listCopy.add(string);

        }
        return copy;
    }

}