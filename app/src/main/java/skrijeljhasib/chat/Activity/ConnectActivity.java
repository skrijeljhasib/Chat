package skrijeljhasib.chat.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.engineio.client.transports.WebSocket;
import skrijeljhasib.chat.ChatApplication;
import skrijeljhasib.chat.Client.ConfigClient;
import skrijeljhasib.chat.Client.MessageClient;
import skrijeljhasib.chat.Client.RoomClient;
import skrijeljhasib.chat.Db.MessageProvider;
import skrijeljhasib.chat.Entity.Config.Config;
import skrijeljhasib.chat.Helper.JsonObjectConverter;
import skrijeljhasib.chat.R;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getSharedPreferences("chat-connect", Context.MODE_PRIVATE);

        if (sharedPref.contains("api-url")) {
            String apiAddress = sharedPref.getString("api-url", "");
            String socketAddress = sharedPref.getString("socket-url", "");
            String token = sharedPref.getString("token", "");
            String username = sharedPref.getString("username", "");

            try {
                openMainActivity(apiAddress, socketAddress, token, username);
            } catch (Exception e) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.commit();
                e.printStackTrace();
            }
        }

        setContentView(R.layout.activity_connect);
    }

    public void connect(View view) {

        TextInputEditText apiInput = findViewById(R.id.network_settings_api);
        TextInputEditText tokenInput = findViewById(R.id.network_settings_token);
        TextInputEditText usernameInput = findViewById(R.id.user_preferences_username);

        String apiAddress = apiInput.getText().toString();
        String username = usernameInput.getText().toString();
        String token = tokenInput.getText().toString();

        if (apiAddress.isEmpty()) {
            apiInput.setError("Please enter your chat server address");
        } else if (username.isEmpty()) {
            usernameInput.setError("Please enter an username");
        } else {
            try {
                ConfigClient configClient = new ConfigClient(apiAddress, token);
                String data = configClient.fetchConfig();

                if (data.equals("")) {
                    throw new Exception("Wrong chat server");
                }

                Config config = (Config) JsonObjectConverter.jsonToObject(data, Config.class);

                String socketAddress = config.getSocket().getUrl();

                SharedPreferences sharedPref = getSharedPreferences("chat-connect", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("api-url", apiAddress);
                editor.putString("socket-url", socketAddress);
                editor.putString("token", token);
                editor.putString("username", username);
                editor.commit();
                openMainActivity(apiAddress, socketAddress, token, username);
            } catch (Exception e) {
                apiInput.setError("Enter a valid chat server address");
            }
        }
    }

    public void openMainActivity(String apiAddress, String socketAddress, String token, String username) throws URISyntaxException {
        ChatApplication chatApplication = (ChatApplication) getApplication();

        IO.Options opts = new IO.Options();
        opts.transports = new String[]{WebSocket.NAME};

        chatApplication.setSocket(IO.socket(socketAddress, opts));
        chatApplication.setMessageClient(new MessageClient(apiAddress, token));
        chatApplication.setRoomClient(new RoomClient(apiAddress, token));
        chatApplication.setUsername(username);
        chatApplication.setMessageProvider(new MessageProvider(this));

        Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
