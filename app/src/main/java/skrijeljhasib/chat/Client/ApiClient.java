package skrijeljhasib.chat.Client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import skrijeljhasib.chat.Client.Runnable.HttpRequest;

abstract class ApiClient {
    private String url;
    private String authorization;

    ApiClient(String u, String a) {
       url = u;
       authorization = a;
    }

    private HttpURLConnection prepareConnection(String urlParams) {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url + urlParams).openConnection();
            con.setRequestProperty("Authorization", authorization);
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return con;
    }

    String post(String urlParams, String body) {
        try {
            HttpURLConnection con = prepareConnection(urlParams);

            con.setRequestMethod("POST");
            con.setDoOutput(true);

            HttpRequest post = new HttpRequest(con, body);

            Thread thread = new Thread(post);
            thread.start();
            thread.join();
            con.disconnect();
            return post.getResponseBody();
        } catch (ProtocolException | InterruptedException e) {
            System.err.println(e.getMessage());
        }

        return "";
    }

    String get(String urlParams) {
        try {
            HttpURLConnection con = prepareConnection(urlParams);

            con.setRequestMethod("GET");

            HttpRequest get = new HttpRequest(con, "");

            Thread thread = new Thread(get);
            thread.start();
            thread.join();
            con.disconnect();
            return get.getResponseBody();
        }  catch (ProtocolException | InterruptedException e) {
            System.err.println(e.getMessage());
        }

        return "";
    }
}
