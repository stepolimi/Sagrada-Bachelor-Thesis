package it.polimi.ingsw.server.model.game;


import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.controller.ServerController;
import it.polimi.ingsw.server.connection.Connected;
import it.polimi.ingsw.server.connection.socket.MultiSocketServer;
import it.polimi.ingsw.server.connection.rmi.RmiServerMethod;
import it.polimi.ingsw.server.connection.rmi.RmiServerMethodInterface;
import it.polimi.ingsw.server.set.up.TakeDataFile;
import it.polimi.ingsw.server.virtual.view.VirtualView;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observer;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.LogConstants.CONNECTION_ERROR;
import static it.polimi.ingsw.server.costants.LogConstants.MAIN_MAIN;
import static it.polimi.ingsw.server.costants.LogConstants.RMI_READY;
import static it.polimi.ingsw.server.costants.NameConstants.RMI_PORT;
import static it.polimi.ingsw.server.costants.NameConstants.SOCKET_PORT;
import static it.polimi.ingsw.server.costants.SetupConstants.CONFIGURATION_FILE;

public class Main {
    public static void main(String[] args){
        int rmiPort;
        int socketPort;
        TakeDataFile netConfig = new TakeDataFile(CONFIGURATION_FILE);
        Observer virtual = VirtualView.getVirtualView();
        Session session = Session.getSession();
        ServerController controller = ServerController.getServerController();
        session.addObserver(virtual);
        ((VirtualView)virtual).addObserver(controller);
        Connected connection = Connected.getConnected();
        rmiPort = Integer.parseInt(netConfig.getParameter(RMI_PORT));
        socketPort = Integer.parseInt(netConfig.getParameter(SOCKET_PORT));

        try {
            RmiServerMethod obj = new  RmiServerMethod((VirtualView)virtual,connection);
            RmiServerMethodInterface stub = (RmiServerMethodInterface) UnicastRemoteObject.exportObject(obj,rmiPort);
            LocateRegistry.createRegistry(rmiPort);
            Naming.rebind("RmiServerMethodInterface", stub);
            Log.getLogger().addLog(RMI_READY, Level.INFO, Main.class.getName(),MAIN_MAIN);

        }catch (Exception e) {
            Log.getLogger().addLog(CONNECTION_ERROR + e, Level.SEVERE, Main.class.getName(),MAIN_MAIN);
        }

        MultiSocketServer s = new MultiSocketServer(socketPort,(VirtualView)virtual,connection);
        s.startServer();
    }
}

