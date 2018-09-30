package skrijeljhasib.chat.Socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;

public class SocketConnection
{
    private SocketIOServer server;

    public SocketConnection()
    {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);

        Configuration config = new Configuration();
        config.setHostname("chat.local");
        config.setPort(3000);
        config.setSocketConfig(socketConfig);

        server = new SocketIOServer(config);
    }

    public SocketIOServer getServer()
    {
        return server;
    }

    public void setServer(SocketIOServer server)
    {
        this.server = server;
    }
}
