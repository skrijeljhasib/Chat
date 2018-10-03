package skrijeljhasib.chat;

import android.app.Application;
import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import skrijeljhasib.chat.Constants.Constants;

public class ChatApplication extends Application {

    private Socket socket;

    public ChatApplication() {
        try {
            this.socket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
           throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
