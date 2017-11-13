package edu.cecs478.securechat.client.model.wrapper;

/**
 * Created by sasch on 09/11/2017.
 */
public class Login2RequestWrapper {
    String username;
    byte[] tag;

    public Login2RequestWrapper() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getTag() {
        return tag;
    }

    public void setTag(byte[] tag) {
        this.tag = tag;
    }
}
