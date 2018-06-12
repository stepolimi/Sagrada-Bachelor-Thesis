package it.polimi.ingsw.client.view;

import java.io.IOException;
import java.util.Scanner;

public class Handler {
    View v;
    LoadImage load;
    public Handler() {
        load = new LoadImage();
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

            try {
                load.displayImage("startGame.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                v = new ControllerGUI(this);
                gui.setController((ControllerGUI) v);
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

