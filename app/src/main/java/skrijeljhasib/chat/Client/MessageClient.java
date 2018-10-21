package skrijeljhasib.chat.Client;

import skrijeljhasib.chat.Entity.Message;

public class MessageClient extends ApiClient {
    public MessageClient(String url, String authorization) {
        super(url + "/api/messages", authorization);
    }

    public String addMessageToRoom(Message message) {
        String jsonBody = "message={" +
                "\"room_id\":\"" + message.getRoom().getId() + "\"," +
                "\"body\":\"" + message.getBody() + "\"," +
                "\"user_name\":\"" + message.getUsername() + "\"}";

        return post("", jsonBody);
    }
}
