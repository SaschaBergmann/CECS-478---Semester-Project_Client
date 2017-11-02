package edu.cecs478.securechat.client.view;

import edu.cecs478.securechat.client.model.Message;
import edu.cecs478.securechat.client.security.decryption.DecryptionService;
import edu.cecs478.securechat.client.security.encryption.EncryptionService;
import edu.cecs478.securechat.client.security.exceptions.IntegrityNotGuaranteedException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by sasch on 09/10/2017.
 */
public class Chat {
    private JTextField chatInputTextField;
    private JTextPane chatHistoryTextPane;
    private JButton sendButton;
    private JPanel Base;
    private JScrollPane ContentScroller;

    public Chat() {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Message message = new Message(chatInputTextField.getText().getBytes(), new byte[0], new byte[0], new byte[0], new byte[0]);
                chatInputTextField.setText("");

                printToHistory(message.toString());
                printToHistory("\n\n\n---------------NOW ENCRYPTING---------------\n\n\n");

                message = EncryptionService.encrypt(message, "C:\\OpenSSL-Win32\\bin\\public.pem");

                printToHistory(message.toString());
                printToHistory("\n\n\n---------------NOW DECRYPTING---------------\n\n\n");

                try {
                    message = DecryptionService.decrypt(message, "C:\\OpenSSL-Win32\\bin\\private.pem");
                } catch (IntegrityNotGuaranteedException e1) {
                    e1.printStackTrace();
                }

                printToHistory(message.toString());

                printToHistory("\n\n\n------------------------------\n\n\n");
            }
        });
    }

        private void printToHistory(String content){
        chatHistoryTextPane.setText(chatHistoryTextPane.getText()+"\n"+content);
     }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat");
        frame.setContentPane(new Chat().Base);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1000,1000);
    }
}
