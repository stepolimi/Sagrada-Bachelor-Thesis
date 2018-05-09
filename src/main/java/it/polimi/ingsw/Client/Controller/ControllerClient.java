package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Client.ClientConnection.RmiClientMethod;
import it.polimi.ingsw.Client.View.View;
import it.polimi.ingsw.ServerConnection.RmiServerMethodInterface;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControllerClient implements ActionListener {
    private View v;
    RmiServerMethodInterface server;
    RmiClientMethod client;

    public ControllerClient(){}
    public void initView(View v){this.v = v;}

    public void doConnect()
    {
        if (v.actButton.getText().equals("Connettiti")){

            try{
                client=new RmiClientMethod("aaaaa");
                client.setView(v);

                server=(RmiServerMethodInterface) Naming.lookup("rmi://127.0.0.1/myabc");
                server.login(client);


                v.actButton.setText("Disconnettiti");
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Errore connessione");
            }
        }else{
            v.actButton.setText("Connettiti");
            try {
                server.disconnected(client);
            }catch(RemoteException ex)
            {
                System.out.println(ex.getMessage());
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
            this.doConnect();
            //connettiti
        }
        // bisogna chiamare il metodo del rmi server
        try {
            if(action.size()!=0)
                server.forwardAction(action);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
