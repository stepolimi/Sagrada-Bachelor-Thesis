package it.polimi.ingsw.server.model.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetSchemas {
    private List<Schema> schema ;
    private List <Integer> nSchema;

    public SetSchemas(int nPlayers) {
        nSchema = new ArrayList<Integer>(){};

        for(int i=1;i<13;i++)
            nSchema.add(i);
        schema = createSchemas(nPlayers);
    }

     // take the player's index and returns the set of schemas from which the player will choose one
    public List<Schema> deliver(int nPlayer)
    {
    return schema.subList(nPlayer*4,nPlayer*4+4);
    }

    private List<Schema> createSchemas(int nPlayers){
        schema = new ArrayList<Schema>();
        for(int i=0;i<2*nPlayers;i++)
        {
            int random = (int) ( Math.random()*nSchema.size());
            try {
                schema.add(new Schema().schemaInit(nSchema.get(random)));
                schema.add(new Schema().schemaInit(nSchema.get(random)+12));
                nSchema.remove(random);
            }catch(IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
        return schema;
    }


}
