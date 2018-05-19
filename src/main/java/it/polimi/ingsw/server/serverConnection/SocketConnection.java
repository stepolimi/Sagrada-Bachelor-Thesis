package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.server.virtualView.VirtualView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import static it.polimi.ingsw.costants.LoginMessages.loginError;

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
                    this.logout(action);
                }else if(action.get(0).equals("Login")) {
                    this.login((String) action.get(1));
                }
                this.forwardAction(action);
                System.out.println("Mi hai scritto:"+str);
            }
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public boolean login(String str) {
        if(connection.checkUsername(str)) {
            this.name = str;
            connection.getUsers().put(this,str);
            System.out.println(str +" si è loggato");
            return true;
        }else{
            action.clear();
            action.add(loginError);
            this.sendMessage(action);
            return false;
        }
    }

    public void logout(List action) {
        try {
            in.close();
            out.close();
            s.close();
            connection.remove(this);
        }catch(IOException io)
        {
            System.out.println(io.getMessage());
        }
    }


    public void sendMessage(List action) {
        String message = new String();
        for(Object o: action){
            message = message + "-" + o;
        }
        out.println(message);
        out.flush();
    }

    public void forwardAction(ArrayList action) {
        virtual.forwardAction(action);
    }

    public String getName(){return this.name;}

}
