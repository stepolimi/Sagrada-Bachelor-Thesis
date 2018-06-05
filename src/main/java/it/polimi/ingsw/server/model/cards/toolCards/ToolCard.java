package it.polimi.ingsw.server.model.cards.toolCards;

import java.util.ArrayList;
import java.util.List;

public class ToolCard {

    private String name;
    private int number;
    private boolean used;
    private List<String> ignoredRules;
    private List<List<String>> nextActions;

    public void setUsed(boolean used) { this.used = used; }

    public boolean isUsed() { return used; }

    public String getName() { return name; }

    public int getNumber() { return number; }

    public List<String> getIgnoredRules() { return ignoredRules; }

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