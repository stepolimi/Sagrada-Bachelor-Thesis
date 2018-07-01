package it.polimi.ingsw.server.model.game;


import it.polimi.ingsw.server.Log.Log;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.serverConnection.Connected;
import it.polimi.ingsw.server.serverConnection.socket.MultiSocketServer;
import it.polimi.ingsw.server.serverConnection.rmi.RmiServerMethod;
import it.polimi.ingsw.server.serverConnection.rmi.RmiServerMethodInterface;
import it.polimi.ingsw.server.setUp.TakeDataFile;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observer;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.NameCostants.RMI_PORT;
import static it.polimi.ingsw.server.costants.NameCostants.SOCKET_PORT;
import static it.polimi.ingsw.server.costants.SetupCostants.CONFIGURATION_FILE;

public class Main {
    public static void main(String[] args){
        int rmiPort,socketPort;
        TakeDataFile netConfig = new TakeDataFile(CONFIGURATION_FILE);
        Observer virtual = new VirtualView();
        Session session = new Session();
        ServerController controller = new ServerController(session,(VirtualView)virtual);
        session.setObserver(virtual);
        session.addObserver(virtual);
        ((VirtualView)virtual).addObserver(controller);
        Connected connection = new Connected();
        ((VirtualView) virtual).setConnection(connection);
        rmiPort = Integer.parseInt(netConfig.getParameter(RMI_PORT));
        socketPort = Integer.parseInt(netConfig.getParameter(SOCKET_PORT));



        try {
            RmiServerMethod obj = new  RmiServerMethod((VirtualView)virtual,connection);
            RmiServerMethodInterface stub = (RmiServerMethodInterface) UnicastRemoteObject.exportObject(obj,rmiPort);
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            Naming.rebind("RmiServerMethodInterface", stub);
            Log.getLogger().addLog("Rmi ready", Level.INFO,"Main","Main");

        }catch (Exception e) {
            Log.getLogger().addLog("Connection error: " + e,Level.SEVERE,"Main","Main");
        }

        MultiSocketServer s = new MultiSocketServer(socketPort,(VirtualView)virtual,connection);
        s.startServer();
    }
}

