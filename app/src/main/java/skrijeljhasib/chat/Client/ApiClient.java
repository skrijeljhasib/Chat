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

abstract class ApiClient
{
    private HttpURLConnection con;

    ApiClient(String url, String authorization)
    {
        try {
            this.con = (HttpURLConnection) new URL(url).openConnection();
            this.con.setRequestProperty("Content-Type", "application/json");
            this.con.setRequestProperty("Authorization", authorization);
            this.con.setConnectTimeout(3000);
            this.con.setReadTimeout(3000);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    String post(String json)
    {
        StringBuilder response = new StringBuilder();

        try {
            this.con.setRequestMethod("POST");
            DataOutputStream wr = new DataOutputStream (this.con.getOutputStream());
            wr.writeBytes(json);
            wr.close();

            InputStream is = this.con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
        } catch (Exception e) {
            this.con.disconnect();
            e.printStackTrace();
        }
        this.con.disconnect();

        return response.toString();
    }

    String get(Map<String, String> parameters)
    {
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
