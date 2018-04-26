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



package it.polimi.ingsw;


import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.SourceTree;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Schema {
    private String name;  //nme of schema card
    private int difficult;
    private Box [][] table;
    private static final int rows = 4;
    private static final int columns = 5;

    public void schemaInit (Schema sch, int n) throws IOException {   //costructs the Schema obj from file

        final String filePath = new String("src/main/data/Schema/" + n + ".json");  //import every schema from
                                                                //json file form /src/main/data/Schema/i.jon
        Gson g = new Gson();

        FileReader f;
        f=new FileReader(filePath);

        BufferedReader b;
        b=new BufferedReader(f);

        String sc;
        sc=b.readLine();

        sch= g.fromJson(sc,Schema.class);
        sch.toString();
    }


    public int getDifficult() {
        return difficult;
    }

    public boolean insertDice(int rows , int columns,Dice d)
    {
        if(this.table[rows][columns].isOccupied()==false)
        {
            this.table[rows][columns].setFull(true);
            this.table[rows][columns].setDice(d);
            return true;
        }
        return false;
    }
    // ritorna il dado inserito nello schema in posizione rows e columns, se è vuota ritorna null
    public Dice removeDice(int rows,int columns)
    {
        Dice d;
        if(this.table[rows][columns].isOccupied()==true)
        {
            d = this.table[rows][columns].getDice();
            this.table[rows][columns].setDice(null);
            this.table[rows][columns].setFull(false);
            return d;
        }
        return null;
    }


    @Override
    public String toString() {
        String str="";
        System.out.println(this.getName());
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        for(int i=0; i<this.rows;i++)
        {
            System.out.print("║  ");
            for(int j=0;j<this.columns;j++)
            {
                System.out.print(table[i][j].toString());

            }
            System.out.print("  ║");
            System.out.println("");
        }
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        System.out.print("Difficult:");
        for(int i=0;i<this.getDifficult();i++)
        System.out.print("*");

        return str;
    }

    public void dump(){ System.out.println(this); }

    public String getName() {
        return name;
    }
}
