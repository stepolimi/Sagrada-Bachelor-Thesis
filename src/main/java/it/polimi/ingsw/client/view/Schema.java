package it.polimi.ingsw.client.view;

import com.google.gson.Gson;

import java.io.*;

import static it.polimi.ingsw.client.constants.MessageConstants.PAINT_ROW;

public class Schema {
    private String name;
    private Dices grid [][];
    int difficult;
    private static final int ROWS = 4;
    private static final int COLUMNS = 5;
    private String paint [];
    public Schema()
    {
        paint = new String[PAINT_ROW];
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

    public void setPaint(String[] paint) {
        this.paint = paint;
    }

    public Schema InitSchema(String nome)
    {
        Schema sch;
        Gson g = new Gson();
        String gsonString = null;
        try {
            gsonString = getGson(nome);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sch = g.fromJson(gsonString,Schema.class);
        return sch;
    }

    public String getGson(String nome) throws IOException
    {
        final String filePath = nome + ".json";  //import every schema from
        //json file form /src/main/data/Schema/i.json

        String sc="";
        InputStream is = Schema.class.getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            sc = reader.readLine();
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally {
            reader.close();
        }

        return sc;
    }

    public Schema initCustomSchema(String nome)
    {
        Schema sch;
        Gson g = new Gson();
        String gsonString = null;
        try {
            gsonString = getCustomGson(nome);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sch = g.fromJson(gsonString,Schema.class);
        return sch;
    }

    public String getCustomGson(String nome) throws  IOException
    {
        final String filePath = nome+".json";
        String gson;
        try (BufferedReader b = new BufferedReader(new FileReader(filePath))) {
            gson  = b.readLine();
        }
        return gson;
    }

    public String[] getPaint() {
        return paint;
    }

    public Dices[][] getGrid()
    {
        return this.grid;
    }

    public void splitImageSchema() {
        paint[1]= this.name;

        paint[2]= "┏-----------------------------┓";
        for(int i=0; i<ROWS;i++)
        {
            paint[3+i]= "║  ";
            for(int j=0;j<COLUMNS;j++)
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
    public void setDifficult(int nConstraint)
    {
        int difficult;

        if(nConstraint<8)
            difficult = 1;
        else if(nConstraint<11)
            difficult = 2;
        else if(nConstraint<12)
            difficult=3;
        else if(nConstraint<13)
            difficult=4;
        else if(nConstraint<14)
            difficult=5;
        else
            difficult=6;

        this.difficult = difficult;
    }

    public String getName() {
        return name;
    }

    public int getDifficult() {
        return difficult;
    }

    public boolean nearConstraint(int rows, int columns, String costraint)
    {
        if(costraint.equals(""))
            return true;


                     if ((checkNearCostraint(rows +1, columns, costraint) &&
                             checkNearCostraint(rows -1, columns , costraint) &&
                             checkNearCostraint(rows , columns + 1, costraint) &&
                             checkNearCostraint(rows , columns - 1, costraint)))
                         return true;


        return false;

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
