package it.polimi.ingsw.serverTest.modelTest.boardTest;

import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.board.DeckSchemas;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeckSchemasTest {
    private DeckSchemas set = new DeckSchemas(3);

    private List<Schema> schemasList() {
        List<Schema> schemas = new ArrayList<Schema>();
        for (int i = 1; i < 13; i++) {
            schemas.add(buildSchema(i));
            schemas.add(buildSchema(i + 12));

        }
        return schemas;
    }

    @Test
    void deliverTest() {
        List<Schema> schemas = schemasList();
        boolean found = false;

        assertEquals(4, set.deliver(0).size());
        assertEquals(4, set.deliver(1).size());
        assertEquals(4, set.deliver(2).size());

        for (Schema s : set.deliver(0)) {
            for (Schema s2 : schemas) {
                if (s.getName().equals(s2.getName())) {
                    found = true;
                }
            }
            assertTrue(found);
            found = false;
        }

        //each set of schemas delivered contains different schemas
        for (Schema s : set.deliver(0))
            assertFalse(set.deliver(1).contains(s) || set.deliver(2).contains(s));
        for (Schema s : set.deliver(1))
            assertFalse(set.deliver(0).contains(s) || set.deliver(2).contains(s));
    }
}
