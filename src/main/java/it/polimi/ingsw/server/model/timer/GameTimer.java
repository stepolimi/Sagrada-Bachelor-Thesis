package it.polimi.ingsw.server.model.timer;

import java.util.TimerTask;

import static it.polimi.ingsw.server.costants.MessageConstants.TIMER_PING;

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
            timedComponent.timerPing();
        }
        else{
            timedComponent.timerPing();
            timedComponent.timerElapsed();
            this.cancel();
        }
    }
}
