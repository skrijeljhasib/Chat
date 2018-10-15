package skrijeljhasib.chat.Client;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import skrijeljhasib.chat.Client.Runnable.HttpRequest;
import skrijeljhasib.chat.Helper.ParameterStringBuilder;

abstract class ApiClient {
    private HttpURLConnection con;

    ApiClient(String url, String authorization) {
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", authorization);
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    String post(String json) {
        try {
            con.setRequestMethod("POST");

            HttpRequest post = new HttpRequest(con, json);

            Thread thread = new Thread(post);
            thread.start();
            thread.join();
            return post.getResponseBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    String get(Map<String, String> parameters) {
        try {
            con.setRequestMethod("GET");

            String parameterString = ParameterStringBuilder.getParamsString(parameters);

            HttpRequest get = new HttpRequest(con, parameterString);

            Thread thread = new Thread(get);
            thread.start();
            thread.join();
            return get.getResponseBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
