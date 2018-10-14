package skrijeljhasib.chat.Client;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import skrijeljhasib.chat.Client.Runnable.HttpRequest;

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

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(json);
            wr.flush();
            wr.close();

            HttpRequest post = new HttpRequest(con);
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

            /*String parameterString = ParameterStringBuilder.getParamsString(parameters);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(parameterString);
            out.flush();
            out.close();*/

            HttpRequest get = new HttpRequest(con);
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
