package it.polimi.ingsw.Server.ServerConnection;

import it.polimi.ingsw.Server.VirtualView.VirtualView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class SocketConnection extends Observable implements Runnable {
    Socket s;
    VirtualView virtual;
    Scanner in;
    PrintWriter out;
    private String name;
    public SocketConnection(Socket s,VirtualView virtual)
    {
        this.s = s;
        this.virtual = virtual;
    }

    public void InviaInfo(String str)
    {
        out.println(str);
        out.flush();
    }

    public void run() {
        try {
            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
            while(true) {
                String str = in.nextLine();
                if(str.equals("Disconnected")) {
                        this.logout();
                        break;
                    }else if(str.equals("Login")){
                    this.login(str);
                }else{
                    // verrà sostituito sicuramente con Json
                    ArrayList<String> action = new ArrayList<String>();
                    action.add(str);
                    this.forwardAction(action);
                    System.out.println("Mi hai scritto:"+str);
                }
            }
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }


    }

    public void login(String str) {

        this.name = in.nextLine();
        setChanged();
        notifyObservers(this);
        this.sendMessage("Welcome "+this.name);
        System.out.println(this.name+" si è loggato");
    }

    public void logout() {
        try {
            in.close();
            out.close();
            s.close();
            setChanged();
            notifyObservers(this);
        }catch(IOException io)
        {
            System.out.println(io.getMessage());
        }
    }


    public void sendMessage(String str) {
        out.println(str);
        out.flush();
    }

    public void forwardAction(ArrayList action) {
        virtual.forwardAction(action);
    }

    public String getName(){return this.name;}

}
