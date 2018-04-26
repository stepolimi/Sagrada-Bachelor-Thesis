package it.polimi.ingsw;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Schema {
    private String name;
    private int difficult;
    private Box table [][];
    private static final int rows = 4;
    private static final int columns = 5;

    public Schema(int difficult,Box table[][],String name)
    {
        this.name = name;
        this.difficult = difficult;
        this.table = table;
    }
    public Schema(){}

    public void schemaInit (Schema sch, int i) throws IOException /*,NotInRangeException*/{
        Gson g = new Gson();

        FileReader f;
        f=new FileReader("C:\\Users\\stefano\\IdeaProjects\\sagrada_familia\\src\\main\\java\\Schemi\\Schema_" + i);

        BufferedReader b;
        b=new BufferedReader(f);

        String sc;
        sc=b.readLine();

        sch= g.fromJson(sc,Schema.class);
        sch.toString();
    }
    @Override
    public String toString() {
        System.out.println(this.name + "\n");
        System.out.println(this.difficult + "\n");
        System.out.println(this.table[0][0].getNumber() + "\n");
        System.out.println(this.table[0][0].getC() + "\n");
        return "ciao";
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
}
