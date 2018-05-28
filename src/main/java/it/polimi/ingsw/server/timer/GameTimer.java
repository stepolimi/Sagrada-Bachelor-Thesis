package it.polimi.ingsw.server.timer;

import java.util.TimerTask;

import static it.polimi.ingsw.costants.LoginMessages.timerElapsed;
import static it.polimi.ingsw.costants.LoginMessages.timerPing;

public class GameTimer extends TimerTask{
    private Long startingTime = 0L;
    private int waitTime;
    private TimedComponent timedComponent;

    public GameTimer(int waitTime, TimedComponent timedComponent) {
        this.waitTime = waitTime ;
        this.timedComponent = timedComponent;
    }

    @Override
    public void run() {
        if(startingTime == 0)
            startingTime = System.currentTimeMillis();
        if (System.currentTimeMillis() < startingTime + waitTime * 1000) {
            timedComponent.notifyChanges(timerPing);
        }
        else{
            timedComponent.notifyChanges(timerPing);
            timedComponent.notifyChanges(timerElapsed);
            this.cancel();
        }
    }
}
