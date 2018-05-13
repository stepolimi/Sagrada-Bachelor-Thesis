package it.polimi.ingsw.Server.Model.game;


import it.polimi.ingsw.Server.Controller.ServerController;
import it.polimi.ingsw.Server.ServerConnection.Connected;
import it.polimi.ingsw.Server.ServerConnection.MultiSocketServer;
import it.polimi.ingsw.Server.ServerConnection.RmiServerMethod;
import it.polimi.ingsw.Server.ServerConnection.RmiServerMethodInterface;
import it.polimi.ingsw.Server.VirtualView.VirtualView;

import java.rmi.Naming;
import java.util.Observer;

public class Main {
    public static void main(String[] args){
        // prova funzionamento di Rmi
        // verrà cambiato tutto
        Observer virtual = new VirtualView();
        Session session =new Session();
        ServerController controller = new ServerController(session,(VirtualView)virtual);
        session.setObserver(virtual);
        session.addObserver(virtual);
        ((VirtualView)virtual).addObserver(controller);
        Connected connection = new Connected();
        try {
            //System.setSecurityManager(new RMISecurityManager());
            java.rmi.registry.LocateRegistry.createRegistry(1099);

            RmiServerMethodInterface b = new RmiServerMethod((VirtualView)virtual,connection);
            Naming.rebind("rmi://127.0.0.1/myabc", b);
            // devo passare un oggetto serverMethod a virtualView
            System.out.println("Server connesso");

        }catch (Exception e) {
            System.out.println("Errore di connessione: " + e);
        }

        MultiSocketServer s = new MultiSocketServer(1666,(VirtualView)virtual,connection);
        s.StartServer();
    }
}

