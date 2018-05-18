package it.polimi.ingsw.client.clientConnection;

import it.polimi.ingsw.client.view.ControllerClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketConnection implements Connection,Runnable {

    Socket socket;
    PrintWriter out;
    Scanner in;
    ControllerClient controllerClient;// da vedere
    private boolean stopThread = false;

    public SocketConnection(ControllerClient controllerClient) throws IOException
    {
        this.controllerClient = controllerClient;
        socket = new Socket("localhost", 1666);
        out = new PrintWriter(socket.getOutputStream());
        in = new Scanner(socket.getInputStream());

    }


    public void sendMessage(String str) {
        out.println(str);
        out.flush();
    }


    public void login(String nickname) {
       out.println("Login-" + nickname);
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
                if(str.equals("Welcome") || str.equals("Login_error"))
                    controllerClient.login_resultSocket(str);
                System.out.println(str);
            }catch (NoSuchElementException e){
                System.out.println("disconnesso");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
