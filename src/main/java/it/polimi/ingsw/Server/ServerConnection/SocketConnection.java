package it.polimi.ingsw.Server.ServerConnection;

import it.polimi.ingsw.Server.VirtualView.VirtualView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SocketConnection implements Runnable,Connection {
    Socket s;
    VirtualView virtual;
    Scanner in;
    PrintWriter out;
    Connected connection;
    ArrayList action= new ArrayList();
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
                StringTokenizer token = new StringTokenizer(str, "-");
                while(token.hasMoreTokens())
                    action.add(token.nextToken());
                System.out.println(str);
                if(action.get(0).equals("Disconnected")) {
                        this.logout();
                        break;
                    }else if(action.get(0).equals("Login")){
                    this.login((String)action.get(1));
                }else{

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


        if(connection.checkUsername(str))
        {
            connection.getUsers().put(this,str);
            this.sendMessage("Welcome");
            System.out.println(str +" si è loggato");
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
