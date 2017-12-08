package edu.cecs478.securechat.client;

import edu.cecs478.securechat.client.security.pem.PEMKeyGenerator;
import edu.cecs478.securechat.client.view.Login;
import edu.cecs478.securechat.client.view.Registration;
import edu.cecs478.securechat.client.view.ServerSelection;

import javax.swing.*;
import java.io.File;

/**
 * Created by sasch on 06/11/2017.
 */
public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("URL");
        frame.setContentPane(new ServerSelection().Base);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500,500);

    }
}
