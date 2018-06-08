package it.polimi.ingsw.server.model.game.states;

import it.polimi.ingsw.server.model.board.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Random;

public class RoundManager  {
    private Board board;
    private List<Round> rounds;
    private Observer obs;
    private int firstPlayerIndex = 0;
    private int roundNum = 0;
    private Round round ;

    public RoundManager(Board board){
        this.board = board;
        rounds = new ArrayList<Round>();
    }

    public void startNewRound() {
        if(roundNum <=10){
            boolean playerConnected = false;
            do{
                if(board.getPlayerList().get(firstPlayerIndex).isConnected()) {
                    round = new Round(board.getPlayerList().get(firstPlayerIndex), board, this);
                    playerConnected = true;
                }
                if(firstPlayerIndex < board.numPlayers()-1)
                    firstPlayerIndex ++;
                else
                    firstPlayerIndex = 0;
            }while (!playerConnected);

            round.addObserver(obs);
            rounds.add(round);
            round.roundInit();
            roundNum ++;
        }
    }

    public void setFirstPlayer(){
        Random rand = new Random();
        firstPlayerIndex = rand.nextInt(board.numPlayers());
    }

    public int getRoundNumber(){return roundNum;}

    public Round getRound(){ return rounds.get(roundNum - 1);}

    public void setObserver(Observer obs){
        this.obs = obs;
    }
}
