package edu.cecs478.securechat.client.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cecs478.securechat.client.model.Account;
import edu.cecs478.securechat.client.model.Message;
import edu.cecs478.securechat.client.model.wrapper.Login1Response;
import edu.cecs478.securechat.client.model.wrapper.Login2RequestWrapper;
import edu.cecs478.securechat.client.model.wrapper.Login2Response;
import edu.cecs478.securechat.client.network.exceptions.HttpResponseNotCorrectException;
import edu.cecs478.securechat.client.network.json.Login1ResponseConverter;
import edu.cecs478.securechat.client.network.json.Login2RequestConverter;
import edu.cecs478.securechat.client.network.json.Login2ResponseConverter;
import edu.cecs478.securechat.client.security.integrity.HMACService;
import edu.cecs478.securechat.client.security.integrity.HashService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasch on 07/11/2017.
 */
public class Session {

    private boolean loggedIn = false;
    private Account account;

    public Session(Account user){
        try {
            String obj = HttpService.sendPOSTRequest(user.getUsername(), "http://127.0.0.1:8080/account/login1");

            Login1ResponseConverter respConv1 = new Login1ResponseConverter();

            Login1Response resp = respConv1.convertBack(obj);

            byte[] challenge = resp.getChallenge().getBytes("ISO-8859-1");
            byte[] salt = resp.getSalt().getBytes("ISO-8859-1");

            user.setSalt(salt);
            user.setLastChallenge(challenge);

            user.setPwd(HashService.hash(user.getPwd(), user.getSalt()));

            byte[] tag = HMACService.CreateHMACHash(user.getLastChallenge(), user.getPwd());

            Login2RequestWrapper wrapper = new Login2RequestWrapper();
            Login2RequestConverter reqConv = new Login2RequestConverter();
            wrapper.setUsername(user.getUsername());
            wrapper.setTag(tag);

            ObjectMapper mapper = new ObjectMapper();
            try {
                obj = HttpService.sendPOSTRequest(mapper.writeValueAsString(wrapper), "http://127.0.0.1:8080/account/login2");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            Login2ResponseConverter respConv2 = new Login2ResponseConverter();
            Login2Response resp2 = respConv2.convertBack(obj);

            user.setToken(resp2.getJwt());
            account = user;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpResponseNotCorrectException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message msg){
        msg.setSender(account.getUsername());
        msg.setJwt(account.getToken());

        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpService.sendPOSTRequest(mapper.writeValueAsString(msg),"http://127.0.0.1:8080/message/send");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpResponseNotCorrectException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessages(){

        ObjectMapper mapper = new ObjectMapper();
        try {
            String response = HttpService.sendGetRequest("http://127.0.0.1:8080/message/receive/"+account.getUsername(), account.getToken());
            ArrayList<Message> messages = mapper.readValue(response, new TypeReference<ArrayList<Message>>(){});
            return messages;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpResponseNotCorrectException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
