package edu.cecs478.securechat.client.model.wrapper;

/**
 * Created by sasch on 09/11/2017.
 */

public class Login1Response {
    boolean successful;
    String challenge;
    String salt;

    public Login1Response() {
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
