package it.polimi.ingsw.Server.ServerConnection;

import it.polimi.ingsw.Server.VirtualView.VirtualView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class SocketConnection implements Runnable,Connection {
    Socket s;
    VirtualView virtual;
    Scanner in;
    PrintWriter out;
    Connected connection;
    private String name;
    public SocketConnection(Socket s,VirtualView virtual,Connected connection)
    {
        this.s = s;
        this.virtual = virtual;
        this.connection = connection;
    }


    public void run() {
        try {
            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
            while(true) {
                String str = in.nextLine();
                System.out.println(str);
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
        if(connection.checkUsername(this.name))
        {
            connection.getUsers().put(this,this.name);
            this.sendMessage("Welcome");
            System.out.println(this.name+" si è loggato");
        }else
            this.sendMessage("Login_error");

    }

    public void logout() {
        try {
            in.close();
            out.close();
            s.close();
            connection.sendMessage(this.name+" si è scollegato");
            connection.remove(this);
            connection.sendMessage("Numero utenti ancora connessi:"+connection.nConnection());
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
