package skrijeljhasib.chat.Client.Runnable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest implements Runnable {

    private volatile InputStream inputStream;
    private volatile StringBuilder responseBody = new StringBuilder();

    public HttpRequest(InputStream i) {
        inputStream = i;
    }

    public String getResponseBody() {
        return responseBody.toString();
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

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