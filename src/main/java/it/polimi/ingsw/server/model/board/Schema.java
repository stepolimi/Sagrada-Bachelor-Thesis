/*
class used to construct and represent the schema of every player.

***to do yet***
* -----create method that create 4 schema card
        for each player (for a maximum of 16 schema obj). firstly it exracts 2 card for every player with
         random function; then, it associates every (front) card with his back card.
         [for example first extract--> schema2 and schema7
                (we associated their back card with increment of 12)
                --> (schema2/schema14 and schema7/schema19)
 */



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
    private String name;  //name of schema card
    private int difficult;
    private Box[][] table;
    private boolean isEmpty = true;
    private String player;
    private int size = 0;
    private String json;

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

    public void silentInsertDice(int rows, int columns, Dice d) {
        this.isEmpty = false;
        this.table[rows][columns].setDice(d);
        size++;
    }

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

    //it removed dice from rows-colomuns position. it throws exception if is already empty
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

    public void silentRemoveDice(int rows, int columns) {
        table[rows][columns].setDice(null);
        size--;
        if (size == 0)
            isEmpty = true;
    }

    public List<Dice> nearDice(int rows, int columns) {
        List<Dice> nearDice = new ArrayList<Dice>(9);

        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                nearDice.add(checkNearDice(rows + i, columns + j));
        nearDice.remove(4);
        return nearDice;

    }

    private Dice checkNearDice(int rows, int columns) {
        Dice d;

        try {
            d = this.table[rows][columns].getDice();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return d;
    }

    public List<Dice> getDices() {
        List<Dice> dices = new ArrayList<>();

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++)
                if (table[i][j].getDice() != null)
                    dices.add(table[i][j].getDice());

        return dices;
    }

    public List<Dice> getDicesInRow(int x) {
        List<Dice> dices = new ArrayList<>();

        for (int j = 0; j < 5; j++)
            if (table[x][j].getDice() != null)
                dices.add(table[x][j].getDice());

        return dices;
    }

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
