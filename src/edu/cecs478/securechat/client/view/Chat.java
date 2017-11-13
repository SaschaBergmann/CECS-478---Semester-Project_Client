package edu.cecs478.securechat.client.view;

import edu.cecs478.securechat.client.model.Message;
import edu.cecs478.securechat.client.network.Session;
import edu.cecs478.securechat.client.security.decryption.DecryptionService;
import edu.cecs478.securechat.client.security.encryption.EncryptionService;
import edu.cecs478.securechat.client.security.exceptions.IntegrityNotGuaranteedException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sasch on 09/10/2017.
 */
public class Chat {
    private JTextField chatInputTextField;
    private JTextPane chatHistoryTextPane;
    private JButton sendButton;
    public JPanel Base;
    private JScrollPane ContentScroller;
    private JButton recieveButton;
    private JTextField recepientTextField;
    private String prefix = "SecureChat";

    private Session session;

    public Chat(Session s) {

        session = s;

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String publicKey = "./"+prefix+"public.pem";

                Message message = new Message(chatInputTextField.getText().getBytes(), new byte[0], "", "", new byte[0], new byte[0], "");
                chatInputTextField.setText("");
                message.setRecipient(recepientTextField.getText());
                printToHistory("\n\n\n---------------NOW ENCRYPTING AND SENDING---------------\n\n\n");


                message = EncryptionService.encrypt(message, publicKey);
                session.sendMessage(message);

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        recieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String privateKey = "./"+prefix+"private.pem";

                List<Message> msgs;

                msgs = session.getMessages();

                for (Message message :
                        msgs) {
                    try {
                        message = DecryptionService.decrypt(message, privateKey);
                    } catch (IntegrityNotGuaranteedException e1) {
                        e1.printStackTrace();
                    }
                    printToHistory(message.toString());
                }
            }
        });
    }

        private void printToHistory(String content){
        chatHistoryTextPane.setText(chatHistoryTextPane.getText()+"\n"+content);
     }
}
