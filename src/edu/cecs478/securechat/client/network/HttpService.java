package edu.cecs478.securechat.client.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import edu.cecs478.securechat.client.helper.Constants;
import edu.cecs478.securechat.client.network.exceptions.HttpResponseNotCorrectException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Finn on 06/11/2017.
 */
public class HttpService {
    private static HttpClient httpClient    = new DefaultHttpClient();

    public static String sendPOSTRequest(String json, String url) throws UnsupportedEncodingException, HttpResponseNotCorrectException {
        HttpPost     post          = new HttpPost(url);
        StringEntity postingString = new StringEntity(json);
        post.setEntity(postingString);
        post.setHeader(Constants.CONTENT_TYPE_HEADER_NAME, Constants.CONTENT_TYPE_JSON);
        try {
            HttpResponse response = httpClient.execute(post);

            if(response.getStatusLine().getStatusCode() == 200) {
                return new BasicResponseHandler().handleResponse(response);
            }
            throw new HttpResponseNotCorrectException(response.getStatusLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sendGetRequest(String url, String token) throws UnsupportedEncodingException, HttpResponseNotCorrectException {
        HttpGet get = new HttpGet(url);
        try {

            get.setHeader(Constants.AUTHORIZATION_HEADER_TOKEN_NAME, token);
            HttpResponse response = httpClient.execute(get);

            if(response.getStatusLine().getStatusCode() == 200) {
                return new BasicResponseHandler().handleResponse(response);
            }
            throw new HttpResponseNotCorrectException(response.getStatusLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
