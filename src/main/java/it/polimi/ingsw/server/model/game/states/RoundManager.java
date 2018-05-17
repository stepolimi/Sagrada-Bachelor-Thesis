package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.model.board.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class RoundManager  {
    private Board board;
    private List<Round> rounds;
    private Observer obs;
    private int firstPlayerIndex = 0;
    private int roundNum = 0;

    public RoundManager(Board board,Observer obs){
        this.board = board;
        this.obs = obs;
        rounds = new ArrayList<Round>();
    }

    public void startNewRound() {
        Round round ;
        if(roundNum <10){
            round = new Round(board.getPlayerList().get(firstPlayerIndex),board);
            round.addObserver(obs);
            rounds.add(round);
            round.roundInit();
            if(firstPlayerIndex < board.getPlayerList().size())
                firstPlayerIndex ++;
            else
                firstPlayerIndex = 0;
            roundNum ++;
        }
    }

    public int getRoundNumber(){return rounds.size();}

    public Round getRound(){ return rounds.get(rounds.size() - 1);}
}
