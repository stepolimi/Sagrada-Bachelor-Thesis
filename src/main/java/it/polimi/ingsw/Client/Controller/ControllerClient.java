package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Client.ClientConnection.RmiClientMethod;
import it.polimi.ingsw.Client.View.View;
import it.polimi.ingsw.ServerConnection.RmiServerMethodInterface;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;


public class ControllerClient implements ActionListener,Runnable {
    View v;
    // virtual view per testarlo in locale, poi utilizzeremo rmi
    RmiServerMethodInterface server;
    RmiClientMethod client;
    boolean isRmi;
    boolean isSocket;
    PrintWriter out;
    Socket s;
    Scanner in;
    public ControllerClient() {}

    public void initView(View v)
    {
        this.v = v;
        isRmi = false;
        isSocket = false;
    }

    public void doConnectRmi()
    {

        if (v.actButton.getText().equals("Connettiti")){

            try{
                client=new RmiClientMethod("aaaa");
                client.setView(v);

                server=(RmiServerMethodInterface) Naming.lookup("rmi://127.0.0.1/myabc");
                server.login(client);


                v.actButton.setText("Disconnettiti");

            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Errore connessione");
            }
        }
        else{
            try {
                server.disconnected(client);
                v.actButton.setText("Connettiti");
            }catch(Exception e) {
                e.printStackTrace();
                System.out.println("Errore connessione");
            }
        }

    }


    public void doConnectSocket()
    {

        if (v.actButton.getText().equals("Connettiti")){
            try {
                s = new Socket("localhost", 1666);
                out = new PrintWriter(s.getOutputStream());
                in = new Scanner(s.getInputStream());
                Thread t = new Thread(this);
                t.start();


                v.actButton.setText("Disconnettiti");
            }catch(IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else{
            try {

                s.close();
                out.close();
                in.close();
                v.actButton.setText("Connettiti");
            }catch(Exception e) {
                e.printStackTrace();
                System.out.println("Errore connessione");
            }
        }

    }


    public void actionPerformed(ActionEvent e) {

        ArrayList action = new ArrayList();

        if (e.getSource() == v.insButton) {
            action.add("InsertDice");
        } else if (e.getSource() == v.remButton) {
            action.add("RemoveDice");
            action.add(1);
        } else if (e.getSource() == v.extrButton) {
            action.add("ExtractDices");
        } else if (e.getSource() == v.actButton) {
            if (v.connection.getText().equals("Rmi")) {
                this.doConnectRmi();
                isRmi = true;
            }else
            {
                this.doConnectSocket();
                isSocket = true;
            }
        }
        //connettiti

        // bisogna chiamare il metodo del rmi server
        if(isRmi)
        {
            System.out.println("Sto usando RMI");
            try {
                if(action.size()!=0)
                    server.forwardAction(action);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }else if(isSocket)
        {
            System.out.println("Sto usando Socket");
            if(action.size()>0)
            {
                out.println(action.get(0));
                out.flush();
            }
        }
    }

    @Override
    public void run() {
        while(true) {
            String str = in.nextLine();
            System.out.println(str);
            v.text.setText(str);
        }
    }
}
