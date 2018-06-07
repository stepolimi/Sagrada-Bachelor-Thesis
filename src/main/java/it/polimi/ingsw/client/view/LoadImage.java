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
        Colour colour;
        b = new BufferedReader(f);
        try {
            String sc;
            sc = b.readLine();
            while(sc!=null)
            {

                i++;
                switch (i/2)
                {
                    case 0:  colour = Colour.ANSI_GREEN;break;
                    case 1: colour = Colour.ANSI_RED;break;
                    case 2:colour = Colour.ANSI_BLUE;break;
                    case 3: colour = Colour.ANSI_YELLOW;break;
                    default:colour = Colour.ANSI_PURPLE;
                }
                System.out.println(Colour.colorString(sc,colour));
                sc = b.readLine();
            }
        }
        finally {
            b.close();
        }
    }
}
