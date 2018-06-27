
package it.polimi.ingsw.server.model.board;

/**
 * Are the possible colours of a dice or a box's colour restriction.
 */
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

    public String escape() {
        return escape;
    }

}
