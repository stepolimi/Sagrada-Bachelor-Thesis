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


import com.google.gson.Gson;
import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.model.rules.RulesManager;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static it.polimi.ingsw.costants.GameConstants.*;

public class Schema extends Observable{
    private String name;  //name of schema card
    private int difficult;
    private Box[][] table;
    private boolean isEmpty = true;
    private static final int ROWS = 4;
    private static final int COLUMNS = 5;
    private String player;
    private RulesManager rulesManager;

    public Schema schemaInit (int n) throws IOException {   //constructs the Schema obj from file

        Schema sch = new Schema();
        final String filePath = "src/main/data/Schema/" + n + ".json";  //import every schema from
        //json file form /src/main/data/Schema/i.json
        Gson g = new Gson();

        FileReader f;
        f = new FileReader(filePath);

        BufferedReader b;
        b = new BufferedReader(f);
        try {
            String sc;
            sc = b.readLine();
            sch = g.fromJson(sc, Schema.class);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally {
            b.close();
        }
        return sch;
    }

    public int getDifficult() {
        return difficult;
    }

    public Box getTable(int i, int j) {
        return table[i][j];
    }

    public void testInsertDice(int rows , int columns, Dice d, int tool) throws InsertDiceException{
        List<String> action = new ArrayList<String>();
        if(!rulesManager.checkRules(tool,rows,columns,d,this)) {
            action.add(PLACE_DICE_SCHEMA_ERROR);
            action.add(player);
            setChanged();
            notifyObservers(action);
            throw new InsertDiceException();
        }
    }

    public void insertDice(int rows , int columns, Dice d, int tool) throws InsertDiceException {
        List<String> action = new ArrayList<String>();
        if(rulesManager.checkRules(tool,rows,columns,d,this)) {                                 //can be useless
            this.isEmpty = false;
            this.table[rows][columns].setDice(d);
            action.add(PLACE_DICE_SCHEMA);
            action.add(player);
            action.add(((Integer)rows).toString());
            action.add(((Integer)columns).toString());
            action.add(d.getColour().toString());
            action.add(((Integer)d.getValue()).toString());
            setChanged();
            notifyObservers(action);
        } else
            throw new InsertDiceException();
    }

    public void insertDice(int rows , int columns, Dice d)
    {
        this.isEmpty = false;
        this.table[rows][columns].setDice(d);

    }

    //it removed dice from rows-colomuns position. it returns null if is already empty
    public Dice removeDice(int rows,int columns) {
        List<String> action = new ArrayList<String>();
        Dice d;
        if(this.table[rows][columns].getDice()!=null) {
            d = this.table[rows][columns].getDice();
            this.table[rows][columns].setDice(null);
            action.add(PICK_DICE_SCHEMA);
            action.add(player);
            action.add(((Integer)rows).toString());
            action.add(((Integer)columns).toString());
            setChanged();
            notifyObservers(action);
            return d;
        }
        action.add(PICK_DICE_SCHEMA_ERROR);
        action.add(player);
        setChanged();
        notifyObservers(action);
        return null;
    }

    public List nearDice(int rows,int columns)
    {

        List<Dice> nearDice = new ArrayList<Dice>(9);
        for(int i=-1;i<2;i++)
            for (int j = -1; j < 2; j++)
                nearDice.add(checkNearDice(rows + i, columns + j));

        // rimuovo il dado stesso ( ho preferito aggiungere la remove che controllare ogni ciclo che non fosse [0][0])
        nearDice.remove(4);
        return nearDice;

    }

    // sfrutto il fatto che se si va fuori dai contorni mi viene generata un'eccezione per mettere la casella corrispondente a null
    // riesco a uniformare il codice senza gestire ogni singolo caso (bordo,spigolo)
    public Dice checkNearDice(int rows,int columns)
    {
        Dice d=null;
        try {
            d= this.table[rows][columns].getDice();
        }catch (ArrayIndexOutOfBoundsException e) {
            // siamo nel contorno
            return d;
        }
        return d;
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setRulesManager(RulesManager rulesManager){
        this.rulesManager = rulesManager;
    }

    public void setPlayer(Player player){ this.player = player.getNickname(); }


    @Override
    public String toString() {
        String str="";
        str+= this.name +"\n";
        str+= "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        for(int i=0; i<this.ROWS;i++)
        {
            str+= "║  ";
            for(int j=0;j<this.COLUMNS;j++)
            {
                str+=table[i][j].toString();

            }
            str+="  ║\n";

        }
        str+="┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
        str+="Difficult:";
        for(int i=0;i<this.getDifficult();i++)
            str+="*";

        return str;
    }

    public void dump(){ System.out.println(this); }

}
