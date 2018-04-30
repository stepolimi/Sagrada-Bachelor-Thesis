package it.polimi.ingsw.ruleTest;

import com.google.gson.Gson;
import it.polimi.ingsw.Colour;
import it.polimi.ingsw.Dice;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Schema;
import it.polimi.ingsw.rules.EmptyRule;
import it.polimi.ingsw.rules.InsertionRule;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmptyRuleTest {
    @Test
    public void correctInsertion() {
        Schema schema = new Schema();
        Player player = new Player("player 1");
        Dice dice_1 = new Dice(Colour.ANSI_GREEN, 4);
        Dice dice_2 = new Dice(Colour.ANSI_YELLOW, 3);
        String schemaStr = "{\"name\":\"FulgordelCielo\",\"difficult\":5,\"table\":[[{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false}],[{\"number\":0,\"full\":false},{\"number\":4,\"full\":false},{\"number\":5,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false}],[{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"number\":2,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":5,\"full\":false}],[{\"number\":6,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":3,\"full\":false},{\"number\":1,\"full\":false},{\"number\":0,\"full\":false}]]}\n";
        Gson g = new Gson();
        InsertionRule rule = new EmptyRule();

        schema = g.fromJson(schemaStr, Schema.class);
        schema.insertDice(0,0,dice_1);
        player.setSchema(schema);

        assertTrue(rule.checkRule(player,0,1,0,dice_2));
    }

    @Test
    public void wrongInsertion() {
        Schema schema = new Schema();
        Player player = new Player("player 1");
        Dice dice_1 = new Dice(Colour.ANSI_GREEN, 4);
        Dice dice_2 = new Dice(Colour.ANSI_YELLOW, 3);
        String schemaStr = "{\"name\":\"FulgordelCielo\",\"difficult\":5,\"table\":[[{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false}],[{\"number\":0,\"full\":false},{\"number\":4,\"full\":false},{\"number\":5,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false}],[{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"number\":2,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":5,\"full\":false}],[{\"number\":6,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":3,\"full\":false},{\"number\":1,\"full\":false},{\"number\":0,\"full\":false}]]}\n";
        Gson g = new Gson();
        InsertionRule rule = new EmptyRule();

        schema = g.fromJson(schemaStr, Schema.class);
        schema.insertDice(0,0,dice_1);
        player.setSchema(schema);

        assertFalse(rule.checkRule(player,0,0,0,dice_2));
    }
}

