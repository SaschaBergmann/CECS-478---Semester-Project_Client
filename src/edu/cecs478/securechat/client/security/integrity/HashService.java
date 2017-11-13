package edu.cecs478.securechat.client.security.integrity;

import org.apache.commons.lang3.ArrayUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sasch on 01/11/2017.
 */
public class HashService {
    public static byte[] hash(byte[] input, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] concatinated = ArrayUtils.addAll(input,salt);
        md.update(concatinated); // Change this to "UTF-16" if needed
        byte[] digest = md.digest();
        return digest;
    }
}
