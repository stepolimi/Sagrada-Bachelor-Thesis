package it.polimi.ingsw.server.model.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.serverCostants.Costants.NUM_SCHEMAS;

public class SetSchemas {
    private List<Schema> schemas ;
    private List <Integer> nSchema;

    public SetSchemas(int nPlayers) {
        this.nSchema = new ArrayList<Integer>(){};
        this.createSchemas(nPlayers);
    }

     // take the player's index and returns the set of schemas from which the player will choose one
    public List<Schema> deliver(int nPlayer)
    {
    return schemas.subList(nPlayer*4,nPlayer*4+4);
    }

    private void createSchemas(int nPlayers){
        for(int i=1;i<=NUM_SCHEMAS;i++)
            nSchema.add(i);

        schemas = new ArrayList<Schema>();
        for(int i=0;i<2*nPlayers;i++)
        {
            int random = (int) ( Math.random()*nSchema.size());
            try {
                schemas.add(new Schema().schemaInit(nSchema.get(random)));
                schemas.add(new Schema().schemaInit(nSchema.get(random)+12));
                nSchema.remove(random);
            }catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
