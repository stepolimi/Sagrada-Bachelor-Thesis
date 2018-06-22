package it.polimi.ingsw.serverTest.modelTest.ruleTest;

import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Dice;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.model.rules.RulesManager;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.builders.SchemaBuilder.buildSchema;
import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RulesManagerTest {
    private Schema schema;
    private Player player = new Player("player 1");
    private Dice dice_1 = new Dice(Colour.ANSI_BLUE, 4);
    private Dice dice_2 = new Dice(Colour.ANSI_GREEN, 3);
    private Dice dice_3 = new Dice(Colour.ANSI_GREEN, 4);
    private Dice dice_4 = new Dice(Colour.ANSI_BLUE, 3);
    private List<Schema> schemas = new ArrayList<Schema>();

    @Test
    public void correctInsertion() throws IOException {
        player.setObserver(new VirtualView());
        schema = buildSchema(24);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());

        assertTrue(RulesManager.getRulesManager().checkRules(null,0,0,dice_1, schema));
    }

    @Test
    public void wrongInsertion() throws IOException {
        player.setObserver(new VirtualView());
        schema = buildSchema(24);
        schemas.add(schema);
        player.setSchemas(schemas);
        player.setSchema(schema.getName());
        schema.silentInsertDice(0,0,dice_1);

        //wrong for EmptyRule
        assertFalse(RulesManager.getRulesManager().checkRules(null,0,0,dice_2, schema));

        //wrong for NumberRule
        assertFalse(RulesManager.getRulesManager().checkRules(null,0,1,dice_3, schema));

        //wrong for ColourRule
        assertFalse(RulesManager.getRulesManager().checkRules(null,0,1,dice_4, schema));

        //wrong for AdjacentRule
        assertFalse(RulesManager.getRulesManager().checkRules(null,0,2,dice_2, schema));
    }


}
