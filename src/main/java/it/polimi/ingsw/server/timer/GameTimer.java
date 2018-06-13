package it.polimi.ingsw.server.timer;

import java.util.TimerTask;

import static it.polimi.ingsw.costants.LoginMessages.TIMER_ELAPSED;
import static it.polimi.ingsw.costants.LoginMessages.TIMER_PING;

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
            timedComponent.notifyChanges(TIMER_PING);
        }
        else{
            timedComponent.notifyChanges(TIMER_PING);
            timedComponent.timerElapsed();
            this.cancel();
        }
    }
}
