package it.polimi.ingsw.client.view;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Scanner;

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
            if (Integer.parseInt(choose) == 1)
            {
                v = new ViewCLI();
                v.setHandler(this);
                correct = true;
            }
            else if(Integer.parseInt(choose) == 2)
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
    // deliver action on GUI or CLI
    public void deliverGI(String action)
    {
        if(action.equals("Welcome") || action.equals("Login_error"))
        {
            v.login(action);
        }
    }


}
