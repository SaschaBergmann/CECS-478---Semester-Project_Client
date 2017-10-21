package edu.cecs478.securechat.client.security.integrity;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * Created by sasch on 10/10/2017.
 */
public class HMACService {
    public static byte[] CreateHMACHash(byte[] toEncode,byte[] key) {
        SHA256Digest sha256 = new SHA256Digest();
        HMac hmac=new HMac(sha256);
        byte[] resBuf=new byte[hmac.getMacSize()];
        byte[] plainBytes = toEncode;
        byte[] keyBytes=key;
        hmac.init(new KeyParameter(keyBytes));
        hmac.update(plainBytes,0,plainBytes.length);
        hmac.doFinal(resBuf,0);
        return resBuf;
    }
}
