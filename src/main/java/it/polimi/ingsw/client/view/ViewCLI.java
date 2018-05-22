package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.RmiConnection;
import it.polimi.ingsw.client.clientConnection.SocketConnection;

import java.io.IOException;
import java.util.List;
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
        }else if(str.equals("Login_error-username")) {
            System.out.println("Errore, nickname già in uso");
            this.setLogin();
        }else if(str.equals("Login_error-game")) {
            System.out.println("Errore, partita già in corso");
            this.setLogin();
        }
    }

    public void playerConnected(String name){
        System.out.println(name + " si è aggiunto alla lobby\n");
    }

    public void playerDisconnected(String name){
        System.out.println(name + " si è disconnesso\n");
    }

    public void timerPing(String time) {
        System.out.println("la partita inizierà tra " + time + " secondi\n");
    }

    public void createGame(){
        System.out.println("partita creata\n");
    }

    public void setSchemas(List<String> schemas){
        System.out.println("scrivi il nome dello schema che preferisci tra:");
        for(String s: schemas)
            System.out.println(s);
        System.out.println("\n");
    }

    public void setPrivateCard(String colour){
        System.out.println("il tuo obiettivo privato sarà il colore: " + colour + "\n");
    }

    public void setPublicObjectives(List<String> cards){
        System.out.println("gli obiettivi publici per questa partita saranno:");
        for(String s: cards)
            System.out.println(s);
        System.out.println("\n");
    }

    public void setToolCards(List<String> cards){
        System.out.println("le carte utensili per questa partita saranno:");
        for(String s: cards)
            System.out.println("la carta numero " + s + ",");
        System.out.println("\n");
    }

    public void setHandler(Handler hand) {
        this.hand = hand;
    }

    public String getName(){ return this.username;}
}

