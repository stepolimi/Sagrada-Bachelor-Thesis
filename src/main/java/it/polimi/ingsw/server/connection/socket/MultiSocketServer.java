package it.polimi.ingsw.server.connection.socket;

import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.connection.Connected;
import it.polimi.ingsw.server.virtual.view.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.LogConstants.MULTI_SOCKET_SERVER_START_SERVER;
import static it.polimi.ingsw.server.costants.LogConstants.SOCKET_READY;

public class MultiSocketServer{
    private final int port;
    private final VirtualView virtual;
    private final Connected connection;
    private boolean loop;

    public MultiSocketServer(int port,VirtualView virtual,Connected connection) {
        this.port = port;
        this.virtual = virtual;
        this.connection = connection;
        this.loop = true;
    }

    /**
     * start server connection with socket
     */
    public void startServer()
    {
        ExecutorService execute = Executors.newCachedThreadPool();
        try(ServerSocket serverSocket = new ServerSocket(port)){
            Log.getLogger().addLog(SOCKET_READY, Level.INFO,this.getClass().getName(),MULTI_SOCKET_SERVER_START_SERVER);
            while(loop) {
                    Socket socket = serverSocket.accept();
                    SocketConnection sock = new SocketConnection(socket,virtual,connection);
                    execute.submit(sock);
            }
        }catch(IOException e) {
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE, this.getClass().getName(), MULTI_SOCKET_SERVER_START_SERVER);
        }
    }
}
