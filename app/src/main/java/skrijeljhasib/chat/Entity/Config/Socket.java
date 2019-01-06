package skrijeljhasib.chat.Entity.Config;

import skrijeljhasib.chat.Entity.Entity;

public class Socket implements Entity {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
