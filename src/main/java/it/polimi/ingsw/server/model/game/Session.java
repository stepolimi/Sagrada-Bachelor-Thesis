package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.timer.TaskTimer;

import java.util.*;

import static it.polimi.ingsw.costants.LoginMessages.*;
import static it.polimi.ingsw.costants.TimerCostants.lobbyTimer;

public class Session extends Observable{
    private List<Player> lobby ;
    private GameMultiplayer game;
    private TaskTimer taskTimer;
    private Timer timer;
    private Observer obs;
    private Long startingTime = 0L;
    private List<String> action ;
    private String player;

    public void setObserver (Observer obs){ this.obs = obs; }

    public void joinPlayer(String player) {
        this.player = player;
        action = new ArrayList<String>();
        if(game == null) {
            if (lobby == null) {
                lobby = new ArrayList<Player>();
            }else
                for(Player p: lobby) {
                    if (p.getNickname().equals(player)) {
                        notify(loginError);
                        return;
                    }
                }
            notify(loginSuccessful);
            lobby.add(new Player(player));
            if(lobby.size() == 2 ) {
                startingTime = System.currentTimeMillis();
                taskTimer = new TaskTimer(lobbyTimer,this);
                timer = new Timer();
                timer.schedule(taskTimer,0L,5000L);
            }
            else if(lobby.size() == 4){
                timer.cancel();
                notify(lobbyFull);
            }
        }
        else {
            notify(loginError);
        }
    }

    public void removePlayer(String player){
        action = new ArrayList<String>();
        if(game == null) {
            for(Player p: lobby)
                if(p.getNickname().equals(player)) {
                    lobby.remove(p);
                    break;
                }
            if(lobby.size() == 1) {
                timer.cancel();
                startingTime = 0L;
            }
        }
        else
            for(Player p: game.getPlayers()){
                if(p.getNickname().equals(player)) {
                    p.setConnected(false);
                }
            }
    }

    public List<Player> getPlayers(){ return this.lobby; }

    public GameMultiplayer getGame() { return game; }

    private void startGame(){
        game = new GameMultiplayer(lobby);
        game.setObserver(obs);
        game.addObserver(obs);
        game.gameInit();
    }

    public void notify(String string){
        if(string.equals(timerElapsed) || string.equals(lobbyFull)) {
            action.clear();
            action.add(startingGameMsg);
            action.add("partita creata");
            setChanged();
            notifyObservers(action);
            startGame();
        }else if(string.equals(timerPing)){
            action.clear();
            action.add(startingGameMsg);
            action.add(((Long)(lobbyTimer - ((System.currentTimeMillis() - startingTime)/1000))).toString());
            setChanged();
            notifyObservers(action);
        }else{
            action.add(string);
            action.add(player);
            setChanged();
            notifyObservers(action);
        }
    }
}
