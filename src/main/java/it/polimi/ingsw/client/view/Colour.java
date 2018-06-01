//colour assumed by dices or box in a schema

package it.polimi.ingsw.client.view;

public enum Colour {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m");
    static final String RESET = "\u001B[0m";

    private String escape;

    Colour(String escape) {
        this.escape = escape;
    }

    public static Colour stringToColour(String c)
    {
        if(c.equals("ANSI_GREEN")){ return Colour.ANSI_GREEN; }
        else if(c.equals("ANSI_BLUE")) { return Colour.ANSI_BLUE; }
        else if(c.equals("ANSI_RED")){ return Colour.ANSI_RED; }
        else if(c.equals("ANSI_PURPLE")) { return Colour.ANSI_PURPLE; }
        else if(c.equals("ANSI_YELLOW")) { return Colour.ANSI_YELLOW; }
        return null;
    }

    public String escape() {
        return escape;
    }

    public static String ColorString(String str,Colour colour)
    {
        return colour.escape+str+Colour.RESET;
    }
}
