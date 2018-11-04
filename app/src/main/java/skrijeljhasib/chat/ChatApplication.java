package skrijeljhasib.chat;

import android.app.Application;

import io.socket.client.Socket;
import skrijeljhasib.chat.Client.MessageClient;
import skrijeljhasib.chat.Client.RoomClient;
import skrijeljhasib.chat.Db.MessageProvider;

public class ChatApplication extends Application {

    private Socket socket;
    private MessageClient messageClient;
    private RoomClient roomClient;
    private String username;
    private MessageProvider messageProvider;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public MessageClient getMessageClient() {
        return messageClient;
    }

    public void setMessageClient(MessageClient messageClient) {
        this.messageClient = messageClient;
    }

    public RoomClient getRoomClient() {
        return roomClient;
    }

    public void setRoomClient(RoomClient roomClient) {
        this.roomClient = roomClient;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MessageProvider getMessageProvider() {
        return messageProvider;
    }

    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }
}
