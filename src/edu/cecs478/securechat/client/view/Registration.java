package edu.cecs478.securechat.client.view;

import edu.cecs478.securechat.client.helper.Constants;
import edu.cecs478.securechat.client.model.Account;
import edu.cecs478.securechat.client.network.HttpService;
import edu.cecs478.securechat.client.network.exceptions.HttpResponseNotCorrectException;
import edu.cecs478.securechat.client.network.json.AccountJsonConverter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;

/**
 * Created by sasch on 02/11/2017.
 */
public class Registration extends JFrame {
    private String URLBase = "";
    private JTextField name;
    private JPasswordField pwd;
    private JButton registerButton;
    public JPanel Base;

    public Registration() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(name.getText() != "" && pwd.getPassword().length > 5){
                    Account a = new Account(name.getText(), (new String(pwd.getPassword()).getBytes()), new byte[0], new byte[0], "");

                    AccountJsonConverter conv = new AccountJsonConverter();
                    try {
                        HttpService.sendPOSTRequest(conv.convert(a),URLBase+Constants.URL_ACCOUNT_REGISTER);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (HttpResponseNotCorrectException e1) {
                        e1.printStackTrace();
                    }
                    JFrame frame = (JFrame)SwingUtilities.getRoot(Base);
                    frame.setTitle("Login");
                    Login l = new Login();
                    l.setURLBase(URLBase);
                    frame.setContentPane(l.Base);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setSize(500,500);
                }
            }
        });
    }

    public String getURLBase() {
        return URLBase;
    }

    public void setURLBase(String URLBase) {
        this.URLBase = URLBase;
    }
}
