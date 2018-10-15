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

        TextInputEditText socketInput = findViewById(R.id.network_settings_socket);
        TextInputEditText apiInput = findViewById(R.id.network_settings_api);
        TextInputEditText tokenInput = findViewById(R.id.network_settings_token);
        TextInputEditText usernameInput = findViewById(R.id.user_preferences_username);

        String socketAddress = socketInput.getText().toString();
        String apiAddress = apiInput.getText().toString();
        String username = usernameInput.getText().toString();
        String token = tokenInput.getText().toString();

        if (socketAddress.isEmpty()) {
            socketInput.setError("Please enter a socket address");
        } else if (apiAddress.isEmpty()) {
            apiInput.setError("Please enter a API address");
        } else if (username.isEmpty()) {
            usernameInput.setError("Please enter an username");
        } else {

            ChatApplication chatApplication = (ChatApplication) getApplication();

            IO.Options opts = new IO.Options();
            opts.transports = new String[]{WebSocket.NAME};

            try {
                chatApplication.setSocket(IO.socket(socketAddress, opts));
                chatApplication.setMessageClient(new MessageClient(apiAddress, token));
                chatApplication.setRoomClient(new RoomClient(apiAddress, token));
                chatApplication.setUsername(username);

                Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } catch (Throwable e) {
                socketInput.setError("Check you addresses");
            }
        }
    }
}
