package it.polimi.ingsw.server.timer;

import it.polimi.ingsw.server.model.game.states.Round;

import java.util.TimerTask;

import static it.polimi.ingsw.costants.LoginMessages.timerElapsed;
import static it.polimi.ingsw.costants.LoginMessages.timerPing;

public class TurnTimer extends TimerTask{
    private Long startingTime = 0L;
    private int waitTime;
    private Round round;

    public TurnTimer(int waitTime, Round round) {
        this.waitTime = waitTime ;
        this.round = round;
    }

    @Override
    public void run() {
        if(startingTime == 0)
            startingTime = System.currentTimeMillis();
        if (System.currentTimeMillis() < startingTime + waitTime * 1000) {
            round.notifyChanges(timerPing);
        }
        else{
            round.notifyChanges(timerElapsed);
            this.cancel();
        }
    }
}
