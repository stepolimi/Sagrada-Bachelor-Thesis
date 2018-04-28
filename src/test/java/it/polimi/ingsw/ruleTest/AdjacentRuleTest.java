package it.polimi.ingsw.ruleTest;

import com.google.gson.Gson;
import it.polimi.ingsw.Colour;
import it.polimi.ingsw.Dice;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Schema;
import it.polimi.ingsw.rules.AdjacentRule;
import it.polimi.ingsw.rules.InsertionRule;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdjacentRuleTest {
    @Test
    public void correctInsertion() {
        Schema schema = new Schema();
        Player player = new Player("player 1", schema);
        Dice dice_1 = new Dice(Colour.ANSI_BLUE, 4);
        Dice dice_2 = new Dice(Colour.ANSI_GREEN, 3);
        Dice dice_3 = new Dice(Colour.ANSI_GREEN, 2);
        String schemaStr = "{\"name\":\"FulgordelCielo\",\"difficult\":5,\"table\":[[{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false}],[{\"number\":0,\"full\":false},{\"number\":4,\"full\":false},{\"number\":5,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false}],[{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"number\":2,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":5,\"full\":false}],[{\"number\":6,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":3,\"full\":false},{\"number\":1,\"full\":false},{\"number\":0,\"full\":false}]]}\n";
        Gson g = new Gson();
        InsertionRule rule = new AdjacentRule();

        schema = g.fromJson(schemaStr, Schema.class);

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
    public void wrongInsertion() {
        Schema schema = new Schema();
        Player player = new Player("player 1", schema);
        Dice dice_1 = new Dice(Colour.ANSI_BLUE, 4);
        Dice dice_2 = new Dice(Colour.ANSI_GREEN, 3);
        Dice dice_3 = new Dice(Colour.ANSI_GREEN, 2);
        Dice dice_4 = new Dice(Colour.ANSI_YELLOW, 3);
        String schemaStr = "{\"name\":\"FulgordelCielo\",\"difficult\":5,\"table\":[[{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false}],[{\"number\":0,\"full\":false},{\"number\":4,\"full\":false},{\"number\":5,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false}],[{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"number\":2,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":5,\"full\":false}],[{\"number\":6,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":3,\"full\":false},{\"number\":1,\"full\":false},{\"number\":0,\"full\":false}]]}\n";
        Gson g = new Gson();
        InsertionRule rule = new AdjacentRule();

        schema = g.fromJson(schemaStr, Schema.class);

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
