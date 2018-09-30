package skrijeljhasib.chat.Socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;

public class MessageListener implements ConnectListener
{
    public MessageListener(SocketConnection socketConnection)
    {
        socketConnection.getServer().addConnectListener(this);
    }

    @Override
    public void onConnect(SocketIOClient client)
    {
        client.joinRoom("chat.message.create");
    }
}
