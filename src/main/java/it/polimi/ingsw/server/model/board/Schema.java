package it.polimi.ingsw.server.model.board;


import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;
import it.polimi.ingsw.server.model.cards.toolCards.ToolCard;
import it.polimi.ingsw.server.model.rules.RulesManager;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static it.polimi.ingsw.server.costants.MessageConstants.*;
import static it.polimi.ingsw.server.costants.Constants.COLUMNS_SCHEMA;
import static it.polimi.ingsw.server.costants.Constants.ROWS_SCHEMA;

public class Schema extends Observable {
    private String name;
    private int difficult;
    private Box[][] table;
    private boolean isEmpty = true;
    private String player;
    private int size = 0;
    private String json;

    /**
     * Creates a schema with "ROW_SCHEMA" * "COLUMN_SCHEMA"  boxes.
     */
    public Schema() {
        this.name= "";
        this.difficult = 0;
        table = new Box[ROWS_SCHEMA][COLUMNS_SCHEMA];

        for(int i=0;i<ROWS_SCHEMA;i++)
            for(int j=0;j<COLUMNS_SCHEMA;j++)
                table[i][j] = new Box(null,0);

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTable(int i,int j,Box box) {
        table[i][j] = box;
    }

    public void setJson(String json) { this.json = json; }

    public String getJson() { return json; }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public int getDifficult() {
        return difficult;
    }

    public Box getTable(int i, int j) {
        return table[i][j];
    }

    /**
     * Checks if it's possible to insert a dice in the specified row and column of the schema.
     * If it is not possible notifies the error to the player.
     * @param rows is the row of the schema where it is trying to insert the dice.
     * @param columns is the column of the schema where it is trying to insert the dice.
     * @param d is the dice to be inserted in the schema.
     * @param toolCard is the eventual tool card used for the dice's insertion.
     * @throws InsertDiceException if the insertion is not correct.
     */
    public void testInsertDice(int rows, int columns, Dice d, ToolCard toolCard) throws InsertDiceException {
        List<String> action = new ArrayList<String>();
        if (!RulesManager.getRulesManager().checkRules(toolCard, rows, columns, d, this)) {
            action.add(PLACE_DICE_SCHEMA_ERROR);
            action.add(player);
            setChanged();
            notifyObservers(action);
            throw new InsertDiceException();
        }
    }

    /**
     * Puts the dice in the specified row and column of the schema and notifies it to the players.
     * @param rows is the row of the schema where the dice will be inserted.
     * @param columns is the column of the schema where the dice will be inserted.
     * @param d is the dice that will be inserted in the schema.
     */
    public void insertDice(int rows, int columns, Dice d) {
        List action = new ArrayList<>();
        this.isEmpty = false;
        size++;
        this.table[rows][columns].setDice(d);
        action.add(PLACE_DICE_SCHEMA);
        action.add(player);
        action.add(rows);
        action.add(columns);
        action.add(d.getColour().toString());
        action.add(d.getValue());
        setChanged();
        notifyObservers(action);
    }

    /**
     * Puts the dice in the specified row and column of the schema without notifying it to the players.
     * @param rows is the row of the schema where the dice will be inserted.
     * @param columns is the column of the schema where the dice will be inserted.
     * @param d is the dice that will be inserted in the schema.
     */
    public void silentInsertDice(int rows, int columns, Dice d) {
        this.isEmpty = false;
        this.table[rows][columns].setDice(d);
        size++;
    }

    /**
     * Checks if it's possible to remove the dice in the specified row and column of the schema.
     * If it is not possible notifies the error to the player.
     * @param rows is the row of the schema where it is trying to remove the dice.
     * @param columns is the column of the schema where it is trying to remove the dice.
     * @return the dice in the specified position if it is present.
     * @throws RemoveDiceException when there is no dices in the specified box of the schema.
     */
    public Dice testRemoveDice(int rows, int columns) throws RemoveDiceException {
        List<String> action = new ArrayList<String>();
        if (this.table[rows][columns].getDice() == null) {
            action.add(PICK_DICE_SCHEMA_ERROR);
            action.add(player);
            setChanged();
            notifyObservers(action);
            throw new RemoveDiceException();
        }
        return table[rows][columns].getDice();
    }


    /**
     * Removes the dice in the specified row and column of the schema, returns it and notifies it to the players.
     * @param rows is the row of the schema where the dice will be removed.
     * @param columns is the column of the schema where the dice will be removed.
     * @return the dice that was in the specified row and column of the schema.
     */
    public Dice removeDice(int rows, int columns) {
        List action = new ArrayList<>();
        Dice d;
        size--;
        if (size == 0)
            isEmpty = true;
        d = table[rows][columns].getDice();
        table[rows][columns].setDice(null);
        action.add(PICK_DICE_SCHEMA);
        action.add(player);
        action.add(rows);
        action.add(columns);
        setChanged();
        notifyObservers(action);
        return d;
    }

    /**
     * Removes the dice in the specified row and column of the schema.
     * @param rows is the row of the schema where the dice will be removed.
     * @param columns is the column of the schema where the dice will be removed.
     */
    public void silentRemoveDice(int rows, int columns) {
        table[rows][columns].setDice(null);
        size--;
        if (size == 0)
            isEmpty = true;
    }

    /**
     * Checks all the boxes near the specified position of the schema and returns a list with the dices found in those boxes.
     * @param row is the row of the schema where is the wanted box.
     * @param column is the column of the schema where is the wanted box.
     * @return a list with the dices found near the specified position.
     */
    public List<Dice> nearDice(int row, int column) {
        List<Dice> nearDice = new ArrayList<Dice>(9);

        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                nearDice.add(checkNearDice(row + i, column + j));
        nearDice.remove(4);
        return nearDice;

    }

    /**
     * Checks if it is present a dice in the specified row and column of the schema and returns it.
     * @param row is the row of the schema where it will check for a dice.
     * @param column is the column of the schema where it will check for a dice.
     * @return the dice in the specified row and column of the schema if it is present. if it is not, returns null.
     */
    private Dice checkNearDice(int row, int column) {
        Dice d;

        try {
            d = this.table[row][column].getDice();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return d;
    }

    /**
     * Collects all the dices in the schema and returns them.
     * @return all the dices in the schema.
     */
    public List<Dice> getDices() {
        List<Dice> dices = new ArrayList<>();

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++)
                if (table[i][j].getDice() != null)
                    dices.add(table[i][j].getDice());

        return dices;
    }

    /**
     * Collects all the dices in the specified row of the schema and returns them.
     * @param x is the row where the dices will be searched.
     * @return all the dices in the specified row of the schema.
     */
    public List<Dice> getDicesInRow(int x) {
        List<Dice> dices = new ArrayList<>();

        for (int j = 0; j < 5; j++)
            if (table[x][j].getDice() != null)
                dices.add(table[x][j].getDice());

        return dices;
    }

    /**
     * Collects all the dices in the specified column of the schema and returns them.
     * @param y is the column where the dices will be searched.
     * @return all the dices in the specified column of the schema.
     */
    public List<Dice> getDicesInColumn(int y) {
        List<Dice> dices = new ArrayList<>();

        for (int i = 0; i < 4; i++)
            if (table[i][y].getDice() != null)
                dices.add(table[i][y].getDice());

        return dices;
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setPlayer(Player player) {
        this.player = player.getNickname();
    }

    public int getSize() {
        return this.size;
    }


    @Override
    public String toString() {
        String str = "";
        str += this.name + "\n";
        str += "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        for (int i = 0; i < ROWS_SCHEMA; i++) {
            str += "║  ";
            for (int j = 0; j < COLUMNS_SCHEMA; j++) {
                str += table[i][j].toString();

            }
            str += "  ║\n";

        }
        str += "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
        str += "Difficult:";
        for (int i = 0; i < this.getDifficult(); i++)
            str += "*";

        return str;
    }

    public void dump() {
        System.out.println(this);
    }

}
