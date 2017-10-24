package edu.cecs478.securechat.client.security;

import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by FinnB on 10/10/2017.
 */
public class SecurityHelper {

    /**
     * Runs the given data through the given paddedbufferedBLockCipher
     * @param cipher PaddedBufferedBlockCipher that can be either encryption or decryption mode.
     * @param data Data that should be en- or decrypted
     * @return returns the data after it ran through the Cipher
     * @throws Exception Every Exception that can arise in the processs will be thrown upwards
     */
    public static byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data) throws Exception {
        int minSize = cipher.getOutputSize(data.length);
        byte[] outBuf = new byte[minSize];
        int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
        int length2 = cipher.doFinal(outBuf, length1);
        int actualLength = length1 + length2;
        byte[] result = new byte[actualLength];
        System.arraycopy(outBuf, 0, result, 0, result.length);
        return result;
    }
}
