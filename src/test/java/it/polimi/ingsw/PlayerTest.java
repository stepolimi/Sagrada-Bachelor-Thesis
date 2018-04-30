package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

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
}


