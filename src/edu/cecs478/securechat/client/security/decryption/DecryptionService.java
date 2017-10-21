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
import org.bouncycastle.openssl.PasswordFinder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.util.Arrays;

/**
 * Created by sasch on 10/10/2017.
 */
public class DecryptionService{

    public static Message decrypt(Message msg, String rsaKeyFilePath) throws IntegrityNotGuaranteedException{
        byte[] keyString = RSADecrypt(msg.getKeys(), rsaKeyFilePath);

        byte[] aesKey = ArrayUtils.subarray(keyString, 0, 32);
        byte[] HMACKey = ArrayUtils.subarray(keyString, 32, 64);

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

    private static byte[] AESDecrypt(byte[] content, byte[] aesKey, IvParameterSpec iv) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(aesKey), iv.getIV());
        aes.init(false, ivAndKey);
        return SecurityHelper.cipherData(aes, content);
    }


    private static byte[] RSADecrypt(byte[] input, String rsaKeyFilePath) {
        byte[] decryptedData = null;
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            PEMReader reader = new PEMReader(new FileReader(rsaKeyFilePath), new SecureChatPasswordFinder());
            try {
                Object o = reader.readObject();
                if (o instanceof KeyPair){
                    KeyPair kp = (KeyPair)o;
                    Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding", "BC");

                    cipher.init(Cipher.DECRYPT_MODE, kp.getPrivate());

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
