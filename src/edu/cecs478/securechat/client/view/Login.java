package edu.cecs478.securechat.client.view;

import edu.cecs478.securechat.client.model.Account;
import edu.cecs478.securechat.client.network.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by sasch on 08/11/2017.
 */
public class Login {
    public JPanel Base;
    private JTextField usernameInputTextField;
    private JTextField passwordTextField;
    private JButton loginButton;

    public Login() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account account = new Account(usernameInputTextField.getText(), passwordTextField.getText().getBytes(), new byte[0], new byte[0], "");

                Session session = new Session(account);

                JFrame frame = new JFrame("Chat");
                Chat chat = new Chat(session);
                frame.setContentPane(chat.Base);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setSize(1000,1000);
            }
        });
    }
}
