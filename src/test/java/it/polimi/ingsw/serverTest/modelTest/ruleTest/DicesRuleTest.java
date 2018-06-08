package it.polimi.ingsw.serverTest.modelTest.ruleTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.rules.DicesRule;
import it.polimi.ingsw.server.model.rules.InsertionRule;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DicesRuleTest {
    Schema schema = new Schema();
    Player player = new Player("player 1");
    Dice dice_1 = new Dice(Colour.ANSI_BLUE, 4);
    Dice dice_2 = new Dice(Colour.ANSI_GREEN, 3);
    Dice dice_3 = new Dice(Colour.ANSI_GREEN, 2);
    InsertionRule rule = new DicesRule();

    @Test
    public void correctInsertion() throws IOException {
        player.setObserver(new VirtualView());
        schema = schema.schemaInit(24);
        schema.insertDice(3,3,dice_2);
        schema.insertDice(1,3,dice_3);

        //test for correct generic insertion
        assertTrue(rule.checkRule(3,2,dice_1, schema));
        assertTrue(rule.checkRule(2,2,dice_1, schema));
        assertTrue(rule.checkRule(2,3,dice_1, schema));
    }

    @Test
    public void wrongInsertion() throws IOException {
        player.setObserver(new VirtualView());
        schema = schema.schemaInit(24);

        schema.insertDice(0,0,dice_2);

        //test for wrong generic insertion
        assertFalse(rule.checkRule(0,1,dice_3, schema));
        assertFalse(rule.checkRule(1,0,dice_3, schema));
    }
}
