package it.polimi.ingsw.serverTest.modelTest.ruleTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.rules.InsertionRule;
import it.polimi.ingsw.server.model.rules.NumberRule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberRuleTest {
    private Schema schema;
    private Player player = new Player("player 1");
    private Dice dice = new Dice(Colour.ANSI_GREEN, 4);
    private InsertionRule rule = new NumberRule();
    private List<Schema> schemas = new ArrayList<>();

    @Test
    void correctInsertion(){
        schema = buildSchema(24);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());
        assertTrue(rule.checkRule(0,0,dice, schema));
        assertTrue(rule.checkRule(1,1,dice, schema));
    }

    @Test
    void wrongInsertion(){
        schema = buildSchema(24);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());

        assertFalse(rule.checkRule(1,2,dice, schema));
    }


}
