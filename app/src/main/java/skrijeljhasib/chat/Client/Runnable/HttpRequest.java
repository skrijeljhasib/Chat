package skrijeljhasib.chat.Client.Runnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpRequest implements Runnable {

    private volatile HttpURLConnection httpURLConnection;
    private volatile StringBuilder responseBody = new StringBuilder();

    public HttpRequest(HttpURLConnection h) {
        httpURLConnection = h;
    }

    public String getResponseBody() {
        return responseBody.toString();
    }

    @Override
    public void run() {
        try {
            httpURLConnection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}