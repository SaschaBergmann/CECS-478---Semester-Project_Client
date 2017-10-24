package edu.cecs478.securechat.client.security;

import org.bouncycastle.openssl.PasswordFinder;

/**
 * Created by FinnB on 18/10/2017.
 */

/**
 * Implementation of the PasswordFinder Interface for the SecureChat application
 * Returns a password for the private Key file
 */
public class SecureChatPasswordFinder implements PasswordFinder {

    /**
     * Gets the password for the private key file
     * @return Password for the Key File
     */
    @Override
    public char[] getPassword() {
        return ("NotFrench").toCharArray();
    }
}