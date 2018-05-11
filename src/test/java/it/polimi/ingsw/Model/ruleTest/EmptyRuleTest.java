package it.polimi.ingsw.Model.ruleTest;

import it.polimi.ingsw.Server.Model.board.Colour;
import it.polimi.ingsw.Server.Model.board.Dice;
import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.board.Schema;
import it.polimi.ingsw.Server.Model.rules.EmptyRule;
import it.polimi.ingsw.Server.Model.rules.InsertionRule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmptyRuleTest {
    Schema schema = new Schema();
    Player player = new Player("player 1");
    Dice dice_1 = new Dice(Colour.ANSI_GREEN, 4);
    Dice dice_2 = new Dice(Colour.ANSI_YELLOW, 3);
    InsertionRule rule = new EmptyRule();

    @Test
    public void correctInsertion() throws IOException {
        schema = schema.schemaInit(24);
        schema.insertDice(0,0,dice_1);
        player.setSchema(schema);

        assertTrue(rule.checkRule(0,1,0,dice_2, schema));
    }

    @Test
    public void wrongInsertion() throws IOException {
        schema = schema.schemaInit(24);
        schema.insertDice(0,0,dice_1);
        player.setSchema(schema);

        assertFalse(rule.checkRule(0,0,0,dice_2, schema));
    }
}

