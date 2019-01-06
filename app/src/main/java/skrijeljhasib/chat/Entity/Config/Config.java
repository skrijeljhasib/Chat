package skrijeljhasib.chat.Entity.Config;

import skrijeljhasib.chat.Entity.Entity;

public class Config implements Entity {
    private Object connect;

    private Object logger;

    private Socket socket;

    public Object getConnect() {
        return connect;
    }

    public void setConnect(Object connect) {
        this.connect = connect;
    }

    public Object getLogger() {
        return logger;
    }

    public void setLogger(Object logger) {
        this.logger = logger;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
