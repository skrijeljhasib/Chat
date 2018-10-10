package skrijeljhasib.chat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.socket.client.IO;
import io.socket.engineio.client.transports.WebSocket;
import skrijeljhasib.chat.ChatApplication;
import skrijeljhasib.chat.Client.MessageClient;
import skrijeljhasib.chat.Client.RoomClient;
import skrijeljhasib.chat.R;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    public void connect(View view) {

        TextInputEditText serverInput = findViewById(R.id.network_settings_server);
        TextInputEditText tokenInput = findViewById(R.id.network_settings_token);
        TextInputEditText usernameInput = findViewById(R.id.user_preferences_username);

        String serverAddress = serverInput.getText().toString();
        String username = usernameInput.getText().toString();
        String token = tokenInput.getText().toString();

        if (serverAddress.isEmpty()) {
            serverInput.setError("Please enter a server address");
        } else if (username.isEmpty()) {
            usernameInput.setError("Please enter an username");
        } else {

            ChatApplication chatApplication = (ChatApplication) getApplication();

            IO.Options opts = new IO.Options();
            opts.transports = new String[]{WebSocket.NAME};

            try {
                chatApplication.setSocket(IO.socket(serverAddress, opts));
                chatApplication.setMessageClient(new MessageClient(serverAddress, token));
                chatApplication.setRoomClient(new RoomClient(serverAddress, token));

                Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } catch (Throwable e) {
                serverInput.setError("Please enter a valid server address");
            }
        }
    }
}
