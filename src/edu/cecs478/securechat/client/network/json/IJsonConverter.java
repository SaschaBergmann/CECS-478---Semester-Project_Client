package edu.cecs478.securechat.client.network.json;

/**
 * Created by sasch on 06/11/2017.
 */
public interface IJsonConverter<K> {

    String convert(K obj);

}
