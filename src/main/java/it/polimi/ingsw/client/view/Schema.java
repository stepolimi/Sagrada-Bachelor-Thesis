package it.polimi.ingsw.client.view;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Schema {
    private String name;
    private Dices grid [][];
    private  final int ROWS = 4;
    private final int COLUMNS = 5;
    int difficult;


    public Schema InitSchema(String nome) throws IOException
    {
        Schema sch = new Schema();
        final String filePath = "src/main/data/SchemaClient/" + nome + ".json";  //import every schema from
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
    @Override
    public String toString() {
        String str = "";
        str += this.name+"\n";

        str+= "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n";
        for(int i=0; i<this.ROWS;i++)
        {
            str+= "║  ";
            for(int j=0;j<this.COLUMNS;j++)
            {
                str+=" "+grid[i][j].toString()+" ";

            }
            str+="  ║\n";

        }
        str+="┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n";
        str += "Difficoltà:";
        for(int i=0;i<difficult;i++)
        str+= "*";
        str+="\n";
     return str;
    }
}
