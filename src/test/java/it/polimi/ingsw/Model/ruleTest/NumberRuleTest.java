package it.polimi.ingsw.Model.ruleTest;

import it.polimi.ingsw.Model.Colour;
import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Schema;
import it.polimi.ingsw.Model.rules.InsertionRule;
import it.polimi.ingsw.Model.rules.NumberRule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberRuleTest {
    Schema schema = new Schema();
    Player player = new Player("player 1");
    Dice dice = new Dice(Colour.ANSI_GREEN, 4);
    InsertionRule rule = new NumberRule();

    @Test
    public void correctInsertion() throws IOException {
        schema = schema.schemaInit(24);
        player.setSchema(schema);

        assertTrue(rule.checkRule(0,0,0,dice, schema));
        assertTrue(rule.checkRule(0,1,1,dice, schema));
        assertTrue(rule.checkRule(3,1,2,dice, schema));
    }

    @Test
    public void wrongInsertion() throws IOException {
        schema = schema.schemaInit(24);
        player.setSchema(schema);

        assertFalse(rule.checkRule(0,1,2,dice, schema));

    }


}
