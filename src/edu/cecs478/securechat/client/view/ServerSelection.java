package edu.cecs478.securechat.client.view;

import edu.cecs478.securechat.client.helper.Constants;
import edu.cecs478.securechat.client.security.pem.PEMKeyGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by sasch on 03/12/2017.
 */
public class ServerSelection {
    public JPanel Base;
    private JTextField PortInput;
    private JTextField UrlInput;
    private JButton OKButton;

    public ServerSelection() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = UrlInput.getText()+":"+PortInput.getText();
                String prefix = "SecureChat";
                File f = new File("./"+prefix+ Constants.PUBLIC_KEY_ENDING);
                if(!(f.exists() && !f.isDirectory())) {
                    PEMKeyGenerator keyGen = new PEMKeyGenerator();
                    try {
                        keyGen.generatePrivateAndPublicKey(prefix);

                        JFrame frame = (JFrame)SwingUtilities.getRoot(Base);
                        Registration r = new Registration();
                        r.setURLBase(url);
                        frame.setTitle("Registration");
                        frame.setContentPane(r.Base);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                        frame.setSize(500,500);
                    } catch (Exception er) {
                        er.printStackTrace();
                    }
                }else {
                    JFrame frame = (JFrame)SwingUtilities.getRoot(Base);
                    Login l = new Login();
                    l.setURLBase(url);
                    frame.setTitle("Login");
                    frame.setContentPane(l.Base);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setSize(500,500);
                }
            }
        });
    }
}
