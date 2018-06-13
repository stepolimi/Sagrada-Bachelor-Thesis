package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.costants.TimerCostants;
import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.timer.GameTimer;
import it.polimi.ingsw.server.timer.TimedComponent;

import java.util.*;

import static it.polimi.ingsw.costants.LoginMessages.*;
import static it.polimi.ingsw.server.serverCostants.Constants.MAX_PLAYERS;
import static it.polimi.ingsw.server.serverCostants.Constants.MIN_PLAYERS;

//session manage the process of game start, players can join/left the lobby before game starts for reaching 4 players or the time limit.
//players that will leave the game after his start will be set as "disconnected" but not removed from the game.

public  class Session extends Observable implements TimedComponent {
    private List<Player> lobby ;
    private GameMultiplayer game;
    private GameTimer lobbyTimer;
    private Timer timer;
    private Long startingTime = 0L;
    private Observer obs;
    private String player;

    public void setObserver (Observer obs){ this.obs = obs; }

    public void joinPlayer(String player) {
        this.player = player;
        if(game == null) {
            if (lobby == null) {
                lobby = new ArrayList<Player>();
            }else
                for(Player p: lobby) {
                    if (p.getNickname().equals(player)) {
                        notifyChanges(LOGIN_ERROR);
                        return;
                    }
                }
            notifyChanges(LOGIN_SUCCESSFUL);
            lobby.add(new Player(player));
            System.out.println("connected\n" + "players in lobby: " + lobby.size() + "\n ---");
            if(lobby.size() == MIN_PLAYERS ) {
                startingTime = System.currentTimeMillis();
                lobbyTimer = new GameTimer(TimerCostants.LOBBY_TIMER_VALUE,this);
                timer = new Timer();
                timer.schedule(lobbyTimer,0L,5000L);
            }
            else if(lobby.size() == MAX_PLAYERS){
                timer.cancel();
                notifyChanges(LOBBY_FULL);
                startGame();
            }
        }
        else {
            for(Player p: game.getPlayers()){
                if(p.getNickname().equals(player)){
                    game.reconnectPlayer(p);
                    notifyChanges(WELCOME_BACK);
                    return ;
                }
            }
            System.out.println("connection failed: a game is already running\n" + " ---");
            notifyChanges(LOGIN_ERROR);
        }
    }

    public void removePlayer(String player){
        this.player = player;
        if(game == null) {
            for(int i=0; i<lobby.size(); i++)
                if(lobby.get(i).getNickname().equals(player)) {
                    lobby.remove(i);
                }
            if(lobby.size() == MIN_PLAYERS-1) {
                timer.cancel();
                startingTime = 0L;
            }
            System.out.println(player + " disconnected:\n" + "players in lobby: \n" + lobby.size() + " ---");
            notifyChanges(LOGOUT);
        }
        else {
            for (Player p : game.getPlayers()) {
                if (p.getNickname().equals(player)) {
                    p.setConnected(false);
                    System.out.println(player + " disconnected:\n"+ "players still connected: " + game.getBoard().getConnected() + "\n ---" );
                    notifyChanges(LOGOUT);
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

    public void timerElapsed() {
        notifyChanges(TIMER_ELAPSED);
        startGame();

    }
    public void notifyChanges(String string){
        List<String> action = new ArrayList<String>();

        if(string.equals(TIMER_ELAPSED) || string.equals(LOBBY_FULL)) {
            action.add(STARTING_GAME_MSG);
            action.add("partita creata");
        }else if(string.equals(TIMER_PING)){
            action.add(TIMER_PING);
            action.add(((Long)(TimerCostants.LOBBY_TIMER_VALUE - (System.currentTimeMillis() - startingTime)/1000)).toString());
        }else if(string.equals(LOGIN_ERROR)){
            action.add(string);
            action.add(player);
            action.add("game");
        }else if(string.equals(LOGOUT) ) {
            action.add(string);
            action.add(player);
        }else if(string.equals(LOGIN_SUCCESSFUL)){
            action.add(string);
            action.add(player);
            action.add(((Integer)lobby.size()).toString());
        }else if(string.equals(WELCOME_BACK)){
            action.add(string);
            action.add(player);
        }
        setChanged();
        notifyObservers(action);
    }


}
