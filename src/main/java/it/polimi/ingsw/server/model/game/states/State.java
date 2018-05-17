package it.polimi.ingsw.server.model.game.states;

import java.util.List;

public interface State {

    /*idea di funzionamento: la execute dei diversi stati notificherà alla virtual View i pulsanti/movimenti/azioni che
     saranno disponibili all'utente in quella data situazione, la quale manderà le informazioni alla View (o notificherà
     direttamente alla View)
     */

    public void execute(Round round, List action);

    public String nextState(Round round, List action);

}
