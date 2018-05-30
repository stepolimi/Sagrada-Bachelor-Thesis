package it.polimi.ingsw.server.model.game;


import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.serverConnection.MultiSocketServer;
import it.polimi.ingsw.server.serverConnection.RmiServerMethod;
import it.polimi.ingsw.server.serverConnection.RmiServerMethodInterface;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
        ((VirtualView) virtual).setConnection(connection);
        try {
            //System.setSecurityManager(new RMISecurityManager());

           // java.rmi.registry.LocateRegistry.createRegistry(1099);

            RmiServerMethod obj = new  RmiServerMethod((VirtualView)virtual,connection);
            RmiServerMethodInterface stub = (RmiServerMethodInterface) UnicastRemoteObject.exportObject(obj,1099);
            Registry registry = LocateRegistry.createRegistry(1099);

            Naming.rebind("RmiServerMethodInterface", stub);
            // devo passare un oggetto serverMethod a virtualView
            System.out.println("Rmi pronto");

        }catch (Exception e) {
            System.out.println("Errore di connessione: " + e);
        }

        MultiSocketServer s = new MultiSocketServer(1666,(VirtualView)virtual,connection);
        s.StartServer();
    }
}

