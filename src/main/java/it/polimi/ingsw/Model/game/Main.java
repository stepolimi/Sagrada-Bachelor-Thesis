package it.polimi.ingsw.Model.game;


import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Model.DiceBag;
import it.polimi.ingsw.Model.DiceSpace;
import it.polimi.ingsw.ServerConnection.RmiServerMethod;
import it.polimi.ingsw.ServerConnection.RmiServerMethodInterface;
import it.polimi.ingsw.VirtualView.VirtualView;

import java.rmi.Naming;

public class Main {
    public static void main(String[] args){
        // prova funzionamento di Rmi
        // verrà cambiato tutto
        DiceBag bag = new DiceBag();
        DiceSpace subject = new DiceSpace();
        ServerController controller = new ServerController(subject,bag);
        VirtualView virtual = new VirtualView(subject,bag,controller);
        subject.addObserver(virtual);

        try {
            //System.setSecurityManager(new RMISecurityManager());
            java.rmi.registry.LocateRegistry.createRegistry(1099);

            RmiServerMethodInterface b = new RmiServerMethod(virtual);
            Naming.rebind("rmi://127.0.0.1/myabc", b);
            // devo passare un oggetto serverMethod a virtualView
            virtual.setServer((RmiServerMethod) b);
            System.out.println("Server connesso");

        }catch (Exception e) {
            System.out.println("Errore di connessione: " + e);
        }
    }
}

