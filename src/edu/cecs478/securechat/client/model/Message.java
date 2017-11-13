package edu.cecs478.securechat.client.model;

import java.io.UnsupportedEncodingException;

/**
 * Created by sasch on 09/10/2017.
 */
public class Message {
    private Long id;
    private byte[] message;
    private byte[] iv;
    private String sender;
    private String recipient;
    private byte[] encryptionK;
    private byte[] HMACHash;
    private String jwt;

    public Message() {
        this.message = new byte[0];
        this.iv = new byte[0];
        this.sender = "";
        this.recipient = "";
        this.encryptionK = new byte[0];
        this.HMACHash = new byte[0];
        this.jwt = "";
    }

    public Message(byte[] message, byte[] iv, String sender, String recipient, byte[] keys, byte[] HMACHash, String jwt) {
        this.message = message;
        this.iv = iv;
        this.sender = sender;
        this.recipient = recipient;
        this.encryptionK = keys;
        this.HMACHash = HMACHash;
        this.jwt = jwt;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public byte[] getEncryptionK() {
        return encryptionK;
    }

    public void setEncryptionK(byte[] encryptionK) {
        this.encryptionK = encryptionK;
    }

    public byte[] getHMACHash() {
        return HMACHash;
    }

    public void setHMACHash(byte[] HMACHash) {
        this.HMACHash = HMACHash;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public  String toString(){
         String output = "";
        try {
            output += "Message: \nSender: "+getSender();
            output += "\nReceprient: "+getRecipient();
            output += "\nMessage: "+new String(getMessage(), "UTF-8");
            output += "\nHMAC-Hash: "+new String(getHMACHash(), "UTF-8");
            output += "\nIV: "+new String(getIv(), "UTF-8");
            output += "\nKeys: "+new String(getEncryptionK(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return output;
    }
}
