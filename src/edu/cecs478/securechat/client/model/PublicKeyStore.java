package edu.cecs478.securechat.client.model;

import org.bouncycastle.openssl.PEMReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**k
 * Created by sasch on 28/11/2017.
 */
public class PublicKeyStore {
    private Map<String, PublicKey> keys;

    public PublicKeyStore(){
        keys = new HashMap<>();
    }

    public boolean isKeyAvailable(String name){
        return keys.containsKey(name);
    }

    public PublicKey getKey(String name){
        if (isKeyAvailable(name)) return keys.get(name);

        return null;
    }

    public void addKey(String name, String filePath){

        PEMReader reader = null;
        try {
            reader = new PEMReader(new FileReader(filePath));

            Object o = null;
            o = reader.readObject();

            if (o instanceof PublicKey) {
                PublicKey pubk = (PublicKey) o;
                keys.put(name, pubk);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
