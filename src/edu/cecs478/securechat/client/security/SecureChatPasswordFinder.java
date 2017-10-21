package edu.cecs478.securechat.client.security;

import org.bouncycastle.openssl.PasswordFinder;

/**
 * Created by sasch on 18/10/2017.
 */

public class SecureChatPasswordFinder implements PasswordFinder {

    @Override
    public char[] getPassword() {
        return ("NotFrench").toCharArray();
    }
}