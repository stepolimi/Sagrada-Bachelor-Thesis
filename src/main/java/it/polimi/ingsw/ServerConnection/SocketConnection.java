package it.polimi.ingsw.ServerConnection;

import it.polimi.ingsw.VirtualView.VirtualView;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class SocketConnection implements Runnable {
    Socket s;
    VirtualView virtual;
    Scanner in;
    PrintWriter out;
    public SocketConnection(Socket s,VirtualView virtual)
    {
        this.s = s;
        this.virtual = virtual;
    }

    public void InviaInfo(String str)
    {
        out.println("Ricevuto: "+str);
        out.flush();
    }

    public void run() {
        try {
            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
            while(true) {
                String str = in.nextLine();
                if(str.equals("quit")) {
                    System.out.println("Finito");
                    break;
                }else{
                    // verrà sostituito sicuramente con Json
                    ArrayList<String> action = new ArrayList<String>();
                    action.add(str);
                    virtual.forwardAction(action);
                    System.out.println("Mi hai scritto:"+str);
                }
            }
            in.close();
            out.close();
            s.close();

        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
