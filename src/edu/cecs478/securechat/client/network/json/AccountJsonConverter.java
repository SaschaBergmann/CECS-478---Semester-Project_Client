package edu.cecs478.securechat.client.network.json;

import com.google.gson.Gson;
import edu.cecs478.securechat.client.model.Account;

/**
 * Created by sasch on 06/11/2017.
 */
public class AccountJsonConverter implements IJsonConverter<Account> {
    @Override
    public String convert(Account obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
