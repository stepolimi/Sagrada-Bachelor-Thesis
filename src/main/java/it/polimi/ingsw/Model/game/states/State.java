package it.polimi.ingsw.Model.game.states;

public abstract class State {
    private String state;
    private String next;

    /*idea di funzionamento: la execute dei diversi stati notificherà alla virtual view i pulsanti/movimenti/azioni che
     saranno disponibili all'utente in quella data situazione, la quale manderà le informazioni alla view (o notificherà
     direttamente alla view)
     */
    public void setActions(Round round){}

    public void execute(Round round, String action){}

    public String nextState(Round round, String action){
        return action + "State";
    }

    @Override
    public String toString (){return state; }

}
