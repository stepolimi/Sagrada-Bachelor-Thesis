package it.polimi.ingsw.server.serverConnection;

import it.polimi.ingsw.server.virtualView.VirtualView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
   // Scanner in;
    PrintWriter out;
    Connected connection;
    ArrayList action= new ArrayList();
    BufferedReader in;

    public SocketConnection(Socket s,VirtualView virtual,Connected connection) {
        this.s = s;
        this.virtual = virtual;
        this.connection = connection;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream());
            while(in!=null) {
                action.clear();
                String str = in.readLine();
                StringTokenizer token = new StringTokenizer(str, "-");
                while(token.hasMoreTokens())
                    action.add(token.nextToken());
                if(action.get(0).equals("Disconnected")) {
                    this.logout();
                }else if(action.get(0).equals("Login")) {
                    this.login((String) action.get(1));
                }
            }
        }catch(IOException e)
        {
           this.logout();
        }
    }

    public void login(String str) {
        System.out.println(str + "'s trying to connect with socket:");
        if(connection.checkUsername(str)) {
            connection.getUsers().put(this,str);
            forwardAction(action);
        }else{
            action.clear();
            action.add(loginError);
            action.add("username");
            sendMessage(action);
        }
    }

    public void logout() {
        try {
            in.close();
            out.close();
            s.close();
            String name = connection.remove(this);
            action.clear();
            action.add("Disconnected");
            action.add(name);
            if(name != null)
                forwardAction(action);
        }catch(IOException io) {
            System.out.println(io.getMessage());
        }
    }


    public void sendMessage(List action) {
        String message = new String();
        for(Object o: action){
            if(message.length() == 0)
                message = message + o;
            else
                message = message + "-" + o;
        }
        out.println(message);
        out.flush();
    }

    public void forwardAction(ArrayList action) { virtual.forwardAction(action); }
}
