package skrijeljhasib.chat.Client;

import java.util.HashMap;
import java.util.Map;

import skrijeljhasib.chat.Entity.Message;
import skrijeljhasib.chat.Helper.JsonObjectConverter;

public class MessageClient extends ApiClient {
    public MessageClient(String url, String authorization) {
        super(url + "/api/messages", authorization);
    }

    public void addMessageToRoom(Message message) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", message);

        post(JsonObjectConverter.objectToJson(parameters));
    }
}
