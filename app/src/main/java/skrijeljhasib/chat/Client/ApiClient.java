package skrijeljhasib.chat.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

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
            con.setDoOutput(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void post(final String json) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    con.setRequestMethod("POST");
                    con.connect();
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(json);
                    wr.flush();
                    wr.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        thread.start();
    }

    String get(Map<String, String> parameters) {
        StringBuilder response = new StringBuilder();

        try {
            this.con.setRequestMethod("GET");
            this.con.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (IOException e) {
            this.con.disconnect();
            e.printStackTrace();
        }

        return response.toString();
    }
}
