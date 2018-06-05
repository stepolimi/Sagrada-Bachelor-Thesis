package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.server.virtualView.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiSocketServer{
    private int port;
    private VirtualView virtual;
    private Connected connection;
    // ricordarsi di togliere il socket quando viene chiusa la connessione

    public MultiSocketServer(int port,VirtualView virtual,Connected connection)
    {
        this.port = port;
        this.virtual = virtual;
        this.connection = connection;
    }

    public void StartServer()
    {
        ExecutorService execute = Executors.newCachedThreadPool();
        ServerSocket serverSocket=null;
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Socket pronto");
            while(true)
            {
                try
                {
                    Socket socket = serverSocket.accept();
                    SocketConnection sock = new SocketConnection(socket,virtual,connection);
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
}
