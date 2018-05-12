package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Client.ClientConnection.Connection;
import it.polimi.ingsw.Client.ClientConnection.RmiClientMethod;
import it.polimi.ingsw.Client.ClientConnection.RmiConnection;
import it.polimi.ingsw.Client.ClientConnection.SocketConnection;
import it.polimi.ingsw.Client.View.View;
import it.polimi.ingsw.Server.ServerConnection.RmiServerMethodInterface;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Scanner;


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

        if (v.actButton.getText().equals("Connettiti")) {

            try {
                connection = new RmiConnection(v);
                connection.login();
                v.actButton.setText("Disconnettiti");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Errore connessione");
            }
        } else {
            try {
                connection.disconnect();
                v.actButton.setText("Connettiti");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Errore connessione");
            }
        }

    }


    public void doConnectSocket() {

        if (v.actButton.getText().equals("Connettiti")) {
            try {
                connection = new SocketConnection(v);
                connection.login();
                t = new Thread((SocketConnection) connection);
                t.start();


                v.actButton.setText("Disconnettiti");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                connection.disconnect();
                v.actButton.setText("Connettiti");
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
        } else if (e.getSource() == v.actButton) {
            if (v.connection.getText().equals("Rmi")) {
                this.doConnectRmi();
            } else {
                this.doConnectSocket();
            }
        }
        //connettiti

        // bisogna chiamare il metodo del rmi server
        if (action != "")
            connection.sendMessage(action);
    }

}