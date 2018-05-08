package it.polimi.ingsw.Model.game;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.game.states.Round;
import it.polimi.ingsw.Model.rules.RulesManager;

import java.util.ArrayList;
import java.util.List;

public class GameMultiplayer {
    private RulesManager rulesManager;
    private List<Round> rounds;
    private List<Player> players;
    private Board board;


    public List getRounds(){ return this.rounds; }

    public GameMultiplayer(List <Player> players){
        this.players = new ArrayList<Player>();
        this.players.addAll(players);
        this.rounds = new ArrayList<Round>();
        this.board= new Board(players);

    }
    public Round getRound(int i) { return this.rounds.get(i); }

    public void gameInit(){
        for(int i=0; i < 10 ; i++){
            int firstPlayerIndex = i % players.size();
            rounds.add(new Round(players.get(firstPlayerIndex),board));
            rounds.get(i).setReference(rounds.get(i));
        }
    }

    public void execute(){
        for(Round r: rounds){
            startRound(rounds.indexOf(r));
        }
    }

    public void startRound(int index){
        rounds.get(index).roundInit();
        rounds.get(index).startRound();

    }



}
