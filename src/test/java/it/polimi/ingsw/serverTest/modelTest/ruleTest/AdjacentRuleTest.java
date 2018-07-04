package it.polimi.ingsw.serverTest.modelTest.ruleTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.rules.AdjacentRule;
import it.polimi.ingsw.server.model.rules.InsertionRule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdjacentRuleTest {
    private Schema schema = new Schema();
    private Dice dice_1 = new Dice(Colour.ANSI_BLUE, 4);
    private Dice dice_2 = new Dice(Colour.ANSI_GREEN, 3);
    private Dice dice_3 = new Dice(Colour.ANSI_GREEN, 2);
    private Dice dice_4 = new Dice(Colour.ANSI_YELLOW, 3);
    private InsertionRule rule = new AdjacentRule();

    @Test
    void correctInsertion(){
        schema = buildSchema(24);

        //test for correct insertion in an empty schema
        assertTrue(rule.checkRule(0,1,dice_1, schema));
        assertTrue(rule.checkRule(1,0,dice_1, schema));
        assertTrue(rule.checkRule(1,4,dice_1, schema));
        assertTrue(rule.checkRule(3,1,dice_1, schema));

        schema.silentInsertDice(3,3,dice_2);
        schema.silentInsertDice(1,3,dice_3);

        //test for correct generic insertion
        assertTrue(rule.checkRule(3,2,dice_1, schema));
        assertTrue(rule.checkRule(2,2,dice_1, schema));
        assertTrue(rule.checkRule(2,3,dice_1, schema));
    }

    @Test
    void wrongInsertion(){
        schema = buildSchema(24);

        //test for wrong insertion in an empty schema
        assertFalse(rule.checkRule(1,1,dice_1, schema));

        schema.silentInsertDice(0,0,dice_2);

        //test for wrong generic insertion
        assertFalse(rule.checkRule(0,2,dice_4, schema));
        assertFalse(rule.checkRule(2,0,dice_4, schema));
        assertFalse(rule.checkRule(3,3,dice_1, schema));

    }
}
