package edu.cecs478.securechat.client.network.exceptions;

import org.apache.http.StatusLine;

/**
 * Created by Finn on 06/11/2017.
 */
public class HttpResponseNotCorrectException extends Exception {

    StatusLine status;

    public HttpResponseNotCorrectException(StatusLine s){
        status = s;
    }

    public StatusLine getStatus() {
        return status;
    }

    public void setStatus(StatusLine status) {
        this.status = status;
    }
}
