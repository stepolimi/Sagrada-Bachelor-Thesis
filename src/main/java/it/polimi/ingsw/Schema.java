package it.polimi.ingsw;


import com.google.gson.Gson;
import exception.NotInRangeException;

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

    public void schemaInit (Schema sch, int n) throws IOException , NotInRangeException {
        if(n<1 || n>24) throw new NotInRangeException();
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

    public void insertDice(int rows , int columns)
    {

    }
    /*public Dice removeDice(int rows,int columns)
    {

    }
    */

    @Override
    public String toString() {

        return "";
    }

    public void dump(){ System.out.println(this); }

}
