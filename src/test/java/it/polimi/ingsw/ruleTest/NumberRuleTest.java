package it.polimi.ingsw.ruleTest;

import com.google.gson.Gson;
import it.polimi.ingsw.Colour;
import it.polimi.ingsw.Dice;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Schema;
import it.polimi.ingsw.rules.InsertionRule;
import it.polimi.ingsw.rules.NumberRule;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberRuleTest {

    @Test
    public void correctInsertion() {
        Schema schema = new Schema();
        Player player = new Player("player 1", schema);
        Dice dice = new Dice(Colour.ANSI_GREEN, 4);
        String schemaStr = "{\"name\":\"FulgordelCielo\",\"difficult\":5,\"table\":[[{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false}],[{\"number\":0,\"full\":false},{\"number\":4,\"full\":false},{\"number\":5,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false}],[{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"number\":2,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":5,\"full\":false}],[{\"number\":6,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":3,\"full\":false},{\"number\":1,\"full\":false},{\"number\":0,\"full\":false}]]}\n";
        Gson g = new Gson();
        InsertionRule rule = new NumberRule();

        schema = g.fromJson(schemaStr, Schema.class);

        player.setSchema(schema);

        assertTrue(rule.checkRule(player,0,0,0,dice));
        assertTrue(rule.checkRule(player,0,1,1,dice));
        assertTrue(rule.checkRule(player,3,1,2,dice));
    }

    @Test
    public void wrongInsertion() {
        Schema schema = new Schema();
        Player player = new Player("player 1", schema);
        Dice dice = new Dice(Colour.ANSI_GREEN, 4);
        String schemaStr = "{\"name\":\"FulgordelCielo\",\"difficult\":5,\"table\":[[{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":0,\"full\":false},{\"number\":0,\"full\":false}],[{\"number\":0,\"full\":false},{\"number\":4,\"full\":false},{\"number\":5,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false}],[{\"c\":\"ANSI_BLUE\",\"number\":0,\"full\":false},{\"number\":2,\"full\":false},{\"number\":0,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":5,\"full\":false}],[{\"number\":6,\"full\":false},{\"c\":\"ANSI_RED\",\"number\":0,\"full\":false},{\"number\":3,\"full\":false},{\"number\":1,\"full\":false},{\"number\":0,\"full\":false}]]}\n";
        Gson g = new Gson();
        InsertionRule rule = new NumberRule();

        schema = g.fromJson(schemaStr, Schema.class);

        player.setSchema(schema);

        assertFalse(rule.checkRule(player,0,1,2,dice));

    }


}
