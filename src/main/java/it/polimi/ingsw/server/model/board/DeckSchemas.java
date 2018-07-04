package it.polimi.ingsw.server.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static it.polimi.ingsw.server.costants.Constants.NUM_SCHEMAS;

public class DeckSchemas {
    private List<Schema> schemas;
    private final List<Integer> nSchema;

    public DeckSchemas(int nPlayers) {
        this.nSchema = new ArrayList<>() ;
        this.createSchemas(nPlayers);
    }

    /**
     * Gives a list of four schemas to a player from which he will choose one.
     * @param nPlayer index of a player.
     * @return a list with four schemas for the player.
     */
    public List<Schema> deliver(int nPlayer) {
        return schemas.subList(nPlayer * 4, nPlayer * 4 + 4);
    }

    /**
     * Creates 4 random schemas for each player in the game.
     * @param nPlayers number of players in the game.
     */
    private void createSchemas(int nPlayers) {
        Random rand = new Random();
        for (int i = 1; i <= NUM_SCHEMAS; i++)
            nSchema.add(i);

        schemas = new ArrayList<>();
        for (int i = 0; i < 2 * nPlayers; i++) {
            int index = rand.nextInt(nSchema.size());
            schemas.add(buildSchema(nSchema.get(index)));
            schemas.add(buildSchema(nSchema.get(index) + 12));
            nSchema.remove(index);

        }
    }


}
