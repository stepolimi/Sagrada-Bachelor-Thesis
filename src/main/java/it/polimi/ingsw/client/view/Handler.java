package it.polimi.ingsw.client.view;

import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.costants.GameCreationMessages.*;
import static it.polimi.ingsw.costants.LoginMessages.*;

public class Handler {
    View v;

    public Handler() {
        this.setGraphicInterface();
        v.startScene();
        v.setScene("connection");
        v.setScene("login");
    }

    public void setGraphicInterface()
    {
        boolean correct=false;
        String choose;
        while (!correct) {
            Scanner in = new Scanner(System.in);
            System.out.println("\u001B[33m"+ "|----------------------------|");
            System.out.println("\u001B[34m" + "  S A G R A D A    G A M E");
            System.out.println("\u001B[33m"+"|----------------------------|");

            System.out.println("\u001B[37m"+"Scegli l'interfaccia grafica");
            System.out.println("1 ----> Cli");
            System.out.println("2-----> GUI");
            choose = in.nextLine();
            if (choose.equals("1"))
            {
                v = new ViewCLI();
                v.setHandler(this);
                correct = true;
            }
            else if(choose.equals("2"))
            {
                String args[]={};
                ViewGUI gui = new ViewGUI();
                v = new ControllerClient(this);
                gui.setController((ControllerClient) v);
                gui.main(args);
                correct = true;
            }
            else {
                System.out.println("Inserisci un parametro valido");
                correct = false;
            }
        }

    }

    public View getView() { return v; }
}
