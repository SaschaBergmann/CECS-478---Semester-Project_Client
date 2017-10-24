package edu.cecs478.securechat.client.security.exceptions;

import edu.cecs478.securechat.client.model.Message;

/**
 * Created by FinnB on 10/10/2017.
 * This exception is thrown if the Integrity of a message can not be guaranteed
 */
public class IntegrityNotGuaranteedException extends Exception {

    private Message faultyMessage;

    /**
     * Creates the IntegrityNotGuaranteedException, sets the fault message and calls the superclasses constructor
     * @param message
     */
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
