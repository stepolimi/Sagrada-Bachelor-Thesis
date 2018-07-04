package it.polimi.ingsw.server.model.timer;

import java.util.TimerTask;

public class GameTimer extends TimerTask{
    private Long startingTime = 0L;
    private final int waitTime;
    private final TimedComponent timedComponent;

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
