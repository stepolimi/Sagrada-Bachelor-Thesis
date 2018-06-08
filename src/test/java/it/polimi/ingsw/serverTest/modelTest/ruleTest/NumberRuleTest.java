package it.polimi.ingsw.serverTest.modelTest.ruleTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.rules.InsertionRule;
import it.polimi.ingsw.server.model.rules.NumberRule;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberRuleTest {
    Schema schema = new Schema();
    Player player = new Player("player 1");
    Dice dice = new Dice(Colour.ANSI_GREEN, 4);
    InsertionRule rule = new NumberRule();
    List<Schema> schemas = new ArrayList<Schema>();

    @Test
    public void correctInsertion() throws IOException {
        player.setObserver(new VirtualView());
        schema = schema.schemaInit(24);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());
        assertTrue(rule.checkRule(0,0,dice, schema));
        assertTrue(rule.checkRule(1,1,dice, schema));
    }

    @Test
    public void wrongInsertion() throws IOException {
        player.setObserver(new VirtualView());
        schema = schema.schemaInit(24);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());

        assertFalse(rule.checkRule(1,2,dice, schema));
    }


}
