package edu.cecs478.securechat.client.security.decryption;

import edu.cecs478.securechat.client.model.Message;
import edu.cecs478.securechat.client.security.SecureChatPasswordFinder;
import edu.cecs478.securechat.client.security.SecurityHelper;
import edu.cecs478.securechat.client.security.exceptions.IntegrityNotGuaranteedException;
import edu.cecs478.securechat.client.security.integrity.HMACService;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.openssl.PEMReader;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileReader;
import java.security.KeyPair;
import java.security.Security;
import java.util.Arrays;

/**
 * Created by sasch on 10/10/2017.
 */
public class DecryptionService{

    /**
     * Decrypts a given message with the parameters given in the message object
     * First the keys are decrypted with a given RSA Key File, then the hash will be checked. Afterwards the message gets decrypted
     * @param msg message object containing the ciphertext, keys, iv and hash
     * @param rsaKeyFilePath Path to the private key file for decryption
     * @return returns the parameter Message object with all decrypted values
     * @throws IntegrityNotGuaranteedException Is thrown if the Hash calculated does not match the one in the object
     */
    public static Message decrypt(Message msg, String rsaKeyFilePath) throws IntegrityNotGuaranteedException{

        byte[] keyString = RSADecrypt(msg.getEncryptionK(), rsaKeyFilePath);

        byte[] aesKey = ArrayUtils.subarray(keyString, 0, 32);
        byte[] HMACKey = ArrayUtils.subarray(keyString, 32, 64);

        //Check Hashes and if they are not the same throw an exception
        if (!Arrays.equals(HMACService.CreateHMACHash(msg.getMessage(),HMACKey),(msg.getHMACHash()))) throw new IntegrityNotGuaranteedException(msg);

        try {
            IvParameterSpec ivParamSpec = new IvParameterSpec(msg.getIv());
            byte[] message = AESDecrypt(msg.getMessage(),aesKey, ivParamSpec);
            msg.setMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }

    /**
     * Perform a AES Decryption on the content with the given key and iv
     * @param content bytes that should be decrypted
     * @param aesKey AES Key used for decryption
     * @param iv IV that should be used in decryption
     * @return the bytes after decryption
     * @throws Exception Every exception thrown in this method not be caught but thrown further
     */
    private static byte[] AESDecrypt(byte[] content, byte[] aesKey, IvParameterSpec iv) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(aesKey), iv.getIV());
        aes.init(false, ivAndKey);
        return SecurityHelper.cipherData(aes, content);
    }

    /**
     * Performs a RSA Decryption on the input bytes using the private key file. The cipher is RSA/NONE/OAEPWithSHA256AndMGF1Padding
     * @param input input that should be decrypted
     * @param rsaKeyFilePath Path to the private key file
     * @return decrypted bytes
     */
    private static byte[] RSADecrypt(byte[] input, String rsaKeyFilePath) {
        byte[] decryptedData = null;
        try {
            //Adding bouncy castle as security provider
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            //Reading the Private Key using the SecureChat password finder
            PEMReader reader = new PEMReader(new FileReader(rsaKeyFilePath), new SecureChatPasswordFinder());
            try {
                Object o = reader.readObject();
                if (o instanceof KeyPair){
                    KeyPair kp = (KeyPair)o;
                    //Setting cipher and decrypting mode
                    Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding", "BC");
                    cipher.init(Cipher.DECRYPT_MODE, kp.getPrivate());

                    //Decrypting the data
                    decryptedData = cipher.doFinal(input);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return decryptedData;
    }
}
