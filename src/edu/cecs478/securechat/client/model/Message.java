package edu.cecs478.securechat.client.model;

import java.io.UnsupportedEncodingException;

/**
 * Created by sasch on 09/10/2017.
 */
public class Message {

    private byte[] message;
    private byte[] iv;
    private byte[] sender;
    private byte[] recipient;
    private byte[] keys;
    private byte[] HMACHash;

    public Message( byte[] message,  byte[] sender,  byte[] recipient,  byte[] iv,  byte[] HMAC) {
        this.message = message;
        this.iv = iv;
        this.sender = sender;
        this.recipient = recipient;
        this.keys = new byte[0];
        this.HMACHash = HMAC;
    }

    public  byte[] getMessage() {
        return message;
    }

    public void setMessage( byte[] message) {
        this.message = message;
    }

    public  byte[] getIv() {
        return iv;
    }

    public void setIv( byte[] iv) {
        this.iv = iv;
    }

    public  byte[] getSender() {
        return sender;
    }

    public void setSender( byte[] sender) {
        this.sender = sender;
    }

    public  byte[] getRecipient() {
        return recipient;
    }

    public void setRecipient( byte[] recipient) {
        this.recipient = recipient;
    }

    public  byte[] getKeys() {
        return keys;
    }

    public void setKeys( byte[] keys) {
        this.keys = keys;
    }

    public  byte[] getHMACHash() {
        return HMACHash;
    }

    public void setHMACHash( byte[] HMACHash) {
        this.HMACHash = HMACHash;
    }

    @Override
    public  String toString(){
         String output = "";
        try {
            output += "Message: \nSender: "+new String(getSender(), "UTF-8");
            output += "\nReceprient: "+new String(getRecipient(), "UTF-8");
            output += "\nMessage: "+new String(getMessage(), "UTF-8");
            output += "\nHMAC-Hash: "+new String(getHMACHash(), "UTF-8");
            output += "\nIV: "+new String(getIv(), "UTF-8");
            output += "\nKeys: "+new String(getKeys(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return output;
    }
}
