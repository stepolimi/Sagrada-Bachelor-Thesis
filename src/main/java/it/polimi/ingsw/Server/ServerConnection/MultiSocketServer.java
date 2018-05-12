package it.polimi.ingsw.Server.ServerConnection;

import it.polimi.ingsw.Server.VirtualView.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiSocketServer implements Observer {
    private int port;
    private VirtualView virtual;
    static int playerConnessi;
    private HashMap<SocketConnection,String> sockets =  new HashMap<SocketConnection, String>();
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
                    sock.addObserver(this);
                    sockets.put(sock,"");
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

    public HashMap<SocketConnection,String> getSocket()
    {
        return this.sockets;
    }


    public void update(Observable o, Object arg) {
        SocketConnection s = (SocketConnection) arg;

        if(sockets.get(arg).equals(""))
         sockets.put(s,s.getName());
        else {
            sockets.remove(s);
            System.out.println("Disconnesso"+s.getName());
            this.publish(s.getName()+" si è disconnesso");
        }
    }

    public void publish(String str) {
        if(!sockets.isEmpty()){
            for(SocketConnection sock:sockets.keySet())
                sock.sendMessage(str);
        }
    }
}
