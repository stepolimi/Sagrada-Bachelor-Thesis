package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.RmiConnection;
import it.polimi.ingsw.client.clientConnection.SocketConnection;

import java.io.IOException;
import java.util.Scanner;

public class ViewCLI implements View{
    private Scanner input;
    private String username;
    private Connection connection;
    private Handler hand;
    public ViewCLI()
    {
        input = new Scanner(System.in);
    }


    public void setScene(String scene) {
        if(scene.equals("connection"))this.setConnection();
        else if(scene.equals("login"))this.setLogin();



    }

    public void setConnection()
    {
        boolean correct= false;
        String choose;
        while(!correct) {
            System.out.println("Scegli la tua connessione");
            System.out.println("1 ----> Socket");
            System.out.println("2 ----> Rmi");
            choose = input.nextLine();
            if (Integer.parseInt(choose) == 1)
            {
                try {
                    connection = new SocketConnection(hand);
                    Thread t = new Thread((SocketConnection)connection);
                    t.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                correct = true;
            }
            else if (Integer.parseInt(choose) == 2)
            {
                connection = new RmiConnection(hand);
                correct = true;
            }
            else
                correct = false;
        }
    }

    public void setLogin()
    {
        System.out.println("Inserisci il tuo username:");
        username = input.nextLine();
        connection.login(username);

    }

    public void startScene() {
        System.out.println("\u001B[32m" + "W E L C O M E ");
        System.out.println("\u001B[0m"+"You can choose a type of:");
        System.out.println("Graphic Interface: GUI or CLI");
        System.out.println("Connection: RMI/SOCKET");
    }

    public void login(String str) {
        if (str.equals("Welcome"))
        {
            System.out.println("La partita inizierà a breve");
            System.out.println("Aspettando altri giocatori...");
        }else if(str.equals("Login_error"))
        {
            System.out.println("Errore, nickname già in uso");
            this.setLogin();
        }
    }

    public void setHandler(Handler hand) {
        this.hand = hand;
    }
}

