package it.polimi.ingsw.serverTest.modelTest.ruleTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.rules.EmptyRule;
import it.polimi.ingsw.server.model.rules.InsertionRule;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmptyRuleTest {
    private Schema schema;
    private Player player = new Player("player 1");
    private Dice dice_1 = new Dice(Colour.ANSI_GREEN, 4);
    private Dice dice_2 = new Dice(Colour.ANSI_YELLOW, 3);
    private InsertionRule rule = new EmptyRule();
    private List<Schema> schemas = new ArrayList<Schema>();

    @Test
    void correctInsertion() throws IOException {
        player.setObserver(VirtualView.getVirtualView());
        schema = buildSchema(24);
        schema.silentInsertDice(0,0,dice_1);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());
        assertTrue(rule.checkRule(1,0,dice_2, schema));
    }

    @Test
    void wrongInsertion() throws IOException {
        player.setObserver(VirtualView.getVirtualView());
        schema = buildSchema(24);
        schema.silentInsertDice(0,0,dice_1);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());

        assertFalse(rule.checkRule(0,0,dice_2, schema));
    }
}

