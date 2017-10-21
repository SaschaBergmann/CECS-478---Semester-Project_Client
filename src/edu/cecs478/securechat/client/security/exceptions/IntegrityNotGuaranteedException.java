package edu.cecs478.securechat.client.security.exceptions;

import edu.cecs478.securechat.client.model.Message;

/**
 * Created by sasch on 10/10/2017.
 */
public class IntegrityNotGuaranteedException extends Exception {

    private Message faultyMessage;

    public IntegrityNotGuaranteedException(Message message) {
        super();
        this.faultyMessage = message;
    }

    public Message getFaultyMessage() {
        return faultyMessage;
    }

    public void setFaultyMessage(Message faultyMessage) {
        this.faultyMessage = faultyMessage;
    }


}
