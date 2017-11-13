package edu.cecs478.securechat.client.model.wrapper;

/**
 * Created by sasch on 09/11/2017.
 */
public class GetMessageWrapper {
    String username;
    String jwt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
