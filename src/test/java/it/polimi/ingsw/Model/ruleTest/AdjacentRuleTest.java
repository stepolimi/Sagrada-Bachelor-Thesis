package it.polimi.ingsw.Model.ruleTest;

import it.polimi.ingsw.Model.Colour;
import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Schema;
import it.polimi.ingsw.Model.rules.AdjacentRule;
import it.polimi.ingsw.Model.rules.InsertionRule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdjacentRuleTest {
    Schema schema = new Schema();
    Player player = new Player("player 1");
    Dice dice_1 = new Dice(Colour.ANSI_BLUE, 4);
    Dice dice_2 = new Dice(Colour.ANSI_GREEN, 3);
    Dice dice_3 = new Dice(Colour.ANSI_GREEN, 2);
    Dice dice_4 = new Dice(Colour.ANSI_YELLOW, 3);
    InsertionRule rule = new AdjacentRule();

    @Test
    public void correctInsertion() throws IOException {
        schema = schema.schemaInit(24);
        player.setSchema(schema);

        //test for correct insertion in an empty schema
        assertTrue(rule.checkRule(player,0,0,1,dice_1));
        assertTrue(rule.checkRule(player,0,1,0,dice_1));
        assertTrue(rule.checkRule(player,0,1,4,dice_1));
        assertTrue(rule.checkRule(player,0,3,1,dice_1));

        schema.insertDice(3,3,dice_2);

        //test for correct insertion with a tool card n*9
        assertTrue(rule.checkRule(player,9,0,1,dice_1));
        assertTrue(rule.checkRule(player,9,1,0,dice_1));
        assertTrue(rule.checkRule(player,9,1,4,dice_1));
        assertTrue(rule.checkRule(player,9,3,1,dice_1));

        schema.insertDice(1,3,dice_3);

        //test for correct generic insertion
        assertTrue(rule.checkRule(player,0,3,2,dice_1));
        assertTrue(rule.checkRule(player,0,2,2,dice_1));
        assertTrue(rule.checkRule(player,0,2,3,dice_1));




    }

    @Test
    public void wrongInsertion() throws IOException {
        schema = schema.schemaInit(24);
        player.setSchema(schema);

        //test for wrong insertion in an empty schema
        assertFalse(rule.checkRule(player,0,1,1,dice_1));

        schema.insertDice(0,0,dice_2);

        //test for wrong insertion with a tool card n*9
        assertFalse(rule.checkRule(player,9,0,1,dice_1));
        assertFalse(rule.checkRule(player,9,1,0,dice_1));
        assertFalse(rule.checkRule(player,9,1,2,dice_1));
        assertFalse(rule.checkRule(player,9,2,1,dice_1));

        //test for wrong generic insertion
        assertFalse(rule.checkRule(player,0,0,1,dice_3));
        assertFalse(rule.checkRule(player,0,1,0,dice_3));
        assertFalse(rule.checkRule(player,0,0,1,dice_4));
        assertFalse(rule.checkRule(player,0,1,0,dice_4));
        assertFalse(rule.checkRule(player,0,3,3,dice_1));






    }
}
