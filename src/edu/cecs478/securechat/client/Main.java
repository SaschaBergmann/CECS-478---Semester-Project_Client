package edu.cecs478.securechat.client;

import edu.cecs478.securechat.client.security.pem.PEMKeyGenerator;
import edu.cecs478.securechat.client.view.Registration;

import javax.swing.*;
import java.io.File;

/**
 * Created by sasch on 06/11/2017.
 */
public class Main {

    public static void main(String[] args) {
        String prefix = "SecureChat";
        File f = new File("./"+prefix+"public.pem");
        //if(!(f.exists() && !f.isDirectory())) {
        if(true){
            PEMKeyGenerator keyGen = new PEMKeyGenerator();
            try {
                keyGen.generatePrivateAndPublicKey(prefix);

                JFrame frame = new JFrame("Registration");
                frame.setContentPane(new Registration().Base);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setSize(1000,1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
