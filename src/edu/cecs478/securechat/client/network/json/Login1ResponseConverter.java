package edu.cecs478.securechat.client.network.json;

import com.google.gson.Gson;
import edu.cecs478.securechat.client.model.wrapper.Login1Response;

/**
 * Created by sasch on 09/11/2017.
 */
public class Login1ResponseConverter implements IJsonConverter<Login1Response> {
    @Override
    public String convert(Login1Response obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public Login1Response convertBack(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, Login1Response.class);
    }
}
