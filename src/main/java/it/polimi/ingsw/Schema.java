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
    private String name;
    private int difficult;
    private Box [][] table;
    private static final int rows = 4;
    private static final int columns = 5;

    public void schemaInit (Schema sch, int n) throws IOException {

        final String filePath = new String("src/main/data/Schema/" + n + ".json");
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
