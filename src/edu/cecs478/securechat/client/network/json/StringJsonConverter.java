package edu.cecs478.securechat.client.network.json;

import com.google.gson.Gson;

/**
 * Created by sasch on 08/11/2017.
 */
public class StringJsonConverter implements IJsonConverter<String> {
    @Override
    public String convert(String st){
        Gson gson=new Gson();
        return gson.toJson(st);
    }
}
