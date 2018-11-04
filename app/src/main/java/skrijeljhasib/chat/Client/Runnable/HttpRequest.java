package skrijeljhasib.chat.Client.Runnable;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpRequest implements Runnable {

    private volatile HttpURLConnection httpURLConnection;
    private volatile StringBuilder responseBody = new StringBuilder();
    private volatile String body;

    public HttpRequest(HttpURLConnection h, String b) {
        httpURLConnection = h;
        body = b;

    }

    public String getResponseBody() {
        return responseBody.toString();
    }

    @Override
    public void run() {
        try {
            if (!body.isEmpty()) {
                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(body);
                wr.flush();
                wr.close();
            }

            httpURLConnection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}