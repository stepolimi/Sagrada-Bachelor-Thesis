package it.polimi.ingsw.serverTest.modelTest.ruleTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.rules.EmptyRule;
import it.polimi.ingsw.server.model.rules.InsertionRule;
import it.polimi.ingsw.server.model.rules.RulesManager;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmptyRuleTest {
    Schema schema = new Schema();
    Player player = new Player("player 1");
    Dice dice_1 = new Dice(Colour.ANSI_GREEN, 4);
    Dice dice_2 = new Dice(Colour.ANSI_YELLOW, 3);
    InsertionRule rule = new EmptyRule();
    List<Schema> schemas = new ArrayList<Schema>();

    @Test
    public void correctInsertion() throws IOException {
        player.setObserver(new VirtualView());
        schema.setRulesManager(new RulesManager());
        schema = schema.schemaInit(24);
        schema.insertDice(0,0,dice_1);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());
        assertTrue(rule.checkRule(0,1,0,dice_2, schema));
    }

    @Test
    public void wrongInsertion() throws IOException {
        player.setObserver(new VirtualView());
        schema.setRulesManager(new RulesManager());
        schema = schema.schemaInit(24);
        schema.insertDice(0,0,dice_1);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());

        assertFalse(rule.checkRule(0,0,0,dice_2, schema));
    }
}

