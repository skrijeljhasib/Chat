package skrijeljhasib.chat.Client;

public class ConfigClient extends ApiClient {
    public ConfigClient(String url, String authorization) {
        super(url + "/api/config", authorization);
    }

    public String fetchConfig() {
        return this.get("");
    }
}
