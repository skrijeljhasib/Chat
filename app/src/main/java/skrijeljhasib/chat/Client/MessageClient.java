package skrijeljhasib.chat.Client;

import java.util.HashMap;
import java.util.Map;
import skrijeljhasib.chat.Entity.Message;
import skrijeljhasib.chat.Helper.JsonObjectConverter;

public class MessageClient extends ApiClient
{
    public MessageClient(String url, String authorization)
    {
        super(url + "/api/messages", authorization);
    }

    public String addMessageToRoom(Message message)
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", message);

        return this.post(JsonObjectConverter.objectToJson(parameters));
    }
}
