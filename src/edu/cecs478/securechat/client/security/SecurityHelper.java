package edu.cecs478.securechat.client.security;

import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by sasch on 10/10/2017.
 */
public class SecurityHelper {
    public static String readFileAsString(String filePath)
            throws java.io.IOException{
        StringBuffer fileData = new StringBuffer(3000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[3000];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[3000];
        }
        reader.close();
        System.out.println(fileData.toString());
        return fileData.toString();
    }


    public static String getHexString(byte[] b) throws Exception {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

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
