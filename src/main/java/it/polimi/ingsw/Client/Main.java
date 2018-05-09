package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.Controller.ControllerClient;
import it.polimi.ingsw.Client.View.View;
// per testare rmi e gli observer
public class Main {
    public static void main(String[] args) {
        ControllerClient ctrlClient = new ControllerClient();
        View v = new View(ctrlClient);
        ctrlClient.initView(v);
    }
}
