package it.polimi.ingsw.states;

public abstract class State {
    private String next;

    /*idea di funzionamento: la execute dei diversi stati notificherà alla virtual view i pulsanti/movimenti/azioni che
     saranno disponibili all'utente in quella data situazione, la quale manderà le informazioni alla view (o notificherà
     direttamente alla view)
     */
    public void execute(Round round){}

    public String nextState(Round round){
        return next;
    }

}
