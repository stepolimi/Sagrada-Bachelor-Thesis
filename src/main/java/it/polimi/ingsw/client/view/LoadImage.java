package it.polimi.ingsw.client.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadImage {

    public void displayImage(String file) throws IOException
    {
        final String filePath = "src/main/data/CliImage/"+file;  //import every schema from
        //json file form /src/main/data/Schema/i.json
        FileReader f;
        f = new FileReader(filePath);
        int i=0;
        BufferedReader b;
        b = new BufferedReader(f);
        try {
            String sc;
            sc = b.readLine();
            while(sc!=null)
            {
                i++;
                System.out.println("\u001B[3"+((i%9)+1)+"m"+sc+Colour.RESET);
                sc = b.readLine();
            }
        }
        finally {
            b.close();
        }
    }
}
