package it.polimi.ingsw.Server.Model.game;

import it.polimi.ingsw.Server.Model.game.states.Round;
import it.polimi.ingsw.Server.Model.rules.RulesManager;
import it.polimi.ingsw.Server.Model.board.Board;
import it.polimi.ingsw.Server.Model.board.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GameMultiplayer extends Observable {
    private RulesManager rulesManager;
    private List<Round> rounds;
    private List<Player> players;
    private Board board;
    private Observer obs;


    public List getRounds(){ return this.rounds; }

    public GameMultiplayer( List <Player> players){
        this.rulesManager = new RulesManager();
        this.players = new ArrayList<Player>();
        this.players.addAll(players);
        this.board= new Board(players);
        this.rounds = new ArrayList<Round>();

    }
    public void setObserver(Observer obs){
        this.obs = obs;
        board.addObserver(obs);
        board.setObserver(obs);
    }

    public Round getRound(int i) { return this.rounds.get(i); }

    public void gameInit(){
        int firstPlayerIndex ;
        //devo trovare un modo giusto per creare e passare gli schemi

        for(Player p: players) {
            p.addObserver(obs);
            p.setObjective(board.getDeckpriv().extract());
        }

        for(int i=0; i < 10 ; i++){
            if(i < players.size())
                firstPlayerIndex = i;
            else
                firstPlayerIndex = 0;
            rounds.add(new Round(players.get(firstPlayerIndex),board));
        }
    }

    public void execute(){
        for(Round r: rounds){
            rounds.get(rounds.indexOf(r)).roundInit();
            rounds.get(rounds.indexOf(r)).startRound();
        }
    }

    public List<Player> getPlayers() { return players; }

    public Board getBoard() { return board; }

    public RulesManager getRulesManager() { return rulesManager; }
}
