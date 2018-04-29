package it.polimi.ingsw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class setSchemi {
    List<Schema> schema ;
    List <Integer> nSchema;

    public setSchemi(int nPlayer)
    {
        nSchema = new ArrayList<Integer>(){};
        schema = new ArrayList<Schema>();

        for(int i=1;i<13;i++)
        nSchema.add(i);

        for(int i=0;i<2*nPlayer;i++)
        {
            int random = (int) ( Math.random()*nSchema.size());
            try {
                schema.add(new Schema().schemaInit(nSchema.get(random)));
                schema.add(new Schema().schemaInit(nSchema.get(random)+12));
                nSchema.remove(random);
              //  nSchema.remove(random);
            }catch(IOException e)
            {
                System.out.println(e.getMessage());
            }
        }

    }

     // gli viene passato il numero del player e ritornerà le carte schema da cui poi il player sceglierà la propria
    public List deliver(int nPlayer)
    {
    return schema.subList(nPlayer*4,nPlayer*4+4);
    }



}
