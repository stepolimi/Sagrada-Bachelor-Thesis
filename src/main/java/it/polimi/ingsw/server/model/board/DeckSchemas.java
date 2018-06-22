package it.polimi.ingsw.server.model.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.server.builders.SchemaBuilder.buildSchema;
import static it.polimi.ingsw.server.serverCostants.Constants.NUM_SCHEMAS;

public class DeckSchemas {
    private List<Schema> schemas;
    private List<Integer> nSchema;

    public DeckSchemas(int nPlayers) {
        this.nSchema = new ArrayList<>() ;
        this.createSchemas(nPlayers);
    }

    // take the player's index and returns the set of schemas from which the player will choose one
    public List<Schema> deliver(int nPlayer) {
        return schemas.subList(nPlayer * 4, nPlayer * 4 + 4);
    }

    private void createSchemas(int nPlayers) {
        Random rand = new Random();
        for (int i = 1; i <= NUM_SCHEMAS; i++)
            nSchema.add(i);

        schemas = new ArrayList<>();
        for (int i = 0; i < 2 * nPlayers; i++) {
            int index = rand.nextInt(nSchema.size());
            try {
                schemas.add(buildSchema(nSchema.get(index)));
                schemas.add(buildSchema(nSchema.get(index) + 12));
                nSchema.remove(index);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
