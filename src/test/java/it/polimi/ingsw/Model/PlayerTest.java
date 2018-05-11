package it.polimi.ingsw.Model;

import it.polimi.ingsw.Server.Model.cards.PrivateObjective;
import it.polimi.ingsw.Server.Model.board.Player;
import it.polimi.ingsw.Server.Model.board.Schema;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class PlayerTest {

        Player player;
    public void setup1() {
        player = new Player("giocatore 1 ");
        player.setTurn(true);
        player.setConnected(false);

    }

    public void setup2(){
        player = new Player("giocatore 1 ");
        player.setTurn(true);
        player.setConnected(true);
    }

    public Schema schemaInit(int n){
        Schema schema = new Schema();
        try {
            schema = schema.schemaInit(n);
        }catch(Exception e){
            System.out.println(e);
        }
        return schema;
    }
    public PrivateObjective objectiveInit(){
        PrivateObjective objective = new PrivateObjective();
        try{
            objective.PrivateInit(2);
        }catch (Exception e){
            System.out.println(e);
        }
        return objective;
    }

    public boolean correct_player_status(Player p){
        return(!p.isMyTurn() || p.isConnected());
    }

    @Test
    public void wrong_behave(){
        setup1();
        assertFalse("impossibile that it's player's turn and he's disconnected", correct_player_status(player));
    }

    @Test
    public void correct_behave(){
        setup2();
        assertTrue("behavior correct", correct_player_status(player));
    }

    @Test
    public void setAttributes(){
        player = new Player("giocatore 1");
        Schema schema= this.schemaInit(1);
        Schema schema2 = this.schemaInit(2);
        List<Schema> schemas = new ArrayList<Schema>();
        schemas.add(schema);
        schemas.add(schema2);
        player.takeSchema(schemas,1);

        assertTrue(player.getSchema() == schema2);
        assertTrue(player.getFavour() == 5);
        assertTrue(player.getNickname().equals("giocatore 1"));

        player.setFavour(1);
        assertTrue(player.getFavour() == 1);

        PrivateObjective objective = this.objectiveInit();
        player.setObjective(objective);
        assertTrue(player.getPrCard() == objective);

        player.setScore(10);
        assertTrue(player.getScore() == 10);

        assertTrue(player.toString().equals("nickname:giocatore 1\n" + "Schema choosen:Aurorae Magnificus\n" + "score:10\n"));

    }
}


