package edu.cecs478.securechat.client.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
        post.setHeader("Content-type", "application/json");
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

            get.setHeader("Authorization", token);
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
