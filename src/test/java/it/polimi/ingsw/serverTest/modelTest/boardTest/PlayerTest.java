package it.polimi.ingsw.serverTest.modelTest.boardTest;

import it.polimi.ingsw.server.model.cards.PrivateObjective;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.model.builders.PrivateObjectiveBuilder.buildPrivateObjective;
import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


class PlayerTest {
    private Player player;
    private Schema schema2;
    private Schema customSchema;
    private PrivateObjective privateObjective;
    private List<Schema> schemas = new ArrayList<>();

    private void testInit(){
        player = new Player("player 1");
        schema2 = schemaInit(2);
        customSchema = schemaInit(3);
        privateObjective = buildPrivateObjective(2);
        schemas.add(schemaInit(1));
        schemas.add(schema2);
    }

    private Schema schemaInit(int n){
        Schema schema = new Schema();
        schema = buildSchema(n);

        return schema;
    }

    @Test
    void setSchema(){
        testInit();
        player.setSchemas(schemas);
        player.setSchema(schema2.getName());

        assertEquals(schema2,player.getSchema());
        assertEquals(5,player.getFavour());
    }

    @Test
    void setCustomSchema(){
        testInit();
        player.setSchema(customSchema);

        assertEquals(customSchema,player.getSchema());
        assertEquals(3,player.getFavour());
    }

    @Test
    void setAttributes(){
        testInit();
        player.setSchemas(schemas);
        player.setSchema(schema2.getName());

        assertEquals("player 1",player.getNickname());

        player.setFavour(1);
        assertEquals(1,player.getFavour());

        player.setPrCard(privateObjective);
        assertEquals(privateObjective,player.getPrCard());

        player.setScore(10);
        assertEquals(10,player.getScore());

        schemas.forEach(schema -> assertTrue(player.getNameSchemas().contains(schema.getName())));
    }
}


