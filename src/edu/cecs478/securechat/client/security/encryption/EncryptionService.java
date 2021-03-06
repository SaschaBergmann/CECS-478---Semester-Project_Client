package edu.cecs478.securechat.client.security.encryption;

import edu.cecs478.securechat.client.helper.Constants;
import edu.cecs478.securechat.client.model.Message;
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
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Created by sasch on 09/10/2017.
 */
public class EncryptionService {
    /**
     * Decrypts a given message with the parameters given in the message object
     * First the keys are decrypted with a given RSA Key File, then the hash will be checked. Afterwards the message gets decrypted
     * @param msg message object containing the ciphertext, keys, iv and hash
     * @param rsaKeyFilePath Path to the private key file for decryption
     * @return returns the parameter Message object with all decrypted values
     * @throws IntegrityNotGuaranteedException Is thrown if the Hash calculated does not match the one in the object
     */
    /**
     * Encrypts a given message
     * First a key is generated for AES encryption on the message in the given object. Then a hash is created using HMAC.
     * the keys are then concatenated and encrypted using RSA and the given public key
     * @param msg message object containing the message and memebers to put the encryption parameters
     * @param rsaKeyFilePath Path to the public key file for the RSA encryption
     * @return message parameter object now containing the ciphertext, keys, iv
     */
    public static Message encrypt(Message msg, PublicKey publicKey){
        try {
            byte[] aESKey = EncryptionService.createPseudoRandomKey(Constants.AES_KEY_LENGTH);
            msg.setIv(EncryptionService.createPseudoRandomKey(Constants.IV_SIZE));
            IvParameterSpec ivParamSpec = new IvParameterSpec(msg.getIv());
            msg.setMessage(AESEncrypt(msg.getMessage(), aESKey, ivParamSpec));
            byte[] hmacKey = EncryptionService.createPseudoRandomKey(Constants.HMAC_KEY_SIZE);
            msg.setHMACHash(HMACService.CreateHMACHash(msg.getMessage(),hmacKey));

            byte[] concatenated = ArrayUtils.addAll(aESKey,hmacKey);

            msg.setEncryptionK(encryptRSA(concatenated, publicKey));
            return msg;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Encrypts the byte input with the public key
     * @param input input to be encrypted
     * @param pubk Public key used for encryption
     * @return encrypted data
     */
    private static byte[] encryptRSA(byte[] input, PublicKey pubk){
        byte[] encryptedData = null;
        try {
            //Setting bouncy castle as security provider
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            //Setting the cipher suite
            Cipher cipher = Cipher.getInstance(Constants.RSA_CIPHER_SUITE,Constants.SECURITY_PROVIDER);
            //Setting the cipher to decryption
            cipher.init(Cipher.ENCRYPT_MODE, pubk);
            encryptedData = cipher.doFinal(input);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return encryptedData;
    }

    /**
     * Perform a AES Enryption on the content with the given key and iv
     * @param content content to be encrypted
     * @param aesKey The aes key that is used
     * @param iv IV that is used in the encryption
     * @return encrypted bytes
     * @throws Exception Every exception thrown in this method not be caught but thrown further
     */
    private static byte[] AESEncrypt(byte[] content, byte[] aesKey, IvParameterSpec iv) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(aesKey), iv.getIV());
        aes.init(true, ivAndKey);
        return SecurityHelper.cipherData(aes, content);
    }

    /**
     * Creates a random key using the PRNG/Entry pool
     * @param cipherBlockSize Size of the key
     * @return Randomly generated Key
     * @throws NoSuchAlgorithmException If the random algorithm isn't found this exception is thrown
     */
    private static byte[] createPseudoRandomKey(int cipherBlockSize) throws NoSuchAlgorithmException {
        SecureRandom randomSecureRandom = SecureRandom.getInstance(Constants.RANDOM_NUMBER_GEN_SUITE);
        byte[] iv = new byte[cipherBlockSize];
        randomSecureRandom.nextBytes(iv);

        return iv;
    }
}
