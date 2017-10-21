package edu.cecs478.securechat.client.security.encryption;

import com.sun.deploy.util.ArrayUtil;
import edu.cecs478.securechat.client.model.Message;
import edu.cecs478.securechat.client.security.SecurityHelper;
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
import javax.crypto.spec.SecretKeySpec;
import java.io.FileReader;
import java.security.*;

/**
 * Created by sasch on 09/10/2017.
 */
public class EncryptionService {

    public static Message encrypt(Message msg, String rsaKeyFilePath){
        try {
            byte[] aESKey = EncryptionService.createPseudoRandomKey(256/8);
            msg.setIv(EncryptionService.createPseudoRandomKey(16));
            IvParameterSpec ivParamSpec = new IvParameterSpec(msg.getIv());
            msg.setMessage(AESEncrypt(msg.getMessage(), aESKey, ivParamSpec));
            byte[] hmacKey = EncryptionService.createPseudoRandomKey(256/8);
            msg.setHMACHash(HMACService.CreateHMACHash(msg.getMessage(),hmacKey));

            byte[] concatenated = ArrayUtils.addAll(aESKey,hmacKey);
            //msg.setKeys(concatenated);
            msg.setKeys(encryptRSA(concatenated, rsaKeyFilePath));
            return msg;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] encryptRSA(byte[] input, String rsaKeyFilePath){
        byte[] encryptedData = null;
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            PEMReader reader = new PEMReader(new FileReader(rsaKeyFilePath));
            try {
                Object o = reader.readObject();
                if (o instanceof PublicKey){
                    PublicKey pubk = (PublicKey)o;
                    Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding","BC");


                    cipher.init(Cipher.ENCRYPT_MODE, pubk);
                    encryptedData = cipher.doFinal(input);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return encryptedData;
    }

    private static byte[] AESEncrypt(byte[] content, byte[] aesKey, IvParameterSpec iv) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(aesKey), iv.getIV());
        aes.init(true, ivAndKey);
        return SecurityHelper.cipherData(aes, content);
    }

    private static byte[] createPseudoRandomKey(int cipherBlockSize) throws NoSuchAlgorithmException {
        SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] iv = new byte[cipherBlockSize];
        randomSecureRandom.nextBytes(iv);

        return iv;
    }
}
