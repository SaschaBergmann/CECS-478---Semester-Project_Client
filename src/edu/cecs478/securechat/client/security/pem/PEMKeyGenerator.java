package edu.cecs478.securechat.client.security.pem;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMWriter;

import java.io.File;
import java.io.FileWriter;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sasch on 02/11/2017.
 */
public class PEMKeyGenerator {

    public void generatePrivateAndPublicKey(String prefix) throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //Generate the key pair
        final KeyPair generateKeyPair = generateKeyPair(2048);

        final File privateKeyFile = new File("./"+prefix + "private.pem");
        final PEMWriter privatePemWriter = new PEMWriter(new FileWriter(privateKeyFile));
        privatePemWriter.writeObject(generateKeyPair.getPrivate());
        privatePemWriter.flush();
        privatePemWriter.close();

         final File publicKeyFile = new File("./"+prefix + "public.pem");
        final PEMWriter publicPemWriter = new PEMWriter(new FileWriter(publicKeyFile));
        publicPemWriter.writeObject(generateKeyPair.getPublic());
        publicPemWriter.flush();
        publicPemWriter.close();

    }

    private KeyPair generateKeyPair(Integer keyLength) {
        final KeyPairGenerator kpg;
        try { 
            kpg = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("RSA support could not be found. There is a problem with this JVM.", e); 
        } 
        catch (NoSuchProviderException e) {
            throw new IllegalStateException("BounceCastle support could not be found. There is a problem with this JVM.", e); 
        } 
         
        kpg.initialize(keyLength); 
        return kpg.generateKeyPair(); 
    } 
}
