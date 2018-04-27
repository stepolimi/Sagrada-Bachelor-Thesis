package it.polimi.ingsw;

import it.polimi.ingsw.states.Round;

import java.util.ArrayList;
import java.util.List;

public class GameMultiplayer {
    private List<Round> rounds= new ArrayList <Round> (10);
    private List<Player> players= new ArrayList();
    private Board board = new Board(players);

    public GameMultiplayer(){
        players.add(new Player("giocatore 1 ",new Schema()));
        players.add(new Player("giocatore 2 ",new Schema()));
        players.add(new Player("giocatore 3 ",new Schema()));
        players.add(new Player("giocatore 4 ",new Schema()));

        for(int i=0; i < 10 ; i++){
            int firstPlayerIndex = i % players.size();
            rounds.add(new Round(players.get(firstPlayerIndex),board));
            rounds.get(i).setReference(rounds.get(i));
        }
    }

    public void execute(){
        for(Round r: rounds){
            startRound(rounds.indexOf(r));
            System.out.println("fine del rurno: " + (rounds.indexOf(r) + 1));
        }
    }
    public void startRound(int index){
        rounds.get(index).roundInit();
        rounds.get(index).executeRound();

    }

    public Round getRound(int index){
        return rounds.get(index);
    }
}
