package edu.cecs478.securechat.client.helper;

/**
 * Created by sasch on 03/12/2017.
 */
public class Constants {
    public static String CONTENT_TYPE_HEADER_NAME = "Content-type";
    public static String CONTENT_TYPE_JSON = "application/json";
    public static String AUTHORIZATION_HEADER_TOKEN_NAME = "Authorization";
    public static String ENCODING_ISO = "ISO-8859-1";

    public static String URL_ENDING_LOGIN1 = "/account/login1";
    public static String URL_ENDING_LOGIN2 = "/account/login2";
    public static String URL_MESSAGE_SEND = "/message/send";
    public static String URL_RECEIVE_MESSAGE = "/message/receive/";
    public static String URL_ACCOUNT_REGISTER = "/account/register";

    public static String RSA_CIPHER_SUITE = "RSA/NONE/OAEPWithSHA256AndMGF1Padding";
    public static String SECURITY_PROVIDER = "BC";
    public static String RANDOM_NUMBER_GEN_SUITE = "SHA1PRNG";
    public static String HASH_VERSION = "SHA-256";

    public static String PRIVATE_KEY_ENDING = "private.pem";
    public static String PUBLIC_KEY_ENDING = "public.pem";
    public static String RSA_GENERATION_TYPE = "RSA";

    public static Integer AES_KEY_LENGTH = 256/8;
    public static Integer IV_SIZE = 16;
    public static Integer HMAC_KEY_SIZE = 256/8;
}
