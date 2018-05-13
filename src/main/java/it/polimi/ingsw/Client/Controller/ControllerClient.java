package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Client.ClientConnection.Connection;
import it.polimi.ingsw.Client.ClientConnection.RmiConnection;
import it.polimi.ingsw.Client.ClientConnection.SocketConnection;
import it.polimi.ingsw.Client.View.View;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ControllerClient implements ActionListener {
    View v;
    // virtual view per testarlo in locale, poi utilizzeremo rmi
    Connection connection;
    Thread t;

    public ControllerClient() {
    }

    public void initView(View v) {
        this.v = v;
    }

    public void doConnectRmi() {

        if (v.RmiButton.getText().equals("Connettiti Rmi")) {

            try {
                connection = new RmiConnection(v);
                connection.login();
                v.RmiButton.setText("Disconnettiti");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Errore connessione");
            }
        } else {
            try {
                connection.disconnect();
                v.RmiButton.setText("Connettiti Rmi");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Errore connessione");
            }
        }

    }


    public void doConnectSocket() {

        if (v.SocketButton.getText().equals("Connettiti Socket")) {
            try {
                connection = new SocketConnection(v);
                connection.login();
                t = new Thread((SocketConnection) connection);
                t.start();


                v.SocketButton.setText("Disconnettiti");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                connection.disconnect();
                v.SocketButton.setText("Connettiti Socket");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Errore connessione");
            }
        }

    }


    public void actionPerformed(ActionEvent e) {

        String action = "";

        if (e.getSource() == v.insButton) {
            action = "InsertDice";
        } else if (e.getSource() == v.remButton) {
            action = "RemoveDice";
        } else if (e.getSource() == v.extrButton) {
            action = "Extract";
        } else if (e.getSource() == v.SocketButton) {
            this.doConnectSocket();
        } else if(e.getSource()== v.RmiButton)
                this.doConnectRmi();


        //connettiti

        // bisogna chiamare il metodo del rmi server
        if (action != "")
            connection.sendMessage(action);
    }

}