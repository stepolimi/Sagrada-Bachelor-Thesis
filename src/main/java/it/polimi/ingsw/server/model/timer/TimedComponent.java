package it.polimi.ingsw.server.model.timer;

public interface TimedComponent {
    void notifyChanges(String string);
    void timerElapsed();
}
