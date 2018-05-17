package it.polimi.ingsw.serverTest.modelTest.ruleTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.rules.ColourRule;
import it.polimi.ingsw.server.model.rules.InsertionRule;
import it.polimi.ingsw.server.model.rules.RulesManager;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColourRuleTest {
    Schema schema = new Schema();
    Player player = new Player("player 1");
    Dice dice = new Dice(Colour.ANSI_BLUE, 4);
    InsertionRule rule = new ColourRule();
    List<Schema> schemas = new ArrayList<Schema>();

    @Test
    public void correctInsertion() throws IOException {
        player.setObserver(new VirtualView());
        schema.setRulesManager(new RulesManager());
        schema = schema.schemaInit(24);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(0);

        assertTrue(rule.checkRule(0,0,0,dice, schema));
        assertTrue(rule.checkRule(0,0,1,dice, schema));
        assertTrue(rule.checkRule(2,0,2,dice, schema));
    }

    @Test
    public void wrongInsertion() throws IOException {
        player.setObserver(new VirtualView());
        schema.setRulesManager(new RulesManager());
        schema = schema.schemaInit(24);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(0);

        assertFalse(rule.checkRule(0,0,2,dice, schema));

    }
}
