package it.polimi.ingsw.Model.ruleTest;

import it.polimi.ingsw.Model.Colour;
import it.polimi.ingsw.Model.Dice;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Schema;
import it.polimi.ingsw.Model.rules.RulesManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RulesManagerTest {
    Schema schema = new Schema();
    Player player = new Player("player 1");
    Dice dice_1 = new Dice(Colour.ANSI_BLUE, 4);
    Dice dice_2 = new Dice(Colour.ANSI_GREEN, 3);
    Dice dice_3 = new Dice(Colour.ANSI_GREEN, 4);
    Dice dice_4 = new Dice(Colour.ANSI_BLUE, 3);
    RulesManager manager = new RulesManager();

    @Test
    public void correctInsertion() throws IOException {
        schema = schema.schemaInit(24);
        player.setSchema(schema);

        assertTrue(manager.checkRules(player,0,0,0,dice_1));
    }

    @Test
    public void wrongInsertion() throws IOException {
        schema = schema.schemaInit(24);
        player.setSchema(schema);
        schema.insertDice(0,0,dice_1);

        //wrong for EmptyRule
        assertFalse(manager.checkRules(player,0,0,0,dice_2));

        //wrong for NumberRule
        assertFalse(manager.checkRules(player,0,0,1,dice_3));

        //wrong for ColourRule
        assertFalse(manager.checkRules(player,0,0,1,dice_4));

        //wrong for AdjacentRule
        assertFalse(manager.checkRules(player,0,0,2,dice_2));
    }


}
