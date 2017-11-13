package edu.cecs478.securechat.client.network.json;

import com.google.gson.Gson;
import edu.cecs478.securechat.client.model.wrapper.Login1Response;
import edu.cecs478.securechat.client.model.wrapper.Login2Response;

/**
 * Created by sasch on 09/11/2017.
 */
public class Login2ResponseConverter implements IJsonConverter<Login2Response> {
    @Override
    public String convert(Login2Response obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public Login2Response convertBack(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, Login2Response.class);
    }
}
