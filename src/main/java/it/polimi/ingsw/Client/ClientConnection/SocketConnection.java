package it.polimi.ingsw.Client.ClientConnection;

import it.polimi.ingsw.Client.View.View;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketConnection implements Connection,Runnable {
    Socket socket;
    PrintWriter out;
    Scanner in;
    View v; // da vedere
    private boolean stopThread = false;

    public SocketConnection(View v) throws IOException
    {
        this.v = v;
        socket = new Socket("localhost", 1666);
        out = new PrintWriter(socket.getOutputStream());
        in = new Scanner(socket.getInputStream());

    }


    public void sendMessage(String str) {
        out.println(str);
        out.flush();
    }


    public void login() {
        out.println("Login"); // deve esserci un if(str.equals("Login")){username = in.nextLine; }
        out.flush();
        out.println(v.getName());
        out.flush();
    }


    public void disconnect(){
        stopRunning();
        out.println("Disconnected");
        out.flush();
        out.close();
        try{
            socket.close();
        }catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        in.close();
    }

    public void stopRunning()
    {
        stopThread = true;
    }


    public void run() {
        while(!stopThread){
            try {
                String str = in.nextLine();
                System.out.println(str);
                v.text.setText(str);
            }catch (NoSuchElementException e){
                System.out.println("disconnesso");
            }

        }
    }
}
