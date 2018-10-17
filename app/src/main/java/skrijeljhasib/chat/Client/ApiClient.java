package skrijeljhasib.chat.Client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import skrijeljhasib.chat.Client.Runnable.HttpRequest;
import skrijeljhasib.chat.Helper.ParameterStringBuilder;

abstract class ApiClient {
    private String url;
    private String authorization;

    ApiClient(String u, String a) {
       url = u;
       authorization = a;
    }

    private HttpURLConnection prepareConnection() {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestProperty("Authorization", authorization);
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return con;
    }

    String post(String json) {
        try {
            HttpURLConnection con = prepareConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);

            HttpRequest post = new HttpRequest(con, json);

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

    String get(Map<String, String> parameters) {
        try {
            HttpURLConnection con = prepareConnection();

            con.setRequestMethod("GET");

            String parameterString = ParameterStringBuilder.getParamsString(parameters);

            HttpRequest get = new HttpRequest(con, parameterString);

            Thread thread = new Thread(get);
            thread.start();
            thread.join();
            con.disconnect();
            return get.getResponseBody();
        }  catch (ProtocolException | InterruptedException | UnsupportedEncodingException e) {
            System.err.println(e.getMessage());
        }

        return "";
    }
}
