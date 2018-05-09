package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Client.Controller.ControllerClient;

import javax.swing.*;
import java.awt.*;
 // la view verrà fatta con javaFX, è solo per testare il funzionamento di rmi
public class View  {
    ControllerClient controller;
    JFrame viewFrame;
    JPanel panel;
    public JTextArea text;
    public JButton insButton, remButton, extrButton, actButton;
    boolean isConnect;


    public View(ControllerClient ctrl)
    {
        this.controller = ctrl;
        isConnect = false;
        createView();
    }

    public void createView()
    {
        viewFrame = new JFrame("Prova Dadi");
        viewFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        viewFrame.setSize(1000,700);

        panel = new JPanel();
        viewFrame.add(panel, BorderLayout.WEST);

        insButton = new JButton("Inserisci dado");
        panel.add(new JLabel("Inserisci un dado"));
        panel.add(insButton);

        remButton = new JButton("Rimuovi dado");
        panel.add(new JLabel("Rimuovi un dado"));
        panel.add(remButton);

        extrButton = new JButton("Estrai nuovi dadi");
        panel.add(new JLabel("Estrai nuovi dadi"));
        panel.add(extrButton);

        actButton = new JButton("Connettiti");
        panel.add(actButton);

        viewFrame.setVisible(true);
        insButton.addActionListener(controller);
        remButton.addActionListener(controller);
        extrButton.addActionListener(controller);
        actButton.addActionListener(controller);

        text = new JTextArea();
        viewFrame.add(text,BorderLayout.SOUTH);


    }
}
