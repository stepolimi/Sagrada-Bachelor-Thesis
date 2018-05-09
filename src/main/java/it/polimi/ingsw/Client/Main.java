package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.Controller.ControllerClient;
import it.polimi.ingsw.Client.View.View;

import java.util.Scanner;

// per testare rmi e gli observer
public class Main {

    public static void main(String[] args) {

        System.out.println("Scegli l'interfaccia grafica:\n1) Cli\n2)Gui");
        Scanner  sc = new Scanner(System.in);
        String str = sc.nextLine();
        if(Integer.parseInt(str)==1)
        {
            // da fare
        }else {
            ControllerClient ctrlClient = new ControllerClient();
            View v = new View(ctrlClient);
            ctrlClient.initView(v);
        }
    }
}
