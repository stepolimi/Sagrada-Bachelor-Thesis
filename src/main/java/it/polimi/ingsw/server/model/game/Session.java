package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.timer.TaskTimer;

import java.util.*;

import static it.polimi.ingsw.costants.LoginMessages.*;
import static it.polimi.ingsw.costants.TimerCostants.lobbyTimer;

//session manage the process of game start, players can join/left the lobby before game starts for reaching 4 players or the time limit.
//players that will leave the game after his start will be set as "disconnected" but not removed from the game.

public  class Session extends Observable {
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
            System.out.println("connected\n" + "players in lobby: " + lobby.size() + "\n ---");
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
            System.out.println("connection failed: a game is already running\n" + " ---");
            notify(loginError);
        }
    }

    public void removePlayer(String player){
        this.player = player;
        action = new ArrayList<String>();
        if(game == null) {
            for(int i=0; i<lobby.size(); i++)
                if(lobby.get(i).getNickname().equals(player)) {
                    lobby.remove(i);
                }
            if(lobby.size() == 1) {
                timer.cancel();
                startingTime = 0L;
            }
            System.out.println(player + " disconnected:\n" + "players in lobby: " + lobby.size() + "\n ---");
            notify(logout);
        }
        else {
            for (Player p : game.getPlayers()) {
                if (p.getNickname().equals(player)) {
                    p.setConnected(false);
                    System.out.println(player + " disconnected:\n"+ "players still connected: " + game.getBoard().getConnected() + "\n ---" );
                    notify(logout);
                }
            }
        }
    }

    public List<Player> getPlayers(){ return this.lobby; }

    public GameMultiplayer getGame() { return game; }

    private void startGame(){
        System.out.println("starting game\n" + " ---");
        game = new GameMultiplayer(lobby);
        game.setObserver(obs);
        game.addObserver(obs);
        game.gameInit();
        System.out.println("game started:\n" + "waiting for players to choose their schema\n" + " ---");
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
            action.add(timerPing);
            action.add(((Long)(lobbyTimer - (System.currentTimeMillis() - startingTime)/1000)).toString());
            setChanged();
            notifyObservers(action);
        }else if(string.equals(loginError)){
            action.add(string);
            action.add(player);
            action.add("game");
            setChanged();
            notifyObservers(action);
        }else if(string.equals(logout) || string.equals(loginSuccessful)){
            action.add(string);
            action.add(player);
            setChanged();
            notifyObservers(action);
        }
    }
}
