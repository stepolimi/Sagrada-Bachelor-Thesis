package it.polimi.ingsw.serverTest.modelTest.boardTest;

import it.polimi.ingsw.server.model.cards.PrivateObjective;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.virtualView.VirtualView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.server.model.builders.PrivateObjectiveBuilder.buildPrivateObjective;
import static it.polimi.ingsw.server.model.builders.SchemaBuilder.buildSchema;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


class PlayerTest {
    Player player;

    private void setup1() {
        player = new Player("giocatore 1 ");
        player.setTurn(true);
        player.setConnected(false);

    }

    private void setup2(){
        player = new Player("giocatore 1 ");
        player.setTurn(true);
        player.setConnected(true);
    }

    private Schema schemaInit(int n){
        Schema schema = new Schema();
        try {
            schema = buildSchema(n);
        }catch(Exception e){
            System.out.println(e);
        }
        return schema;
    }
    private PrivateObjective objectiveInit(){
        return buildPrivateObjective(2);
    }

    private boolean correct_player_status(Player p){
        return(!p.isMyTurn() || p.isConnected());
    }

    @Test
    void wrong_behave(){
        setup1();
        assertFalse("impossibile that it's player's turn and he's disconnected", correct_player_status(player));
    }

    @Test
    void correct_behave(){
        setup2();
        assertTrue("behavior correct", correct_player_status(player));
    }

    @Test
    void setAttributes(){
        player = new Player("giocatore 1");
        Schema schema= this.schemaInit(1);
        Schema schema2 = this.schemaInit(2);
        List<Schema> schemas = new ArrayList<Schema>();
        schemas.add(schema);
        schemas.add(schema2);
        player.setSchemas(schemas);
        player.setObserver(new VirtualView());
        player.setSchema(schema2.getName());

        assertTrue(player.getSchema() == schema2);
        assertTrue(player.getFavour() == 5);
        assertTrue(player.getNickname().equals("giocatore 1"));

        player.setFavour(1);
        assertTrue(player.getFavour() == 1);

        PrivateObjective objective = this.objectiveInit();
        player.setPrCard(objective);
        assertTrue(player.getPrCard() == objective);

        player.setScore(10);
        assertTrue(player.getScore() == 10);

        assertTrue(player.toString().equals("nickname:giocatore 1\n" + "Schema choosen:Aurorae Magnificus\n" + "score:10\n"));

    }
}


