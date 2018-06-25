package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.states.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Random;

public class RoundManager  {
    private Board board;
    private GameMultiplayer game;
    private List<Round> rounds;
    private Observer obs;
    private int firstPlayerIndex = 0;
    private int roundNum = 0;
    private Round round ;

    public RoundManager(Board board, GameMultiplayer game){
        this.board = board;
        this.game = game;
        rounds = new ArrayList<>();
    }

    public void startNewRound() {
        if(roundNum <10){
            boolean playerConnected = false;
            do{
                if(board.getPlayerList().get(firstPlayerIndex).isConnected()) {
                    round = new Round(board.getPlayerList().get(firstPlayerIndex), board, this, game );
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
