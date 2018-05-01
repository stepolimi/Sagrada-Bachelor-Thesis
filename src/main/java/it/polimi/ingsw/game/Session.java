package it.polimi.ingsw.game;

import it.polimi.ingsw.Player;

import java.util.ArrayList;
import java.util.List;

public class Session {
    List<Player> lobby = new ArrayList<Player>();
    GameMultiplayer game;

    public void newGameMultiplayer(){
        game = new GameMultiplayer(lobby);
    }

    public void joinPlayer(String nickname){
        lobby.add(new Player(nickname));
    }

    public void removePlayer(Player player){
        lobby.remove(player);
    }
}
