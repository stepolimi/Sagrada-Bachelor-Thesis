package it.polimi.ingsw.Server.Model.game;

import exception.GameRunningException;
import exception.NotValidNicknameException;
import it.polimi.ingsw.Server.Model.board.Player;

import java.util.*;

public class Session extends Observable{
    private List<Player> lobby ;
    private GameMultiplayer game;
    private int waitTime = 60;
    private TimerTask timer;
    private Observer obs;
    private Long expected = new Long(0);

    public void setObserver (Observer obs){ this.obs = obs; }

    public void joinPlayer(String player) throws GameRunningException,NotValidNicknameException{
        if(game == null) {
            if (lobby == null) {
                lobby = new ArrayList<Player>();
            }
            for(Player p: lobby)
                if(p.getNickname().equals(player))
                    throw new NotValidNicknameException();
            lobby.add(new Player(player));
            if(lobby.size() == 2 || lobby.size() == 3 ) {
                timer = new TimerTask() {
                    @Override
                    public void run() {
                        if(expected.intValue() == 0)
                            expected = System.currentTimeMillis();
                        while(System.currentTimeMillis() < expected + waitTime*1000){
                            setChanged();
                            notifyObservers(((Long)(System.currentTimeMillis() - expected)).toString());
                        }
                        startGame();
                        setChanged();
                        notifyObservers("partita creata: tempo scaduto");
                        return ;
                    }
                };
                Thread t = new Thread(timer);
                t.start();
            }else if(lobby.size() == 3)
                ;
            else if(lobby.size() == 4){
                timer.cancel();
                startGame();
                setChanged();
                notifyObservers("partita creata: limite giocatori raggiunto");
            }
            setChanged();
            notifyObservers(player + " è entrato nella lobby");
        }
        else
            throw new GameRunningException();
    }

    public void removePlayer(String player) throws GameRunningException{
        if(game == null) {
            for(Player p: lobby)
                if(p.getNickname().equals(player)) {
                    lobby.remove(p);
                    break;
                }
            setChanged();
            notifyObservers(player + " è uscito dalla lobby");
            if(lobby.size() == 1) {
                timer.cancel();
                expected = new Long(0);
            }

        }
        else
            throw new GameRunningException();
    }

    public List getPlayers(){ return this.lobby; }

    public GameMultiplayer getGame() { return game; }

    private void startGame(){
        game = new GameMultiplayer(lobby);
        game.setObserver(obs);
        game.addObserver(obs);
        game.gameInit();
    }
}
