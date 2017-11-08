package edu.cecs478.securechat.client.view;

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
public class Registration {
    private JTextField name;
    private JPasswordField pwd;
    private JButton registerButton;
    public JPanel Base;

    public Registration() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(name.getText() != "" && pwd.getPassword().length > 5){
                    Account a = new Account(name.getText(), (new String(pwd.getPassword()).getBytes()), new byte[0], new byte[0], new byte[0]);

                    AccountJsonConverter conv = new AccountJsonConverter();
                    try {
                        HttpService.sendPOSTRequest(conv.convert(a),"http://127.0.0.1:8080/account/register");
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (HttpResponseNotCorrectException e1) {
                        e1.printStackTrace();
                    }


                    JFrame frame = new JFrame("Chat");
                    frame.setContentPane(new Chat().Base);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setSize(1000,1000);


                    Base.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

                }
            }
        });
    }
}
