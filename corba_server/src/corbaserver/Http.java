/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corbaserver;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 *
 * @author lordyhas
 */

public class Http {
    public static String get(String url, int id) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            //HTTP GET method
            HttpGet httpget = new HttpGet(url+"/"+id);
            System.out.println("Executing get with id request" + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> getResponseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();

                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected [get(id)] response status: " + status);
                }
            };
            return httpclient.execute(httpget, getResponseHandler);
            //String responseBody = httpclient.execute(httpget, getResponseHandler);
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
        }
    }

    public static String get(String url) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            //HTTP GET method
            HttpGet httpget = new HttpGet(url);
            System.out.println("Executing get request" + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> getResponseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected [get] response status: " + status);
                }
            };
            return httpclient.execute(httpget, getResponseHandler);
            //System.out.println(responseBody);
        }
    }

    public static void post(String url, String json) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);

            System.out.println("Executing post request" + httpPost.getRequestLine());

            // Create a custom response handler
            ResponseHandler <String> postResponseHandler = res -> {
                int status = res.getStatusLine().getStatusCode();
                if (status >= 200 && status <= 300-1) {
                    HttpEntity entity = res.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected [post] response status: " + status);
                }
            };
            String responseBody = httpclient.execute(httpPost, postResponseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        }
    }



    public static void put(String url, String id) throws IOException {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(url+"/"+id);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            //StringEntity stringEntity = new StringEntity(json);
            //httpPut.setEntity(stringEntity);
            httpclient.execute(httpPut);
           
           
        }
    }
}
