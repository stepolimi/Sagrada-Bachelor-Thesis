package it.polimi.ingsw.game;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.GameMultiplayer;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private List<Player> lobby;
    private GameMultiplayer game;

    public Session(List<Player> playerList){
        this.game = new GameMultiplayer(playerList);
        this.lobby = new ArrayList<Player>(playerList);
    }

    public void joinPlayer(String player){
        lobby.add(new Player(player));
    }

    public void removePlayer(String player){
        lobby.remove(player);
    }

    public List getPlayers(){ return this.lobby; }
}
