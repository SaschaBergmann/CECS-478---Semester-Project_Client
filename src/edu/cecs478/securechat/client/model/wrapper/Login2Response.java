package edu.cecs478.securechat.client.model.wrapper;

/**
 * Created by sasch on 09/11/2017.
 */
public class Login2Response {
    boolean successful;
    String jwt;

    public Login2Response() {
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
