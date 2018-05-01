package it.polimi.ingsw.game;

import it.polimi.ingsw.Board;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.rules.RulesManager;
import it.polimi.ingsw.game.states.Round;

import java.util.ArrayList;
import java.util.List;

public class GameMultiplayer {
    private RulesManager rulesManager= new RulesManager();
    private List<Round> rounds= new ArrayList <Round> (10);
    private List<Player> players;
    private Board board = new Board(players);

    public GameMultiplayer(List <Player> players){ this.players = players; }

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
