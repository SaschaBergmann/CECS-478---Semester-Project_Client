package edu.cecs478.securechat.client.network;

import edu.cecs478.securechat.client.model.Account;
import edu.cecs478.securechat.client.network.exceptions.HttpResponseNotCorrectException;
import edu.cecs478.securechat.client.network.json.StringJsonConverter;

import java.io.UnsupportedEncodingException;

/**
 * Created by sasch on 07/11/2017.
 */
public class Session {

    private boolean loggedIn = false;
    private String token;

    public Session(Account user){
        StringJsonConverter conv = new StringJsonConverter();

        try {
            HttpService.sendPOSTRequest(conv.convert(user.getUsername()), "http://127.0.0.1:8080/account/login1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpResponseNotCorrectException e) {
            e.printStackTrace();
        }
    }

}
