package edu.cecs478.securechat.client.view;

import edu.cecs478.securechat.client.model.Message;
import edu.cecs478.securechat.client.model.PublicKeyStore;
import edu.cecs478.securechat.client.network.Session;
import edu.cecs478.securechat.client.security.decryption.DecryptionService;
import edu.cecs478.securechat.client.security.encryption.EncryptionService;
import edu.cecs478.securechat.client.security.exceptions.IntegrityNotGuaranteedException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by sasch on 09/10/2017.
 */
public class Chat {
    private JTextField chatInputTextField;
    private JButton sendButton;
    public JPanel Base;
    private JScrollPane ContentScroller;
    private JButton recieveButton;
    private JTextField recepientTextField;
    private JList ChatList;
    private String prefix = "SecureChat";
    private DefaultListModel model;
    private PublicKeyStore store;

    private Session session;

    public Chat(Session s) {

        model = new DefaultListModel();
        store = new PublicKeyStore();

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

                if (!store.isKeyAvailable(recepientTextField.getText())){
                    getPublicKey(recepientTextField.getText());
                }

                Message message = new Message(chatInputTextField.getText().getBytes(), new byte[0], "", "", new byte[0], new byte[0], "");
                chatInputTextField.setText("");
                message.setRecipient(recepientTextField.getText());

                printToHistory("Message sent");

                message = EncryptionService.encrypt(message, publicKey);
                session.sendMessage(message);
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
                        String output = new String(message.getMessage(), "UTF-8");
                        output = message.getSender()+": "+output;
                        printToHistory(output);
                    } catch (IntegrityNotGuaranteedException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void printToHistory(String content){
        model.addElement(content);
        ChatList.setModel(model);
    }

    private void getPublicKey(String name){
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Key File","pem");
        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);

        fc.showOpenDialog(this.Base);

        File file = fc.getSelectedFile();

        store.addKey(name, file.getAbsolutePath());
    }
}
