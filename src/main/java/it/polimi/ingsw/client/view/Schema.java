package it.polimi.ingsw.client.view;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static it.polimi.ingsw.costants.GameConstants.PAINT_ROW;

public class Schema {
    private String name;
    private Dices grid [][];
    private  final int ROWS = 4;
    private final int COLUMNS = 5;
    int difficult;

    public String paint [] = new String[PAINT_ROW];
    public Schema()
    {
        for(int i=0;i<PAINT_ROW;i++)
            paint[i] ="";

        grid = new Dices[ROWS][COLUMNS];
        for(int i=0;i<ROWS;i++)
        {
            for (int j=0;j<COLUMNS;j++)
            {
                grid[i][j]= new Dices("",0,null);
            }
        }
    }


    public Schema InitSchema(String nome) throws IOException
    {
        Schema sch = new Schema();
        final String filePath = "src/main/data/" + nome + ".json";  //import every schema from
        //json file form /src/main/data/Schema/i.json
        Gson g = new Gson();

        FileReader f;
        f = new FileReader(filePath);

        BufferedReader b;
        b = new BufferedReader(f);
        try {
            String sc;
            sc = b.readLine();

            sch = g.fromJson(sc,Schema.class);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally {
            b.close();
        }
        return sch;
    }


    public Dices[][] getGrid()
    {
        return this.grid;
    }

    public void splitImageSchema() {
        paint[1]= this.name;

        paint[2]= "┏-----------------------------┓";
        for(int i=0; i<this.ROWS;i++)
        {
            paint[3+i]= "║  ";
            for(int j=0;j<this.COLUMNS;j++)
            {
                paint[3+i]+=" "+grid[i][j].toString()+" ";

            }
            paint[3+i]+="  ║";
        }
        paint[7]="┗-----------------------------┛";
        paint[8]= "Difficoltà:";
        for(int i=0;i<difficult;i++)
            paint[8]+= "*";

    }

    public void showImage()
    {
        for(int i=0;i<PAINT_ROW;i++)
        {
            System.out.print(paint[i]+"\n");
        }
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDifficult(int difficult)
    {
        this.difficult = difficult;
    }

    public boolean nearConstraint(int rows, int columns, String costraint)
    {
        if(costraint.equals(""))
            return true;

        for(int i=-1;i<2;i++)
             for (int j = -1; j < 2; j++)
             {
                 if(i!=0 || j!=0) {
                     if (!checkNearCostraint(rows + i, columns + j, costraint))
                         return false;
                 }
             }

        return true;

    }
    public boolean checkNearCostraint(int rows,int columns,String costraint)
    {

        try {
            if(grid[rows][columns].getConstraint().equals(costraint))
              return false;
        }catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
        return true;
    }

}
