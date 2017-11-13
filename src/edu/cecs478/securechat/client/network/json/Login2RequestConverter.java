package edu.cecs478.securechat.client.network.json;

import com.google.gson.Gson;
import edu.cecs478.securechat.client.model.wrapper.Login2RequestWrapper;
import edu.cecs478.securechat.client.model.wrapper.Login2Response;

/**
 * Created by sasch on 09/11/2017.
 */
public class Login2RequestConverter implements IJsonConverter<Login2RequestWrapper> {
    @Override
    public String convert(Login2RequestWrapper obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public Login2RequestWrapper convertBack(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, Login2RequestWrapper.class);
    }
}
