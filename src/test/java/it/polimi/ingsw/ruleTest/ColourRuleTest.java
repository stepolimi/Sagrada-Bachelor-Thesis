package it.polimi.ingsw.ruleTest;

import it.polimi.ingsw.Model.Colour;
import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Schema;
import it.polimi.ingsw.Model.rules.ColourRule;
import it.polimi.ingsw.Model.rules.InsertionRule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColourRuleTest {
    Schema schema = new Schema();
    Player player = new Player("player 1");
    Dice dice = new Dice(Colour.ANSI_BLUE, 4);
    InsertionRule rule = new ColourRule();

    @Test
    public void correctInsertion() throws IOException {
        schema = schema.schemaInit(24);
        player.setSchema(schema);

        assertTrue(rule.checkRule(player,0,0,0,dice));
        assertTrue(rule.checkRule(player,0,0,1,dice));
        assertTrue(rule.checkRule(player,2,0,2,dice));
    }

    @Test
    public void wrongInsertion() throws IOException {
        schema = schema.schemaInit(24);
        player.setSchema(schema);

        assertFalse(rule.checkRule(player,0,0,2,dice));

    }
}
