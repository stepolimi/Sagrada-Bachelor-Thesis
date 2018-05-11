package it.polimi.ingsw.Server.ServerConnection;

import it.polimi.ingsw.Server.VirtualView.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiSocketServer  {
    private int port;
    private VirtualView virtual;
    static int playerConnessi;
    private Vector<SocketConnection> sockets =  new Vector<SocketConnection>();
    // ricordarsi di togliere il socket quando viene chiusa la connessione

    public MultiSocketServer(int port,VirtualView virtual)
    {
        this.port = port;
        this.virtual = virtual;
        playerConnessi = 0;

    }

    public void StartServer()
    {
        ExecutorService execute = Executors.newCachedThreadPool();
        ServerSocket serverSocket=null;
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Server ready");
            while(true)
            {
                try
                {
                    Socket socket = serverSocket.accept();
                    System.out.println("Accettato");
                    playerConnessi++;
                    SocketConnection sock = new SocketConnection(socket,virtual);
                    sockets.add(sock);
                    execute.submit(sock);

                }catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }

        }catch(IOException e)
        {
            System.out.println(e.getMessage());
            return;
        }finally {
            try {
                serverSocket.close();
            }catch(IOException ex2){}
        }


    }

    public Vector<SocketConnection> getSocket()
    {
        return this.sockets;
    }
}
